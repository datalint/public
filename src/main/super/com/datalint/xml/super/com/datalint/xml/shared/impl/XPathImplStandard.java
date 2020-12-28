package com.datalint.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

class XPathImplStandard extends XPathImpl {
	private static final JavaScriptObject resolver = getResolver();

	private native static JavaScriptObject getResolver() /*-{
		return function resolver(prefix) {
			var ns = {
				'xsl' : 'http://www.w3.org/1999/XSL/Transform',
				'svg' : 'http://www.w3.org/2000/svg'
			};
			return ns[prefix] || null;
		};
	}-*/;

	@Override
	protected native JavaScriptObject evaluateNodesImpl(JavaScriptObject element, String xPath) /*-{
		return element.ownerDocument.evaluate(xPath, element,
				@com.datalint.xml.shared.impl.XPathImplStandard::resolver,
				XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
	}-*/;

	@Override
	protected native JsArray<JavaScriptObject> evaluateNodesImpl(JavaScriptObject nodes) /*-{
		var result = new Array();
		var currentNode = nodes.iterateNext();
		while (currentNode) {
			result.push(currentNode);
			currentNode = nodes.iterateNext();
		}

		return result;
	}-*/;

	@Override
	protected native JavaScriptObject evaluateNodeImpl(JavaScriptObject element, String xPath) /*-{
		return element.ownerDocument.evaluate(xPath, element,
				@com.datalint.xml.shared.impl.XPathImplStandard::resolver,
				XPathResult.ANY_UNORDERED_NODE_TYPE, null).singleNodeValue;
	}-*/;

	@Override
	protected native String evaluateStringImpl(JavaScriptObject element, String xPath) /*-{
		return element.ownerDocument.evaluate(xPath, element,
				@com.datalint.xml.shared.impl.XPathImplStandard::resolver,
				XPathResult.STRING_TYPE, null).stringValue;
	}-*/;

	@Override
	protected native double evaluateNumberImpl(JavaScriptObject element, String xPath) /*-{
		return element.ownerDocument.evaluate(xPath, element,
				@com.datalint.xml.shared.impl.XPathImplStandard::resolver,
				XPathResult.NUMBER_TYPE, null).numberValue;
	}-*/;

	@Override
	protected native int evaluatePosition(JavaScriptObject nodes, JavaScriptObject reference) /*-{
		var result = 0;
		var currentNode = nodes.iterateNext();
		while (currentNode) {
			result++;
			if (currentNode == reference)
				return result;

			currentNode = nodes.iterateNext();
		}

		return 0;
	}-*/;
}
