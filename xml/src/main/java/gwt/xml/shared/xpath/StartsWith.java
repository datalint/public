package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class StartsWith extends ArityExpression {
	public StartsWith(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("starts-with(");
	}
}
