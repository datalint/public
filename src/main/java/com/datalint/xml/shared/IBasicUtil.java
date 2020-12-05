package com.datalint.xml.shared;

public interface IBasicUtil {
	char _APOSTROPHE = '\'';
	char _EQUALS = '=';
	char _GREATER_THAN = '>';
	char _LESS_THAN = '<';
	char _SLASH = '/';
	char _SPACE = ' ';

	String AT = "@";
	String WILDCARD = "*";

	String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	default UnsupportedOperationException createUnsupportedOperationException(String operatonName) {
		return new UnsupportedOperationException(getClass().getName() + " does not support operation " + operatonName);
	}
}
