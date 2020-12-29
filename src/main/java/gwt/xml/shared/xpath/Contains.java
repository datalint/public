package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Contains extends ArityExpression {
	public Contains(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("contains(");
	}
}
