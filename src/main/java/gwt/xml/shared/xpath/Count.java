package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Count extends UnaryExpression {
	public Count(IXPathExpression expression) {
		super(expression);
	}

	protected StringBuilder open(StringBuilder target) {
		return target.append("count(");
	}
}
