package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Not extends UnaryExpression {
	public Not(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("not(");
	}
}
