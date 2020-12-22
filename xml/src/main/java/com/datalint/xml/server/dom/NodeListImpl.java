package com.datalint.xml.server.dom;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListImpl implements NodeList {
	protected final List<Node> nodes;

	public NodeListImpl(List<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	public Node item(int index) {
		return nodes.get(index);
	}

	@Override
	public int getLength() {
		return nodes.size();
	}
}
