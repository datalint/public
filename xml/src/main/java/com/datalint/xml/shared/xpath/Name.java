package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Name extends UnaryExpression {
	public Name(IXPathExpression expression) {
		super(expression);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("name(");
	}
}
