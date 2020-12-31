package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Predicate extends UnaryExpression {
	public Predicate(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append('[');
	}

	@Override
	protected StringBuilder close(StringBuilder target) {
		return target.append(']');
	}
}
