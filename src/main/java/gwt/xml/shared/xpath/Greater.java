package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Greater extends Join {
	public Greater(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append(_GREATER_THAN);
	}
}
