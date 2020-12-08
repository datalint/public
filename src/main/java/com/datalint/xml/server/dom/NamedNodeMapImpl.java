package com.datalint.xml.server.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.datalint.xml.shared.ICommon;

public class NamedNodeMapImpl implements NamedNodeMap, ICommon {
	private final Node owner;
	private final Map<String, String> attrs;

	private List<Node> nodes;

	public NamedNodeMapImpl(Node owner, Map<String, String> attrs) {
		this.owner = owner;
		this.attrs = attrs;
	}

	@Override
	public Node item(int index) {
		if (nodes == null) {
			nodes = new ArrayList<>(attrs.size());

			for (Entry<String, String> entry : attrs.entrySet()) {
				nodes.add(new AttrImpl(owner, entry.getKey(), entry.getValue()));
			}
		}

		return nodes.get(index);
	}

	@Override
	public int getLength() {
		return attrs.size();
	}

	@Override
	public Node getNamedItem(String name) {
		String value = attrs.get(name);

		return value == null ? null : new AttrImpl(owner, name, value);
	}

	@Override
	public Node setNamedItem(Node arg) throws DOMException {
		throw iCreateUoException("setNamedItem");
	}

	@Override
	public Node removeNamedItem(String name) throws DOMException {
		throw iCreateUoException("removeNamedItem");
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw iCreateUoException("getNamedItemNS");
	}

	@Override
	public Node setNamedItemNS(Node arg) throws DOMException {
		throw iCreateUoException("setNamedItemNS");
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw iCreateUoException("removeNamedItemNS");
	}
}
