package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Lit implements IXPathExpression {
	private final String value;

	public Lit(String value) {
		this.value = value;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(value);
	}
}
