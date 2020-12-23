package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Predicate extends UnaryExpression {
	public Predicate(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append('[');
	}

	@Override
	protected StringBuilder close(StringBuilder target) {
		return target.append(']');
	}
}
