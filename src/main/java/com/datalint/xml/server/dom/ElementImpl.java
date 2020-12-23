package com.datalint.xml.server.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public class ElementImpl extends ParentNode implements Element {
	private final String tagName;

	private Map<String, String> attrs;

	public ElementImpl(Node owner, String tagName) {
		super(owner);

		this.tagName = tagName;
	}

	@Override
	public String getNodeName() {
		return tagName;
	}

	@Override
	public short getNodeType() {
		return ELEMENT_NODE;
	}

	@Override
	public String getTagName() {
		return getNodeName();
	}

	@Override
	public String getAttribute(String name) {
		return attrs == null ? EMPTY : iNonNull(attrs.get(name));
	}

	@Override
	public void setAttribute(String name, String value) {
		assert value != null : value;

		if (attrs == null)
			attrs = new HashMap<>();

		attrs.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		if (attrs != null && attrs.remove(name) != null && attrs.isEmpty())
			attrs = null;
	}

	@Override
	public Attr getAttributeNode(String name) {
		String value = getAttribute(name);

		return value == null ? null : new AttrImpl(this, name, value);
	}

	@Override
	public boolean hasAttributes() {
		return attrs != null && attrs.size() > 0;
	}

	public NodeList getElementsByTagName(List<Node> holder, String name) {
		appendElements(holder, name.equals(WILDCARD) ? tagName -> true : tagName -> tagName.equals(name));

		return new NodeListImpl(holder);
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		return getElementsByTagName(new ArrayList<>(), name);
	}

	private void appendElements(List<Node> holder, Predicate<String> predicate) {
		if (children == null)
			return;

		for (Node child : children) {
			if (child instanceof ElementImpl) {
				if (predicate.test(child.getNodeName()))
					holder.add(child);

				((ElementImpl) child).appendElements(holder, predicate);
			}
		}
	}

	@Override
	public boolean hasAttribute(String name) {
		return attrs != null && attrs.containsKey(name);
	}

	@Override
	public Node cloneNode(boolean deep) {
		ElementImpl clone = new ElementImpl(getOwnerDocument(), tagName);

		if (attrs != null) {
			clone.attrs = new HashMap<>(attrs.size());

			for (Entry<String, String> entry : attrs.entrySet()) {
				clone.attrs.put(entry.getKey(), entry.getValue());
			}
		}

		if (deep && children != null) {
			clone.children = new ArrayList<>(children.size());

			for (Node child : children) {
				clone.children.add(child.cloneNode(true));
			}
		}

		return clone;
	}

	@Override
	public Node getParentNode() {
		return owner instanceof Document && this != ((Document) owner).getDocumentElement() ? null : owner;
	}

	@Override
	public NodeList getChildNodes() {
		return new NodeListImpl(children == null ? Collections.emptyList() : children);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return new NamedNodeMapImpl(getOwnerDocument(), attrs == null ? Collections.emptyMap() : attrs);
	}

	@Override
	public Attr setAttributeNode(Attr newAttr) {
		throw iCreateUoException("setAttributeNode");
	}

	@Override
	public Attr removeAttributeNode(Attr oldAttr) {
		throw iCreateUoException("removeAttributeNode");
	}

	@Override
	public String getAttributeNS(String namespaceURI, String localName) {
		throw iCreateUoException("getAttributeNS");
	}

	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
		throw iCreateUoException("setAttributeNS");
	}

	@Override
	public void removeAttributeNS(String namespaceURI, String localName) {
		throw iCreateUoException("removeAttributeNS");
	}

	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName) {
		throw iCreateUoException("getAttributeNodeNS");
	}

	@Override
	public Attr setAttributeNodeNS(Attr newAttr) {
		throw iCreateUoException("setAttributeNodeNS");
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw iCreateUoException("getElementsByTagNameNS");
	}

	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName) {
		throw iCreateUoException("hasAttributeNS");
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw iCreateUoException("getSchemaTypeInfo");
	}

	@Override
	public void setIdAttribute(String name, boolean isId) {
		throw iCreateUoException("setIdAttribute");
	}

	@Override
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
		throw iCreateUoException("setIdAttributeNS");
	}

	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId) {
		throw iCreateUoException("setIdAttributeNode");
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();

		sB.append(_LESS_THAN).append(getNodeName());

		if (attrs != null)
			for (Entry<String, String> entry : attrs.entrySet())
				sB.append(_SPACE).append(AttrImpl.toString(entry.getKey(), entry.getValue()));

		if (children == null)
			sB.append(_SLASH).append(_GREATER_THAN);
		else {
			sB.append(_GREATER_THAN);

			for (Node child : children)
				sB.append(child);

			sB.append(_LESS_THAN).append(_SLASH).append(getNodeName()).append(_GREATER_THAN);
		}

		return sB.toString();
	}
}
