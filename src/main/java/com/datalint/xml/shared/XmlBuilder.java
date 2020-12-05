package com.datalint.xml.shared;

import java.util.Arrays;
import java.util.List;

import com.datalint.xml.shared.xml.XmlAttribute;
import com.datalint.xml.shared.xml.XmlElement;

public class XmlBuilder {
	public static XmlElement createXmlElement(String tagName, List<String> attributes) {
		return new XmlElement(tagName, createAttrExpressions(attributes));
	}

	public static XmlElement createXmlElement(String tagName, String... attributes) {
		return new XmlElement(tagName, createAttrExpressions(Arrays.asList(attributes)));
	}

	private static IXmlExpression[] createAttrExpressions(List<String> attributes) {
		IXmlExpression[] expressions = new IXmlExpression[attributes.size() >> 1];

		for (int i = 0, j = 0; i < expressions.length; i++, j++) {
			expressions[i] = new XmlAttribute(attributes.get(j++), attributes.get(j));
		}

		return expressions;
	}

	private XmlBuilder() {
	}
}
