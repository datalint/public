package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Translate extends ArityExpression {
	public Translate(IXPathExpression first, IXPathExpression second, IXPathExpression third) {
		super(first, second, third);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("translate(");
	}
}
