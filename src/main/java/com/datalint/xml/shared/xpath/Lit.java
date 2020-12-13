package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Lit implements IXPathExpression {
	public static final IXPathExpression last = new Lit(LAST_F);
	public static final IXPathExpression position = new Lit(POSITION_F);
	public static final IXPathExpression text = new Lit(TEXT_F);

	private final String value;

	public Lit(String value) {
		this.value = value;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(value);
	}
}
