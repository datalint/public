package com.datalint.xml.shared;

public interface IXPathExpression extends ICommon {
	abstract StringBuilder append(StringBuilder target);

	default String build() {
		return append(new StringBuilder()).toString();
	}
}
