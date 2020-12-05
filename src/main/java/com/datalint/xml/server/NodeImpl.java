package com.datalint.xml.server;

import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import com.datalint.xml.shared.IBasicUtil;

public abstract class NodeImpl implements Node, IBasicUtil {
	protected Node owner;

	public NodeImpl(Node owner) {
		this.owner = owner;
	}

	@Override
	public String getNodeValue() {
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) {
	}

	@Override
	public Node getParentNode() {
		return owner instanceof Document ? null : owner;
	}

	@Override
	public NodeList getChildNodes() {
		return new NodeListImpl(Collections.emptyList());
	}

	@Override
	public Node getFirstChild() {
		return null;
	}

	@Override
	public Node getLastChild() {
		return null;
	}

	@Override
	public Node getPreviousSibling() {
		if (owner instanceof ElementImpl)
			return ((ElementImpl) owner).getPreviousSibling(this);

		return null;
	}

	@Override
	public Node getNextSibling() {
		if (owner instanceof ElementImpl)
			return ((ElementImpl) owner).getNextSibling(this);

		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public Document getOwnerDocument() {
		if (this instanceof Document)
			return null;
		else if (owner instanceof Document)
			return (Document) owner;

		return owner.getOwnerDocument();
	}

	protected void setOwner(Node node, Node owner) {
		assert node instanceof NodeImpl;

		((NodeImpl) node).owner = owner;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		return newChild;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
		return oldChild;
	}

	@Override
	public Node removeChild(Node oldChild) {
		return oldChild;
	}

	@Override
	public Node appendChild(Node newChild) {
		return newChild;
	}

	@Override
	public boolean hasChildNodes() {
		return false;
	}

	@Override
	public void normalize() {
	}

	@Override
	public String getNamespaceURI() {
		return null;
	}

	@Override
	public String getPrefix() {
		String name = getNodeName();

		if (name != null) {
			int index = name.indexOf(':');

			if (index >= 0)
				return name.substring(0, index);
		}

		return null;
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public boolean isSupported(String feature, String version) {
		throw createUnsupportedOperationException("isSupported");
	}

	@Override
	public void setPrefix(String prefix) {
		throw createUnsupportedOperationException("setPrefix");
	}

	@Override
	public String getLocalName() {
		throw createUnsupportedOperationException("getLocalName");
	}

	@Override
	public String getBaseURI() {
		throw createUnsupportedOperationException("getBaseURI");
	}

	@Override
	public short compareDocumentPosition(Node other) {
		throw createUnsupportedOperationException("compareDocumentPosition");
	}

	@Override
	public String getTextContent() {
		throw createUnsupportedOperationException("getTextContent");
	}

	@Override
	public void setTextContent(String textContent) {
		throw createUnsupportedOperationException("textContent");
	}

	@Override
	public boolean isSameNode(Node other) {
		throw createUnsupportedOperationException("isSameNode");
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		throw createUnsupportedOperationException("namespaceURI");
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		throw createUnsupportedOperationException("isDefaultNamespace");
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		throw createUnsupportedOperationException("lookupNamespaceURI");
	}

	@Override
	public boolean isEqualNode(Node arg) {
		throw createUnsupportedOperationException("isEqualNode");
	}

	@Override
	public Object getFeature(String feature, String version) {
		throw createUnsupportedOperationException("getFeature");
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		throw createUnsupportedOperationException("setUserData");
	}

	@Override
	public Object getUserData(String key) {
		throw createUnsupportedOperationException("getUserData");
	}

	@Override
	public String toString() {
		return '[' + getNodeName() + ": " + getNodeValue() + ']';
	}
}
