package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class UnaryExpression implements IXPathExpression {
	protected final IXPathExpression expression;

	public UnaryExpression(IXPathExpression expression) {
		this.expression = expression;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return close(appendExpression(open(target)));
	}

	protected StringBuilder appendExpression(StringBuilder target) {
		if (expression == null)
			return target;

		return expression.append(target);
	}

	protected StringBuilder open(StringBuilder target) {
		return target.append('(');
	}

	protected StringBuilder close(StringBuilder target) {
		return target.append(')');
	}
}
