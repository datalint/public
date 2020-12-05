package com.datalint.xml.shared;

public interface IXmlExpression extends IBasicUtil {
	abstract StringBuilder append(StringBuilder target);

	default boolean isEmpty() {
		return false;
	}

	default String build() {
		return append(new StringBuilder()).toString();
	}
}
