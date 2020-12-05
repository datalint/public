package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Contains extends ArityExpression {
	public Contains(IXPathExpression first, IXPathExpression second) {
		super(first, second);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("contains(");
	}
}
