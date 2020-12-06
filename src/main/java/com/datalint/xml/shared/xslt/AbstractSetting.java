package com.datalint.xml.shared.xslt;

import com.datalint.xml.shared.ICommon;

public abstract class AbstractSetting implements ICommon {
	// The one used on for-each select
	protected final String xPath;
	// The one used on sort select
	protected final String xPathChild;
	protected final String nameSpace;
	protected final boolean isNumber;
	protected final boolean isMax;

	protected final int hashCode;

	protected AbstractSetting(String xPath, String xPathChild, String nameSpace, boolean isNumber, boolean isMax) {
		this.xPath = xPath;
		this.xPathChild = xPathChild;
		this.nameSpace = nameSpace;
		this.isMax = isMax;
		this.isNumber = isNumber;

		hashCode = toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;

		assert obj instanceof AbstractSetting;

		AbstractSetting other = (AbstractSetting) obj;

		return xPath.equals(other.xPath) && xPathChild.equals(other.xPathChild) && nameSpace.equals(other.nameSpace)
				&& isMax == other.isMax && isNumber == other.isNumber;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return new StringBuilder(xPath).append(xPathChild).append(nameSpace).append(isMax).append(isNumber).toString();
	}

	public abstract String styleSheet();
}
