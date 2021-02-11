package gwt.xml.shared.xml;

import gwt.xml.shared.expression.IExpression;

public class XmlText implements IExpression {
	private final String escapedText;

	public XmlText(String text) {
		escapedText = escapeContent(text);
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(escapedText);
	}
}
