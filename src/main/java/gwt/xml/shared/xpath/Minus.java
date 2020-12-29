package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Minus extends Join {
	public Minus(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append('-');
	}
}
