package gwt.xml.shared.expression;

public class Lit implements IExpression {
	private final Object value;

	public Lit(Object value) {
		this.value = value;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(value);
	}
}
