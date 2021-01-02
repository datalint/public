package gwt.xml.shared.xml;

import gwt.xml.shared.IXmlExpression;

public class XmlText implements IXmlExpression {
	private final String escapedText;

	public XmlText(String text) {
		escapedText = escapeContent(text);
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(escapedText);
	}
}
