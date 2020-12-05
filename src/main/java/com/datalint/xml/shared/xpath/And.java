package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class And extends Join {
	public And(IXPathExpression first, IXPathExpression... expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append(" and ");
	}
}
