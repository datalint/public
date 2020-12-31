package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Name extends UnaryExpression {
	public Name(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("name(");
	}
}
