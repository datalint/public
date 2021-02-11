package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.Join;

public class Descendants extends Join {
	public Descendants(IExpression first, IExpression[] expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append("//");
	}
}
