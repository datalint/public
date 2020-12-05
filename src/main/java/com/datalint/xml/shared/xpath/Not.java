package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Not extends UnaryExpression {
	public Not(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("not(");
	}
}
