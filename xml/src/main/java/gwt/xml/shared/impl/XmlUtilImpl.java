package gwt.xml.shared.impl;

import gwt.xml.shared.ICommon;
import gwt.xml.shared.XPath;
import gwt.xml.shared.common.CollectionMode;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class XmlUtilImpl implements ICommon {
	private static final XmlUtilImpl impl = new XmlUtilImpl();

	private XmlUtilImpl() {
	}

	public static XmlUtilImpl getInstance() {
		return impl;
	}

	public Object getJsObject(Node node) {
		throw iCreateUoException("getJsObject (server side)");
	}

	public void updateElementViaInnerHTML(Element updateElement, Element referenceElement,
										  Object currentElementObject) {
		throw iCreateUoException("updateElementViaInnerHTML (server side)");
	}

	public int getHashCode(Element element) {
		return element.hashCode();
	}

	public String toString(Node node) {
		return String.valueOf(node);
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

	public boolean equals(Attr attrOne, Attr attrTwo) {
		if (attrOne == null)
			return attrTwo == null;

		return attrTwo != null && (attrOne.equals(attrTwo)
				|| attrOne.getName().equals(attrTwo.getName()) && attrOne.getValue().equals(attrTwo.getValue()));
	}
}
