package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Translate extends ArityExpression {
	public Translate(IXPathExpression first, IXPathExpression second, IXPathExpression third) {
		super(first, second, third);
	}

	@Override
	protected StringBuilder open(StringBuilder target) {
		return target.append("translate(");
	}
}
