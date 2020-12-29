package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Less extends Join {
	public Less(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append(_LESS_THAN);
	}
}
