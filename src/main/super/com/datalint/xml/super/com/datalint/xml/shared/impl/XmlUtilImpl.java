package com.datalint.xml.shared.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.datalint.xml.shared.XPath;
import com.datalint.xml.shared.common.CollectionMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

public class XmlUtilImpl {
	private static XmlUtilImpl impl = GWT.create(XmlUtilImpl.class);

	public static XmlUtilImpl getInstance() {
		return impl;
	}

	XmlUtilImpl() {
	}

	public int getHashCode(Element element) {
		return getJsObject(element).hashCode();
	}

	public String toString(Node node) {
		return String.valueOf(node);
	}

	public Node build(final JavaScriptObject jsObject) {
		return NodeImpl.build(jsObject);
	}

	public JavaScriptObject getJsObject(final Node node) {
		final NodeImpl impl = (NodeImpl) node;

		return impl.getJsObject();
	}

	public void updateElementViaInnerHTML(Element updateElement, Element referenceElement,
			com.google.gwt.dom.client.Element currentElement) {
		if (childNodesChanged(updateElement, referenceElement)) {
			String updateElementString = updateElement.toString();

			currentElement.setInnerHTML(updateElementString.substring(updateElementString.indexOf('>') + 1,
					updateElementString.lastIndexOf('<')));

			return;
		}

		com.google.gwt.dom.client.Element currentChildElement = currentElement.getFirstChildElement();
		if (currentChildElement == null)
			return;

		Node updateChild = updateElement.getFirstChild();
		Node referenceChild = referenceElement.getFirstChild();

		while (updateChild != null) {
			if (updateChild.getNodeType() == Node.ELEMENT_NODE) {
				updateElementViaInnerHTML((Element) updateChild, (Element) referenceChild, currentChildElement);

				currentChildElement = currentChildElement.getNextSiblingElement();
				if (currentChildElement == null)
					return;
			}

			updateChild = updateChild.getNextSibling();
			referenceChild = referenceChild.getNextSibling();

		}
	}

	private boolean childNodesChanged(Element updateElement, Element referenceElement) {
		if (updateElement.getChildNodes().getLength() != referenceElement.getChildNodes().getLength())
			return true;

		Node updateChild = updateElement.getFirstChild();
		Node referenceChild = referenceElement.getFirstChild();

		while (updateChild != null) {
			if (!(updateChild.getNodeName().equals(referenceChild.getNodeName())
					&& String.valueOf(updateChild.getNodeValue()).trim()
							.equals(String.valueOf(referenceChild.getNodeValue()).trim()))) {
				return true;
			} else if (updateChild.getNodeType() == Node.ELEMENT_NODE
					&& attributesChanged((Element) updateChild, (Element) referenceChild)) {
				return true;
			}
			updateChild = updateChild.getNextSibling();
			referenceChild = referenceChild.getNextSibling();
		}

		return false;
	}

	private boolean attributesChanged(Element updateElement, Element referenceElement) {
		NamedNodeMap updateAttributes = updateElement.getAttributes();

		if (updateAttributes.getLength() != referenceElement.getAttributes().getLength())
			return true;

		for (int i = 0; i < updateAttributes.getLength(); i++) {
			Node updateNode = updateAttributes.item(i);

			String nodeName = updateNode.getNodeName();

			String currentValue = referenceElement.getAttribute(nodeName);

			if (currentValue == null || !currentValue.equals(updateNode.getNodeValue()))
				return true;
		}

		return false;
	}

	public void updateElement(Element updateElement, Element referenceElement,
			com.google.gwt.dom.client.Element currentElement) {
		List<Element> updateElements = XPath.evaluateNodes(updateElement, ".//*");
		List<Element> referenceElements = XPath.evaluateNodes(referenceElement, ".//*");

		if (!isQuickUpdateAllowed(updateElements, referenceElements)) {
			normalUpdate(updateElement, referenceElement, currentElement);

			return;
		}

		// Quick Update Allowed
		updateAttributes(updateElement, referenceElement, currentElement);

		com.google.gwt.dom.client.NodeList<com.google.gwt.dom.client.Element> currentElements = currentElement
				.getElementsByTagName("*");

		for (int i = 0; i < updateElements.size(); i++) {
			updateAttributes(updateElements.get(i), referenceElements.get(i), currentElements.getItem(i));
		}
	}

	private boolean isQuickUpdateAllowed(List<Element> updateElements, List<Element> referenceElements) {
		if (updateElements.size() != referenceElements.size())
			return false;

		for (int i = 0; i < updateElements.size(); i++) {
			if (!updateElements.get(i).getNodeName().equals(referenceElements.get(i).getNodeName()))
				return false;
		}

		return true;
	}

	private void normalUpdate(Element updateElement, Element referenceElement,
			com.google.gwt.dom.client.Element currentElement) {
		// TODO Normal Update Required
	}

	private void updateAttributes(Element updateElement, Element referenceElement,
			com.google.gwt.dom.client.Element currentElement) {
		NamedNodeMap updateAttributes = updateElement.getAttributes();
		for (int i = 0; i < updateAttributes.getLength(); i++) {
			Node updateNode = updateAttributes.item(i);

			String nodeName = updateNode.getNodeName();

			String currentValue = referenceElement.getAttribute(nodeName);

			if (currentValue == null) {
				currentElement.setAttribute(nodeName, updateNode.getNodeValue());
			} else {
				if (!currentValue.equals(updateNode.getNodeValue()))
					currentElement.setAttribute(nodeName, updateNode.getNodeValue());

				referenceElement.removeAttribute(nodeName);
			}
		}

		NamedNodeMap referenceAttributes = referenceElement.getAttributes();
		for (int i = 0; i < referenceAttributes.getLength(); i++) {
			currentElement.removeAttribute(referenceAttributes.item(i).getNodeName());
		}
	}

	public boolean equals(Node nodeOne, Node nodeTwo) {
		assert nodeOne != null && nodeTwo != null;

		if (nodeOne.equals(nodeTwo))
			return true;

		if (nodeOne instanceof Element && nodeTwo instanceof Element)
			return equals((Element) nodeOne, (Element) nodeTwo);
		else if (nodeOne instanceof Attr && nodeTwo instanceof Attr)
			return equals((Attr) nodeOne, (Attr) nodeTwo);

		return false;
	}

	public boolean equals(Element elementOne, Element elementTwo) {
		assert elementOne != null && elementTwo != null;

		if (elementOne.equals(elementTwo))
			return true;

		if (!elementOne.getTagName().equals(elementTwo.getTagName()))
			return false;

		NamedNodeMap attributesOne = elementOne.getAttributes();
		NamedNodeMap attributesTwo = elementTwo.getAttributes();

		if (attributesOne.getLength() != attributesTwo.getLength())
			return false;

		for (int i = 0; i < attributesOne.getLength(); i++) {
			Node attribute = attributesOne.item(i);

			String nodeName = attribute.getNodeName();

			if (isTemporaryNode(nodeName))
				continue;

			if (!Objects.equals(attribute.getNodeValue(), elementTwo.getAttribute(nodeName)))
				return false;
		}

		if (!Objects.equals(XPath.evaluateText(elementOne), XPath.evaluateText(elementTwo)))
			return false;

		Set<Element> childrenOne = XPath.evaluateElements(elementOne, CollectionMode.insertOrdered);
		Set<Element> childrenTwo = XPath.evaluateElements(elementTwo, CollectionMode.insertOrdered);

		if (childrenOne.size() != childrenTwo.size())
			return false;

		Iterator<Element> iteratorTwo = childrenTwo.iterator();
		for (Element element : childrenOne) {
			if (!equals(element, iteratorTwo.next()))
				return false;
		}

		return true;
	}

	public boolean isTemporaryNode(String nodeName) {
		return false;
	}

	public boolean equals(Attr attrOne, Attr attrTwo) {
		if (attrOne == null)
			return attrTwo == null;

		return attrTwo != null && (attrOne.equals(attrTwo)
				|| attrOne.getName().equals(attrTwo.getName()) && attrOne.getValue().equals(attrTwo.getValue()));
	}
}
