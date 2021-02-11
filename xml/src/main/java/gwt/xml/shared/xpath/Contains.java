package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.ArityExpression;
import gwt.xml.shared.expression.IExpression;

public class Contains extends ArityExpression {
	public Contains(IExpression first, IExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("contains(");
	}
}
