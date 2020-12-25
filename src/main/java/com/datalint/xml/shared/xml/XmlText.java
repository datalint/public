package com.datalint.xml.shared.xml;

import com.datalint.xml.shared.IXmlExpression;

public class XmlText implements IXmlExpression {
	private final String escapedText;

	public XmlText(String text) {
		escapedText = iEscapeContent(text);
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(escapedText);
	}
}
