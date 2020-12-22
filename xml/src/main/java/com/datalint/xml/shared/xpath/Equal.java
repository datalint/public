package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Equal extends Join {
	public Equal(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append('=');
	}
}
