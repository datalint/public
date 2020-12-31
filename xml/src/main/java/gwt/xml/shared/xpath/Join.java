package gwt.xml.shared.xpath;

import gwt.xml.shared.IXPathExpression;

public class Join implements IXPathExpression {
	private final IXPathExpression first;
	private final IXPathExpression[] expressions;

	public Join(IXPathExpression first, IXPathExpression... expressions) {
		this.first = first;
		this.expressions = expressions;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		first.append(target);

		for (IXPathExpression expression : expressions) {
			expression.append(operator(target));
		}

		return target;
	}

	protected StringBuilder operator(StringBuilder target) {
		return target;
	}
}
