package com.datalint.xml.shared.xpath;

import com.datalint.xml.shared.IXPathExpression;

public class Lit implements IXPathExpression {
	public static final IXPathExpression last = new Lit("last()");
	public static final IXPathExpression position = new Lit("position()");
	public static final IXPathExpression text = new Lit("text()");

	private final String value;

	public Lit(String value) {
		this.value = value;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		return target.append(value);
	}
}
