package com.datalint.xml.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.datalint.xml.shared.common.CollectionMode;
import com.datalint.xml.shared.common.TriConsumer;
import com.datalint.xml.shared.impl.XPathImpl;

public class XPath implements ICommon {
	private static XPathImpl impl = XPathImpl.getInstance();

	public static Comparator<Element> createComparator(String xPath) {
		return (o1, o2) -> XPath.evaluateString(o1, xPath).compareTo(XPath.evaluateString(o2, xPath));
	}

	public static Comparator<Element> createDescendingComparator(String xPath) {
		return (o1, o2) -> XPath.evaluateString(o2, xPath).compareTo(XPath.evaluateString(o1, xPath));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Node> List<T> evaluateNodes(Element element, String xPath) {
		return (List<T>) impl.evaluateNodes(element, xPath);
	}

	public static <T extends Node> List<T> evaluateNodes(Document document, String xPath) {
		return evaluateNodes(document.getDocumentElement(), xPath);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Node> T evaluateNode(Element element, String xPath) {
		return (T) impl.evaluateNode(element, xPath);
	}

	public static <T extends Node> T evaluateNode(Document document, String xPath) {
		return evaluateNode(document.getDocumentElement(), xPath);
	}

	public static String evaluateString(Element element, String xPath) {
		return impl.evaluateString(element, xPath);
	}

	public static String evaluateString(Document document, String xPath) {
		return evaluateString(document.getDocumentElement(), xPath);
	}

	public static double evaluateNumber(Element element, String xPath) {
		return impl.evaluateNumber(element, xPath);
	}

	public static double evaluateNumber(Document document, String xPath) {
		return evaluateNumber(document.getDocumentElement(), xPath);
	}

	public static int evaluatePosition(Element element, String xPath, Element reference) {
		return impl.evaluatePosition(element, xPath, reference);
	}

	public static int evaluatePosition(Document document, String xPath, Element reference) {
		return evaluatePosition(document.getDocumentElement(), xPath, reference);
	}

	public static int evaluatePosition(Element element, Element reference) {
		return evaluatePosition(element, WILDCARD, reference);
	}

	public static int evaluatePosition(Document document, Element reference) {
		return evaluatePosition(document.getDocumentElement(), reference);
	}

	public static String[] evaluateArrayAttrValues(Element element, String xPathAttr) {
		return impl.evaluateAttrValues(element, xPathAttr);
	}

	public static String[] evaluateArrayAttrValues(Document document, String xPathAttr) {
		return evaluateArrayAttrValues(document.getDocumentElement(), xPathAttr);
	}

	public static List<String> evaluateListAttrValues(Element element, String xPathAttr) {
		return Arrays.asList(evaluateArrayAttrValues(element, xPathAttr));
	}

	public static List<String> evaluateListAttrValues(Document document, String xPathAttr) {
		return evaluateListAttrValues(document.getDocumentElement(), xPathAttr);
	}

	public static Set<String> evaluateAttrValues(Element element, String xPathAttr, CollectionMode mode) {
		return impl.evaluateAttrValues(element, xPathAttr, mode);
	}

	public static Set<String> evaluateAttrValues(Document document, String xPathAttr, CollectionMode mode) {
		return evaluateAttrValues(document.getDocumentElement(), xPathAttr, mode);
	}

	public static Set<String> evaluateAttrValues(Element element, String xPathAttr) {
		return evaluateAttrValues(element, xPathAttr, CollectionMode.unordered);
	}

	public static Set<String> evaluateAttrValues(Document document, String xPathAttr) {
		return evaluateAttrValues(document.getDocumentElement(), xPathAttr);
	}

	public static List<String> evaluateListTextValues(Element element, String xPathText) {
		List<Text> textNodes = evaluateNodes(element, xPathText);

		List<String> textValues = new ArrayList<>();
		for (Text textNode : textNodes) {
			textValues.add(textNode.getNodeValue());
		}

		return textValues;
	}

	public static List<String> evaluateListTextValues(Document document, String xPathText) {
		return evaluateListTextValues(document.getDocumentElement(), xPathText);
	}

	public static Set<String> evaluateTextValues(Element element, String xPathText, CollectionMode mode) {
		return impl.evaluateTextValues(element, xPathText, mode);
	}

	public static Set<String> evaluateTextValues(Document document, String xPathText, CollectionMode mode) {
		return evaluateTextValues(document.getDocumentElement(), xPathText, mode);
	}

	public static Set<String> evaluateTextValues(Element element, String xPathText) {
		return evaluateTextValues(element, xPathText, CollectionMode.unordered);
	}

	public static Set<String> evaluateTextValues(Document document, String xPathText) {
		return evaluateTextValues(document.getDocumentElement(), xPathText);
	}

	public static Set<Element> evaluateElements(Element element, String xPath, CollectionMode mode) {
		return impl.evaluateElements(element, xPath, mode);
	}

	public static Set<Element> evaluateElements(Document document, String xPath, CollectionMode mode) {
		return evaluateElements(document.getDocumentElement(), xPath, mode);
	}

	public static Set<Element> evaluateElements(Element element, CollectionMode mode) {
		return evaluateElements(element, WILDCARD, mode);
	}

	public static Set<Element> evaluateElements(Document document, CollectionMode mode) {
		return evaluateElements(document.getDocumentElement(), mode);
	}

	public static Set<Element> evaluateElements(Element element) {
		return evaluateElements(element, CollectionMode.unordered);
	}

	public static Set<Element> evaluateElements(Document document) {
		return evaluateElements(document.getDocumentElement());
	}

	public static Set<Element> evaluateElements(Element element, String xPath) {
		return evaluateElements(element, xPath, CollectionMode.unordered);
	}

	public static Set<Element> evaluateElements(Document document, String xPath) {
		return evaluateElements(document.getDocumentElement(), xPath);
	}

	public static Map<String, Element> evaluateElementsMap(Element element, String xPath, String attributeName,
			CollectionMode mode) {
		return impl.evaluateElementsMap(element, xPath, attributeName, mode, Lazy.elementsMapConsumer);
	}

	public static Map<String, Element> evaluateElementsMap(Document document, String xPath, String attributeName,
			CollectionMode mode) {
		return evaluateElementsMap(document.getDocumentElement(), xPath, attributeName, mode);
	}

	public static Map<String, Element> evaluateElementsMap(Element element, String xPath, String attributeName) {
		return evaluateElementsMap(element, xPath, attributeName, CollectionMode.unordered);
	}

	public static Map<String, Element> evaluateElementsMap(Document document, String xPath, String attributeName) {
		return evaluateElementsMap(document.getDocumentElement(), xPath, attributeName);
	}

	public static Map<String, List<Element>> evaluateListElementsMap(Element element, String xPath,
			String attributeName, CollectionMode mode) {
		return impl.evaluateElementsMap(element, xPath, attributeName, mode, Lazy.listElementsMapConsumer);
	}

	public static Map<String, List<Element>> evaluateListElementsMap(Document document, String xPath,
			String attributeName, CollectionMode mode) {
		return evaluateListElementsMap(document.getDocumentElement(), xPath, attributeName, mode);
	}

	public static Map<String, List<Element>> evaluateListElementsMap(Element element, String xPath,
			String attributeName) {
		return evaluateListElementsMap(element, xPath, attributeName, CollectionMode.unordered);
	}

	public static Map<String, List<Element>> evaluateListElementsMap(Document document, String xPath,
			String attributeName) {
		return evaluateListElementsMap(document.getDocumentElement(), xPath, attributeName);
	}

	public static Element evaluateElement(Element element) {
		return evaluateNode(element, WILDCARD);
	}

	public static Element evaluateElement(Document document) {
		return evaluateElement(document.getDocumentElement());
	}

	public static int evaluateNumber(Element element) {
		return (int) evaluateNumber(element, COUNT_ALL);
	}

	public static int evaluateNumber(Document document) {
		return evaluateNumber(document.getDocumentElement());
	}

	public static String evaluateText(Element element) {
		return evaluateString(element, TEXT_F);
	}

	public static String evaluateText(Document document) {
		return evaluateText(document.getDocumentElement());
	}

	private static class Lazy {
		private static TriConsumer<Map<String, Element>, String, Element> elementsMapConsumer = (map, key, item) -> map
				.put(key, item);

		private static TriConsumer<Map<String, List<Element>>, String, Element> listElementsMapConsumer = (map, key,
				item) -> {
			List<Element> value = map.get(key);

			if (value == null) {
				value = new ArrayList<>();

				map.put(key, value);
			}

			value.add(item);
		};
	}
}
