package com.datalint.xml.server.dom;

import java.util.Collections;

import org.w3c.dom.*;

public class DocumentImpl extends NodeImpl implements Document {
	public static final String KEY_id = "id";

	private Element element;

	public DocumentImpl() {
		super(null);
	}

	@Override
	public String getNodeName() {
		return "#document";
	}

	@Override
	public short getNodeType() {
		return DOCUMENT_NODE;
	}

	@Override
	public Node cloneNode(boolean deep) {
		DocumentImpl document = new DocumentImpl();

		if (deep && element != null)
			document.element = (Element) element.cloneNode(true);

		return document;
	}

	@Override
	public Element getDocumentElement() {
		return element;
	}

	@Override
	public Element createElement(String tagName) {
		return new ElementImpl(this, tagName);
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		return new DocumentFragmentImpl(this);
	}

	@Override
	public Text createTextNode(String data) {
		return new TextImpl(this, data);
	}

	@Override
	public Comment createComment(String data) {
		return new CommentImpl(this, data);
	}

	@Override
	public CDATASection createCDATASection(String data) {
		return new CDATASectionImpl(this, data);
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) {
		return new ProcessingInstructionImpl(this, target, data);
	}

	/*
	 * This method is NOT optimized. Client side shall cache idMap by self through
	 * getElementsByTagName(*).
	 */
	@Override
	public Element getElementById(String elementId) {
		NodeList nodeList = getElementsByTagName("*");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);

			if (elementId.equals(element.getAttribute(KEY_id)))
				return element;
		}

		return null;
	}

	@Override
	public NodeList getElementsByTagName(String tagname) {
		return element == null ? new NodeListImpl(Collections.emptyList()) : element.getElementsByTagName(tagname);
	}

	@Override
	public Node importNode(Node importedNode, boolean deep) {
		return importedNode;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		element = (Element) newChild;

		return newChild;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
		element = (Element) newChild;

		return oldChild;
	}

	@Override
	public Node removeChild(Node oldChild) {
		return element = null;
	}

	@Override
	public Node appendChild(Node newChild) {
		element = (Element) newChild;

		return newChild;
	}

	@Override
	public boolean hasChildNodes() {
		return element != null;
	}

	@Override
	public DocumentType getDoctype() {
		throw createUnsupportedOperationException("getDoctype");
	}

	@Override
	public DOMImplementation getImplementation() {
		throw createUnsupportedOperationException("getImplementation");
	}

	@Override
	public Attr createAttribute(String name) {
		throw createUnsupportedOperationException("createAttribute");
	}

	@Override
	public EntityReference createEntityReference(String name) {
		throw createUnsupportedOperationException("createEntityReference");
	}

	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) {
		throw createUnsupportedOperationException("createElementNS");
	}

	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
		throw createUnsupportedOperationException("createAttributeNS");
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw createUnsupportedOperationException("getElementsByTagNameNS");
	}

	@Override
	public String getInputEncoding() {
		throw createUnsupportedOperationException("getInputEncoding");
	}

	@Override
	public String getXmlEncoding() {
		throw createUnsupportedOperationException("getXmlEncoding");
	}

	@Override
	public boolean getXmlStandalone() {
		throw createUnsupportedOperationException("getXmlStandalone");
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) {
		throw createUnsupportedOperationException("setXmlStandalone");
	}

	@Override
	public String getXmlVersion() {
		throw createUnsupportedOperationException("getXmlVersion");
	}

	@Override
	public void setXmlVersion(String xmlVersion) {
		throw createUnsupportedOperationException("setXmlVersion");
	}

	@Override
	public boolean getStrictErrorChecking() {
		throw createUnsupportedOperationException("getStrictErrorChecking");
	}

	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		throw createUnsupportedOperationException("setStrictErrorChecking");
	}

	@Override
	public String getDocumentURI() {
		throw createUnsupportedOperationException("getDocumentURI");
	}

	@Override
	public void setDocumentURI(String documentURI) {
		throw createUnsupportedOperationException("setDocumentURI");
	}

	@Override
	public Node adoptNode(Node source) {
		throw createUnsupportedOperationException("adoptNode");
	}

	@Override
	public DOMConfiguration getDomConfig() {
		throw createUnsupportedOperationException("getDomConfig");
	}

	@Override
	public void normalizeDocument() {
		throw createUnsupportedOperationException("normalizeDocument");
	}

	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) {
		throw createUnsupportedOperationException("renameNode");
	}
}
