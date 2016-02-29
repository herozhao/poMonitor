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
	 * 自定义的，用于存放爬取下来的实体对象
	 */
	protected HashMap<String, Object> map;

	static final Logger logger = LoggerFactory.getLogger(CrawlController.class);

	// 是否完成一次 爬取
	protected boolean finished;

	// 爬虫监视器是否关闭
	protected boolean shuttingDown;

	// 用于存放url的对列
	protected Frontier frontier;

	private String filePath;

	// 用于控制异步的对象
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
		// 如果没有完成，就一直等，如果完成，就退出向下执行
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

	// 进程空挡函数
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
	 * 关闭
	 */
	public void shutdown() {
		logger.info("Shutting down...");
		this.shuttingDown = true;

	}

	/**
	 * 爬虫开始爬取
	 * 
	 * @param _c
	 * @param numberOfCrawlers
	 * @param isBlocking
	 */
	public void start(final Class<DbSaveCrawl> _c, int numberOfCrawlers,
			boolean isBlocking) {
		try {
			finished = false;
			// 清除需要保存的数据
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
									// 如果当前线程已死，更换新线程
									if (!thread.isAlive()) {
										// 还在爬取页面
										if (!shuttingDown) {
											logger.info(
													"Thread {} was dead, I'll recreate it",
													i + 1);
											// 重新启动一个新线程
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

											// 以下代码表明不重新启线程不可行
											// Crawler crawler=crawlers.get(i);
											// ArrayList<NewsEntity> list =
											// frontier
											// .distribute(20);
											// crawler.setWorksList(list);
											// thread.start();

										}
										// 如果不是在等待新线程，则证明其正在工作
									} else {
										someoneIsWorking = true;
									}
								}
								// 如果没有人工作了了
								if (!someoneIsWorking) {
									// Make sure again that none of the threads
									// are alive.
									logger.info("It looks like no thread is working, waiting for 10 seconds to make sure...");
									sleep(10);

									someoneIsWorking = false;
									// 在此去判断是否有人在工作
									for (int i = 0; i < threads.size(); i++) {
										Thread thread = threads.get(i);
										if (thread.isAlive()) {
											someoneIsWorking = true;
										}
									}
									// 确实没人工作
									if (!someoneIsWorking) {
										// monitor还没有关闭
										if (!shuttingDown) {
											// 获得当前需要爬取url的对列长度
											long queueLength = frontier
													.getQueueLength();
											// 如果队列长度大于0,说明还要继续爬取
											if (queueLength > 0) {
												continue;
											}
											// 如果小于或者等于0说明确实已经没有url需要爬取了。
											logger.info("No thread is working and no more URLs are in queue waiting for another 10 seconds to make sure...");
											sleep(10);
											// 时隔10秒再次去判断
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
										// 关闭爬虫对列
										// 当前爬虫控制完成爬取
										finished = true;
										// 解除异步锁
										waitingLock.notifyAll();
										// 关掉数据库的一些阻塞

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

			// 开启这个爬虫进程控制监视器
			monitorThread.start();

			// 如果正在阻塞，等待直到，阻塞解除
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