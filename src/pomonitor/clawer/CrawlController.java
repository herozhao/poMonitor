/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pomonitor.clawer;

import pomonitor.entity.NewsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrawlController {

	/**
	 * �Զ���ģ����ڴ����ȡ������ʵ�����
	 */
	protected HashMap<String, Object> map;

	static final Logger logger = LoggerFactory.getLogger(CrawlController.class);

	// �Ƿ����һ�� ��ȡ
	protected boolean finished;

	// ����������Ƿ�ر�
	protected boolean shuttingDown;

	// ���ڴ��url�Ķ���
	protected Frontier frontier;

	private String filePath;

	// ���ڿ����첽�Ķ���
	protected final Object waitingLock = new Object();

	public CrawlController(String filePath, Frontier frontier) {
		this.frontier = frontier;
		finished = false;
		shuttingDown = false;
		this.filePath = filePath;
	}

	private void stop() {
		finished = false;
		shuttingDown = false;
	}

	public void addUrl(ArrayList<NewsEntity> list) {
		frontier.addAll(list);
	}

	public void addUrl(NewsEntity entity) {
		frontier.add(entity);
	}

	/*
	 * 
	 * 
	 * /** Wait until this crawling session finishes.
	 */
	public void waitUntilFinish() {
		// ���û����ɣ���һֱ�ȣ������ɣ����˳�����ִ��
		while (!finished) {
			synchronized (waitingLock) {
				if (finished) {
					return;
				}
				try {
					waitingLock.wait();
				} catch (InterruptedException e) {
					logger.error("Error occurred", e);
				}
			}
		}
	}

	// ���̿յ�����
	protected static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception ignored) {
			// Do nothing
		}
	}

	public boolean isFinished() {
		return this.finished;
	}

	public boolean isShuttingDown() {
		return shuttingDown;
	}

	/**
	 * �ر�
	 */
	public void shutdown() {
		logger.info("Shutting down...");
		this.shuttingDown = true;

	}

	/**
	 * ���濪ʼ��ȡ
	 * 
	 * @param _c
	 * @param numberOfCrawlers
	 * @param isBlocking
	 */
	public void start(final Class<DbSaveCrawl> _c, int numberOfCrawlers,
			boolean isBlocking) {
		try {
			finished = false;
			// �����Ҫ���������
			final List<Thread> threads = new ArrayList<>();
			final List<DbSaveCrawl> crawlers = new ArrayList<>();
			int count = (int) frontier.getQueueLength() / numberOfCrawlers + 1;
			for (int i = 1; i <= numberOfCrawlers; i++) {
				// Crawl crawler = _c.newInstance();
				DbSaveCrawl crawler = _c.newInstance();
				Thread thread = new Thread(crawler, "Crawler " + i);
				crawler.init(i, filePath);

				ArrayList<NewsEntity> list = frontier.distribute(count);
				crawler.setWorksList(list);
				thread.start();
				crawlers.add(crawler);
				threads.add(thread);
				logger.info("Crawler {} started", i);
			}

			Thread monitorThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						synchronized (waitingLock) {

							while (true) {
								sleep(10);
								boolean someoneIsWorking = false;
								for (int i = 0; i < threads.size(); i++) {
									Thread thread = threads.get(i);
									// �����ǰ�߳��������������߳�
									if (!thread.isAlive()) {
										// ������ȡҳ��
										if (!shuttingDown) {
											logger.info(
													"Thread {} was dead, I'll recreate it",
													i + 1);
											// ��������һ�����߳�
											DbSaveCrawl crawler = _c
													.newInstance();

											ArrayList<NewsEntity> list = frontier
													.distribute(20);
											crawler.init(i + 1, filePath);
											crawler.setWorksList(list);

											thread = new Thread(crawler,
													"Crawler " + (i + 1));
											threads.remove(i);
											threads.add(i, thread);
											thread.start();
											crawlers.remove(i);
											crawlers.add(i, crawler);

											// ���´���������������̲߳�����
											// Crawler crawler=crawlers.get(i);
											// ArrayList<NewsEntity> list =
											// frontier
											// .distribute(20);
											// crawler.setWorksList(list);
											// thread.start();

										}
										// ��������ڵȴ����̣߳���֤�������ڹ���
									} else {
										someoneIsWorking = true;
									}
								}
								// ���û���˹�������
								if (!someoneIsWorking) {
									// Make sure again that none of the threads
									// are alive.
									logger.info("It looks like no thread is working, waiting for 10 seconds to make sure...");
									sleep(10);

									someoneIsWorking = false;
									// �ڴ�ȥ�ж��Ƿ������ڹ���
									for (int i = 0; i < threads.size(); i++) {
										Thread thread = threads.get(i);
										if (thread.isAlive()) {
											someoneIsWorking = true;
										}
									}
									// ȷʵû�˹���
									if (!someoneIsWorking) {
										// monitor��û�йر�
										if (!shuttingDown) {
											// ��õ�ǰ��Ҫ��ȡurl�Ķ��г���
											long queueLength = frontier
													.getQueueLength();
											// ������г��ȴ���0,˵����Ҫ������ȡ
											if (queueLength > 0) {
												continue;
											}
											// ���С�ڻ��ߵ���0˵��ȷʵ�Ѿ�û��url��Ҫ��ȡ�ˡ�
											logger.info("No thread is working and no more URLs are in queue waiting for another 10 seconds to make sure...");
											sleep(10);
											// ʱ��10���ٴ�ȥ�ж�
											queueLength = frontier
													.getQueueLength();
											if (queueLength > 0) {
												continue;
											}
										}

										logger.info("All of the crawlers are stopped. Finishing the process...");
										// At this step, frontier notifies the
										// threads that were waiting for new
										// URLs and they should stop

										logger.info("Waiting for 10 seconds before final clean up...");
										sleep(10);
										// �ر��������
										// ��ǰ������������ȡ
										finished = true;
										// ����첽��
										waitingLock.notifyAll();
										// �ص����ݿ��һЩ����

										return;
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("Unexpected Error", e);
					}
				}
			});

			// �������������̿��Ƽ�����
			monitorThread.start();

			// ��������������ȴ�ֱ�����������
			if (isBlocking) {
				waitUntilFinish();
			}
			frontier.stop();
			stop();

		} catch (Exception e) {
			logger.error("Error happened", e);
		}
	}
}