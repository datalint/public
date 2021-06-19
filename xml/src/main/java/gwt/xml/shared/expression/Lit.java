package gwt.xml.shared.expression;

public class Lit implements IExpression {
	private final Object value;

	public static IExpression create(Object value) {
		return value instanceof IExpression ? (IExpression) value : new Lit(value);
	}

	public static IExpression[] create(Object... values) {
		IExpression[] expressions = new Lit[values.length];

		for (int i = 0; i < values.length; i++) {
			expressions[i] = create(values[i]);
		}

		return expressions;
	}

	public Lit(Object value) {
		this.value = value;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(value);
	}
}
