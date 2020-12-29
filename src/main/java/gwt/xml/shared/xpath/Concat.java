package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Concat implements IXPathExpression {
	private final IXPathExpression first;
	private final IXPathExpression[] expressions;

	public Concat(IXPathExpression first, IXPathExpression... expressions) {
		this.first = first;
		this.expressions = expressions;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		first.append(target.append("concat("));

		for (IXPathExpression expression : expressions) {
			expression.append(target.append(','));
		}

		return target.append(')');
	}
}
