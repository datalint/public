package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Count extends UnaryExpression {
	public Count(IXPathExpression expression) {
		super(expression);
	}

	protected StringBuilder open(StringBuilder target) {
		return target.append("count(");
	}
}
