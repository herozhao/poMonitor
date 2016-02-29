package pomonitor.analyse.entity;

import java.util.ArrayList;

public class HotWordResult {
	private ArrayList<RetLink> links;
	private ArrayList<RetHotWord> nodes;

	public ArrayList<RetLink> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<RetLink> links) {
		this.links = links;
	}

	public ArrayList<RetHotWord> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<RetHotWord> nodes) {
		this.nodes = nodes;
	}

}
