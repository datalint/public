package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.UnaryExpression;

public class Not extends UnaryExpression {
	public Not(IExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("not(");
	}
}
