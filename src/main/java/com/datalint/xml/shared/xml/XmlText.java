package com.datalint.xml.shared.xml;

import com.datalint.xml.shared.IXmlExpression;
import com.google.common.xml.XmlEscapers;

public class XmlText implements IXmlExpression {
	private final String escapedText;

	public XmlText(String text) {
		escapedText = XmlEscapers.xmlContentEscaper().escape(text);
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(escapedText);
	}
}
