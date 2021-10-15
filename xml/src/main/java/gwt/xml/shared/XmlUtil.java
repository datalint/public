package gwt.xml.shared;

import com.google.common.collect.Sets;
import gwt.xml.shared.impl.XmlUtilImpl;
import org.w3c.dom.*;

import javax.annotation.Nullable;
import java.util.*;

public class XmlUtil implements ICommon {
	private static final String ROOT_WRAPPER = "root";

	private static final XmlUtil instance = new XmlUtil();

	private XmlUtil() {
	}

	public static String escapeAttrStatic(String attribute) {
		return instance.escapeAttr(attribute);
	}

	public static List<Element> getAncestors(Node descendant) {
		List<Element> ancestors = new ArrayList<>();

		while ((descendant = descendant.getParentNode()) instanceof Element) {
			ancestors.add((Element) descendant);
		}

		return ancestors;
	}

	public static List<Element> getAncestors(Element descendant, Element ancestor) {
		if (descendant.equals(ancestor))
			return Collections.singletonList(descendant);

		List<Element> ancestors = new ArrayList<>();

		do {
			ancestors.add(descendant);
		} while (!(descendant = (Element) descendant.getParentNode()).equals(ancestor));

		ancestors.add(ancestor);

		return ancestors;
	}

	public static String createHTML(String tagName, @Nullable String className, @Nullable String childHTML) {
		int size = className == null ? 0 : 2;

		if (childHTML != null)
			size++;

		Object[] childAndOrAttributes = new String[size];

		if (size > 1) {
			childAndOrAttributes[size - 2] = "class";
			childAndOrAttributes[size - 1] = className;
		}

		if (childHTML != null)
			childAndOrAttributes[0] = childHTML;

		return createElement(tagName, childAndOrAttributes).toString();
	}

	public static String createXml(String tagName, String text) {
		Document document = XmlParser.createDocument();

		Element element = document.createElement(tagName);
		document.appendChild(element);

		setText(element, text);

		return XmlUtil.toString(document);
	}

	public static String getItem(Element item, @Nullable String locale) {
		if (!instance.isEmpty(locale)) {
			if (item.hasAttribute(locale))
				return item.getAttribute(locale);

			int lastIndexOf = locale.lastIndexOf(_UNDERSCORE);
			if (lastIndexOf > 0)
				return getItem(item, locale.substring(0, lastIndexOf));
		}

		return XPath.evaluateText(item);
	}

	public static boolean getPrimitiveBoolean(Element element, String attributeName) {
		return element != null && element.hasAttribute(attributeName);
	}

	public static Boolean getBooleanValue(Element element, String attributeName) {
		if (element == null || !element.hasAttribute(attributeName))
			return null;

		return Boolean.valueOf(element.getAttribute(attributeName));
	}

	public static int getIntValue(Element element, String attributeName) {
		return getIntValue(element, attributeName, 0);
	}

	public static int getIntValue(Element element, String attributeName, int defaultValue) {
		String attributeValue = element.getAttribute(attributeName);

		if (instance.isEmpty(attributeValue))
			return defaultValue;

		return Integer.parseInt(attributeValue);
	}

	public static void increaseIntValue(Element element, String attributeName) {
		increaseIntValue(element, attributeName, 0);
	}

	public static void increaseIntValue(Element element, String attributeName, int defaultValue) {
		element.setAttribute(attributeName, "" + (getIntValue(element, attributeName, defaultValue) + 1));
	}

	public static void appendText(Element element, String text) {
		element.appendChild(element.getOwnerDocument().createTextNode(text));
	}

	public static boolean copyTextIfUnequal(Element element, String text) {
		Node textNode = XPath.evaluateNode(element, TEXT_F);

		if (textNode == null) {
			if (text != null && text.length() > 0) {
				appendText(element, text);

				return true;
			}
		} else if (!textNode.getNodeValue().equals(text)) {
			textNode.setNodeValue(text);

			return true;
		}

		return false;
	}

	public static boolean isBooleanAttributeChanged(Element one, Element two, String name) {
		return one.hasAttribute(name) ^ two.hasAttribute(name);
	}

	public static boolean isPrimaryNotNullAndNotSame(Element primary, Element secondary, String name) {
		String primaryAttribute = primary.getAttribute(name);

		return primaryAttribute != null && !primaryAttribute.equals(secondary.getAttribute(name));
	}

	public static boolean isNotNullAndSameAttribute(Element primary, Element secondary, String name) {
		return primary.hasAttribute(name) && primary.getAttribute(name).equals(secondary.getAttribute(name));
	}

	public static <T extends Node> List<T> parseAsList(String source) {
		return instance.isEmpty(source) ? Collections.emptyList()
				: XPath.evaluateNodes(parseWithRootParent(source), "*");
	}

	public static Element parseWithRootParent(String children, String... parentAttributes) {
		return parseWithParentElement(ROOT_WRAPPER, children, parentAttributes);
	}

	public static Element parseWithParentElement(String parentTagName, String children, String... parentAttributes) {
		StringBuilder sB = new StringBuilder(children.length() + 16);

		sB.append('<').append(parentTagName);

		for (int i = 0; i < parentAttributes.length; i++) {
			sB.append(' ').append(parentAttributes[i++]).append("='").append(parentAttributes[i]).append('\'');
		}

		sB.append('>').append(children);

		return XmlParser.parse(appendEndTag(sB, parentTagName).toString()).getDocumentElement();
	}

	/**
	 * Remove the referenceNode and all its previous sibling nodes from their
	 * parent.
	 *
	 * @param referenceNode The referenceNode until which itself and the previous
	 *                      sibling nodes be removed from their parent.
	 */
	public static void removePreviousSiblings(Node referenceNode) {
		removePreviousSiblings(referenceNode, null);
	}

	/**
	 * Remove the referenceNode and all its previous sibling nodes from their
	 * parent.
	 *
	 * @param referenceNode      The referenceNode until which itself and the
	 *                           previous sibling nodes be removed from their
	 *                           parent.
	 * @param removedNodesHolder A list holding the removed previous siblings, the
	 *                           first one in the removed holder is the last one in
	 *                           the original parent.
	 */
	public static void removePreviousSiblings(Node referenceNode, @Nullable List<Node> removedNodesHolder) {
		if (referenceNode == null)
			return;

		Node parentNode = referenceNode.getParentNode();
		do {
			if (removedNodesHolder != null)
				removedNodesHolder.add(referenceNode);

			Node previous = referenceNode.getPreviousSibling();
			parentNode.removeChild(referenceNode);
			referenceNode = previous;
		} while (referenceNode != null);
	}

	/**
	 * Remove the referenceNode and all its next sibling nodes from their parent.
	 *
	 * @param referenceNode The referenceNode from which itself and the next sibling
	 *                      nodes be removed from their parent.
	 */
	public static void removeNextSiblings(Node referenceNode) {
		if (referenceNode == null)
			return;

		Node parentNode = referenceNode.getParentNode();
		do {
			Node next = referenceNode.getNextSibling();
			parentNode.removeChild(referenceNode);
			referenceNode = next;
		} while (referenceNode != null);
	}

	/**
	 * Concatenate all the nodes between referenceNodeFrom (exclusive) and
	 * referenceNodeTo (exclusive). If the referenceNodeFrom is same the
	 * referenceNodeTo, the result will be empty.
	 *
	 * @param referenceNodeFrom The referenceNode from which itself and the next
	 *                          sibling nodes be concatenated, until
	 *                          referenceNodeTo.
	 * @param referenceNodeTo   The referenceNode, which stops the concatenation.
	 * @return the result string.
	 */
	public static String concatenateSiblings(Node referenceNodeFrom, Node referenceNodeTo) {
		if (referenceNodeFrom == null && referenceNodeTo == null)
			return EMPTY;

		if (referenceNodeFrom == null)
			referenceNodeFrom = referenceNodeTo.getParentNode().getFirstChild();
		else if (referenceNodeFrom.equals(referenceNodeTo))
			return EMPTY;
		else
			referenceNodeFrom = referenceNodeFrom.getNextSibling();

		StringBuilder sB = new StringBuilder();

		while (referenceNodeFrom != null) {
			if (referenceNodeFrom.equals(referenceNodeTo))
				break;

			sB.append(referenceNodeFrom);

			referenceNodeFrom = referenceNodeFrom.getNextSibling();
		}

		return sB.toString();
	}

	public static Element appendElement(Document document, String tagName) {
		return appendElement(document.getDocumentElement(), tagName);
	}

	public static Element appendElement(Element parent, String tagName) {
		return (Element) parent.appendChild(parent.getOwnerDocument().createElement(tagName));
	}

	public static Element createElementOnRequest(Element parent, String tagName) {
		return createElementOnRequest(parent, tagName, tagName);
	}

	public static Element createElementOnRequest(Element parent, String xPath, String tagName) {
		Element child = XPath.evaluateNode(parent, xPath);

		if (child == null) {
			child = parent.getOwnerDocument().createElement(tagName);

			parent.appendChild(child);
		}

		return child;
	}

	public static Element appendElementWithText(Element parentElement, String childTagName, String text) {
		return (Element) parentElement
				.appendChild(createElementWithText(parentElement.getOwnerDocument(), childTagName, text));
	}

	public static Element createElementWithText(Document document, String tagName, String text) {
		Element element = document.createElement(tagName);

		element.appendChild(document.createTextNode(text));

		return element;
	}

	public static Element getFirstChildElement(Element parentElement) {
		Node child = parentElement.getFirstChild();

		while (child != null) {
			if (child instanceof Element)
				return (Element) child;

			child = child.getNextSibling();
		}

		return null;
	}

	public static boolean equals(Element elementOne, Element elementTwo) {
		return XmlUtilImpl.getInstance().equals(elementOne, elementTwo);
	}

	public static int getHashCode(Element element) {
		return XmlUtilImpl.getInstance().getHashCode(element);
	}

	public static String toString(Node node) {
		return XmlUtilImpl.getInstance().toString(node);
	}

	public static String serializeToString(String name, String value) {
		return name + _EQUALS + _QUOTE + escapeAttrStatic(value) + _QUOTE;
	}

	public static String serializeToString(Node node) {
		return instance.serializeToStringImpl(node);
	}

	private String serializeToStringImpl(Node node) {
		if (node == null)
			return EMPTY;

		if (node instanceof Document)
			return serializeToStringImpl(((Document) node).getDocumentElement());
		else if (node instanceof Text)
			return escapeContent(node.getNodeValue());
		else if (node instanceof Element) {
			StringBuilder sB = new StringBuilder();

			sB.append(_LESS_THAN).append(node.getNodeName());

			NamedNodeMap attributes = node.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Node item = attributes.item(i);
				sB.append(_SPACE).append(serializeToString(item.getNodeName(), item.getNodeValue()));
			}

			NodeList childNodes = node.getChildNodes();
			int length = childNodes.getLength();
			if (length == 0)
				sB.append(_SLASH).append(_GREATER_THAN);
			else {
				sB.append(_GREATER_THAN);

				for (int i = 0; i < length; i++)
					sB.append(serializeToStringImpl(childNodes.item(i)));

				sB.append(_LESS_THAN).append(_SLASH).append(node.getNodeName()).append(_GREATER_THAN);
			}

			return sB.toString();
		}

		return '[' + node.getNodeName() + ": " + node.getNodeValue() + ']';
	}

	public static void copyChildren(Node src, Node target) {
		assert src != null && target != null;

		NodeList childNodes = src.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			target.appendChild(childNodes.item(i).cloneNode(true));
		}
	}

	public static Element copyAttributes(Element src, Element dest, String... attributesName) {
		for (String name : attributesName) {
			String value = src.getAttribute(name);
			if (value != null)
				dest.setAttribute(name, value);
			else
				dest.removeAttribute(name);
		}

		return dest;
	}

	public static Element copyAttributesB(Element src, Element dest, String... skippedNames) {
		return copyAttributesB(src, dest, true, skippedNames);
	}

	private static Map<String, String> createAttributesMap(Element element) {
		NamedNodeMap attributes = element.getAttributes();
		Map<String, String> attributesMap = new HashMap<>(attributes.getLength());
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			attributesMap.put(attribute.getNodeName(), attribute.getNodeValue());
		}

		return attributesMap;
	}

	public static Element copyAttributesB(Element src, Element dest, boolean remove, String... skippedNames) {
		Map<String, String> destAttrsMap = remove ? createAttributesMap(dest) : Collections.emptyMap();

		NamedNodeMap srcAttrs = src.getAttributes();
		Set<String> skippedNameSet = skippedNames.length == 0 ? Collections.emptySet() : Sets.newHashSet(skippedNames);
		for (int i = 0; i < srcAttrs.getLength(); i++) {
			Node srcAttr = srcAttrs.item(i);

			String name = srcAttr.getNodeName();
			String destValue = destAttrsMap.remove(name);

			if (skippedNameSet.contains(name) || (destValue != null && destValue.equals(srcAttr.getNodeValue())))
				continue;

			dest.setAttribute(name, srcAttr.getNodeValue());
		}

		if (remove && destAttrsMap.size() > 0) {
			for (String key : destAttrsMap.keySet()) {
				dest.removeAttribute(key);
			}
		}

		return dest;
	}

	public static boolean copyAttributeIfUnequal(Element src, Element dest, String attributeName) {
		String newAttribute = src.getAttribute(attributeName);
		String attribute = dest.getAttribute(attributeName);

		if (newAttribute == null && attribute == null)
			return false;
		else if (newAttribute == null)
			dest.removeAttribute(attributeName);
		else if (newAttribute.equals(attribute))
			return false;
		else
			dest.setAttribute(attributeName, newAttribute);

		return true;
	}

	public static void setAttribute(Element src, String name, @Nullable String value) {
		if (value == null)
			src.removeAttribute(name);
		else
			src.setAttribute(name, value);
	}

	public static void setAttribute(Element src, String name, boolean value) {
		assert src != null;

		if (value)
			src.setAttribute(name, String.valueOf(true));
		else
			src.removeAttribute(name);
	}

	public static void setText(Element element, String text) {
		List<Text> textNodes = XPath.evaluateNodes(element, "text()");

		if (textNodes.size() == 0) {
			appendText(element, text);

			return;
		}

		textNodes.get(0).setData(text);

		for (int i = 1; i < textNodes.size(); i++) {
			element.removeChild(textNodes.get(i));
		}
	}

	public static void moveChildren(Element source, Element target, String xPath) {
		List<Node> sourceChildren = XPath.evaluateNodes(source, xPath);

		for (Node sourceChild : sourceChildren) {
			target.appendChild(sourceChild);
		}
	}

	public static String createBeginTag(String tagName) {
		return appendBeginTag(new StringBuilder(tagName.length() + 2), tagName).toString();
	}

	public static String createEndTag(String tagName) {
		return appendEndTag(new StringBuilder(tagName.length() + 3), tagName).toString();
	}

	public static StringBuilder appendBeginTag(StringBuilder sB, String tagName, Object... attributes) {
		sB.append('<').append(tagName);

		assert instance.isEven(attributes.length);

		for (int i = 0; i < attributes.length; i++) {
			if (attributes[++i] == Boolean.FALSE || attributes[i] == null)
				continue;

			sB.append(' ').append(attributes[i - 1]).append("=\"").append(instance.escapeAttr(attributes[i].toString()))
					.append('"');
		}

		return sB.append('>');
	}

	public static StringBuilder appendEndTag(StringBuilder sB, String tagName) {
		return sB.append("</").append(tagName).append('>');
	}

	public static StringBuilder appendElement(StringBuilder sB, String tagName, Object... childAndOrAttributes) {
		sB.append('<').append(tagName);

		boolean isEven = instance.isEven(childAndOrAttributes.length);

		for (int i = isEven ? 0 : 1; i < childAndOrAttributes.length; i++) {
			if (childAndOrAttributes[++i] == Boolean.FALSE)
				continue;

			sB.append(' ').append(childAndOrAttributes[i - 1]).append("=\"")
					.append(instance.escapeAttr(childAndOrAttributes[i].toString())).append('"');
		}

		sB.append('>');

		if (!isEven && ((CharSequence) childAndOrAttributes[0]).length() > 0)
			sB.append((CharSequence) childAndOrAttributes[0]);

		return appendEndTag(sB, tagName);
	}

	public static StringBuilder createElement(String tagName, Object... childAndOrAttributes) {
		return appendElement(new StringBuilder(), tagName, childAndOrAttributes);
	}
}
