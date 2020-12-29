package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class ArityExpression extends UnaryExpression {
	protected final IXPathExpression[] expressions;

	protected ArityExpression(IXPathExpression expression, IXPathExpression... expressions) {
		super(expression);

		this.expressions = expressions;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		expression.append(open(target));

		for (IXPathExpression expression : expressions) {
			expression.append(between(target));
		}

		return close(target);
	}

	protected StringBuilder between(StringBuilder target) {
		return target.append(',');
	}
}
