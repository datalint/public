package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.Join;

public class Path extends Join {
	public Path(IExpression first, IExpression[] expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append('/');
	}
}
