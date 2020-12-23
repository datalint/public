package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Union extends Join {
	public Union(IXPathExpression first, IXPathExpression[] expressions) {
		super(first, expressions);
	}

	@Override
	protected StringBuilder operator(StringBuilder target) {
		return target.append('|');
	}
}
