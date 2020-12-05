package com.datalint.xml.shared;

public interface IUtil {
	default UnsupportedOperationException createUnsupportedOperationException(String operatonName) {
		return new UnsupportedOperationException(getClass().getName() + " does not support operation " + operatonName);
	}
}
