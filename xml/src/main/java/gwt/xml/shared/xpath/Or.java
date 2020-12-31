package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Or extends Join {
	public Or(IXPathExpression first, IXPathExpression... expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append(" or ");
	}
}
