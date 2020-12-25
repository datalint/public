package com.datalint.xml.server.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.datalint.xml.shared.ICommon;

public class NodeListImpl implements NodeList, ICommon {
	private final static NodeListImpl EMPTY = new NodeListImpl();
	private final static Predicate<String> TAG_ALL = name -> true;

	private final NodeImpl owner;
	private final String tagName;

	private int cacheRevision;
	private List<Node> cache;

	public static NodeListImpl empty() {
		return EMPTY;
	}

	public NodeListImpl(NodeImpl owner, String tagName) {
		this.owner = owner;
		this.tagName = tagName;
	}

	public NodeListImpl(NodeImpl owner) {
		this(owner, null);
	}

	private NodeListImpl() {
		this(null);
	}

	protected List<Node> getNodes() {
		if (owner == null)
			return Collections.emptyList();
		else if (tagName == null)
			return owner.getChildNodesImpl();

		int childNodesRevision = owner.getChildNodesRevision();

		if (cacheRevision != childNodesRevision || cache == null) {
			cacheRevision = childNodesRevision;

			cache = new ArrayList<Node>();
			owner.appendElementsByTagName(cache, tagName.equals(WILDCARD) ? TAG_ALL : name -> name.equals(tagName));
		}

		return cache;
	}

	@Override
	public Node item(int index) {
		return getNodes().get(index);
	}

	@Override
	public int getLength() {
		return getNodes().size();
	}
}
