package com.datalint.xml.shared.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.datalint.xml.shared.common.CollectionMode;
import com.datalint.xml.shared.common.TriConsumer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public abstract class XPathImpl {
	private static XPathImpl impl = GWT.create(XPathImpl.class);

	public static XPathImpl getInstance() {
		return impl;
	}

	XmlUtilImpl xMLUtil() {
		return XmlUtilImpl.getInstance();
	}

	public String[] evaluateAttrValues(Element element, String xPath) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);

		String[] attrValues = new String[jsArray.length()];

		for (int i = 0; i < jsArray.length(); i++) {
			attrValues[i] = XmlParserImpl.getValue(jsArray.get(i));
		}

		return attrValues;
	}

	public Set<String> evaluateAttrValues(Element element, String xPath, CollectionMode mode) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);

		Set<String> attrValues = mode.createSet(jsArray.length());

		appendAttrValues(attrValues, jsArray);

		return attrValues;
	}

	protected void appendAttrValues(Collection<String> attrValues, JsArray<JavaScriptObject> jsArray) {
		for (int i = 0; i < jsArray.length(); i++) {
			attrValues.add(XmlParserImpl.getValue(jsArray.get(i)));
		}
	}

	public Set<String> evaluateTextValues(Element element, String xPath, CollectionMode mode) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);

		Set<String> textValues = mode.createSet(jsArray.length());

		appendTextValues(textValues, jsArray);

		return textValues;
	}

	protected void appendTextValues(Collection<String> textValues, JsArray<JavaScriptObject> jsArray) {
		for (int i = 0; i < jsArray.length(); i++) {
			textValues.add(XmlParserImpl.getData(jsArray.get(i)));
		}
	}

	public Set<Element> evaluateElements(Element element, String xPath, CollectionMode mode) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);

		Set<Element> elements = mode.createSet(jsArray.length());
		for (int i = 0; i < jsArray.length(); i++) {
			elements.add((Element) xMLUtil().build(jsArray.get(i)));
		}

		return elements;
	}

	public <T> Map<String, T> evaluateElementsMap(Element element, String xPath, String attributeName,
			CollectionMode mode, TriConsumer<Map<String, T>, String, Element> consumer) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);

		Map<String, T> elementsMap = mode.createMap(jsArray.length());
		for (int i = 0; i < jsArray.length(); i++) {
			Element item = (Element) xMLUtil().build(jsArray.get(i));

			consumer.accept(elementsMap, item.getAttribute(attributeName), item);
		}

		return elementsMap;
	}

	public List<Node> evaluateNodes(Element element, String xPath) {
		JsArray<JavaScriptObject> jsArray = evaluateNodesImpl(element, xPath);
		List<Node> nodes = new ArrayList<>(jsArray.length());
		for (int i = 0; i < jsArray.length(); i++) {
			nodes.add(xMLUtil().build(jsArray.get(i)));
		}

		return nodes;
	}

	protected JsArray<JavaScriptObject> evaluateNodesImpl(Element element, String xPath) {
		return evaluateNodesImpl(evaluateNodesImpl(xMLUtil().getJsObject(element), xPath));
	}

	protected abstract JavaScriptObject evaluateNodesImpl(JavaScriptObject element, String xPath);

	protected abstract JsArray<JavaScriptObject> evaluateNodesImpl(JavaScriptObject nodes);

	public Node evaluateNode(Element element, String xPath) {
		JavaScriptObject jSO = evaluateNodeImpl(xMLUtil().getJsObject(element), xPath);

		if (jSO != null) {
			return xMLUtil().build(jSO);
		}

		return null;
	}

	protected abstract JavaScriptObject evaluateNodeImpl(JavaScriptObject element, String xPath);

	public String evaluateString(Element element, String xPath) {
		return evaluateStringImpl(xMLUtil().getJsObject(element), xPath);
	}

	protected abstract String evaluateStringImpl(JavaScriptObject element, String xPath);

	public double evaluateNumber(Element element, String xPath) {
		return evaluateNumberImpl(xMLUtil().getJsObject(element), xPath);
	}

	protected abstract double evaluateNumberImpl(JavaScriptObject element, String xPath);

	public int evaluatePosition(Element element, String xPath, Element reference) {
		return evaluatePosition(evaluateNodesImpl(xMLUtil().getJsObject(element), xPath),
				xMLUtil().getJsObject(reference));
	}

	protected abstract int evaluatePosition(JavaScriptObject nodes, JavaScriptObject reference);
}
