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

	private Map<String, String> attributes;
	private int attributesRevision;

	private int childNodesRevision;

	public ElementImpl(Node owner, String tagName) {
		super(owner);

		this.tagName = tagName;
	}

	public int getAttributesRevision() {
		return attributesRevision;
	}

	public Map<String, String> getAttributesImpl() {
		return attributes == null ? Collections.emptyMap() : attributes;
	}

	@Override
	public int getChildNodesRevision() {
		return childNodesRevision;
	}

	@Override
	public void onChildNodesChanged() {
		Node parent = getParentNode();

		while (parent instanceof NodeImpl) {
			((NodeImpl) parent).onChildNodesChanged();

			parent = parent.getParentNode();
		}

		childNodesRevision++;
	}

	@Override
	public void appendElementsByTagName(List<Node> holder, Predicate<String> predicate) {
		if (children == null)
			return;

		for (Node child : children) {
			if (child instanceof ElementImpl) {
				if (predicate.test(child.getNodeName()))
					holder.add(child);

				((ElementImpl) child).appendElementsByTagName(holder, predicate);
			}
		}
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
		return attributes == null ? EMPTY : iNonNull(attributes.get(name));
	}

	@Override
	public void setAttribute(String name, String value) {
		if (attributes == null)
			attributes = new HashMap<>();

		value = iNonNull(value);

		if (!value.equals(attributes.put(name, value)))
			attributesRevision++;
	}

	@Override
	public void removeAttribute(String name) {
		if (attributes != null && attributes.remove(name) != null) {
			if (attributes.isEmpty())
				attributes = null;

			attributesRevision++;
		}
	}

	@Override
	public Attr getAttributeNode(String name) {
		String value = getAttribute(name);

		return value == null ? null : new AttrImpl(this, name, value);
	}

	@Override
	public boolean hasAttributes() {
		return attributes != null;
	}

	@Override
	public NodeList getElementsByTagName(String tagName) {
		return new NodeListImpl(this, tagName);
	}

	@Override
	public boolean hasAttribute(String name) {
		return attributes != null && attributes.containsKey(name);
	}

	@Override
	public Node cloneNode(boolean deep) {
		ElementImpl clone = new ElementImpl(getOwnerDocument(), tagName);

		if (attributes != null) {
			clone.attributes = new HashMap<>(attributes.size());

			for (Entry<String, String> entry : attributes.entrySet()) {
				clone.attributes.put(entry.getKey(), entry.getValue());
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
		return new NodeListImpl(this);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return new NamedNodeMapImpl(this);
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();

		sB.append(_LESS_THAN).append(getNodeName());

		if (attributes != null)
			for (Entry<String, String> entry : attributes.entrySet())
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
}
