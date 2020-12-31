package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Descendants extends Join {
	public Descendants(IXPathExpression first, IXPathExpression[] expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append("//");
	}
}
