package gwt.xml.shared.expression;

public class ArityExpression extends UnaryExpression {
	protected final IExpression[] expressions;

	protected ArityExpression(IExpression expression, IExpression... expressions) {
		super(expression);

		this.expressions = expressions;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		expression.append(open(target));

		for (IExpression expression : expressions) {
			expression.append(between(target));
		}

		return close(target);
	}

	protected StringBuilder between(StringBuilder target) {
		return target.append(',');
	}
}
