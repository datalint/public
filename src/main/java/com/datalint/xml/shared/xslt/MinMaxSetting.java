package com.datalint.xml.shared.xslt;

public class MinMaxSetting extends AbstractSetting {
	public MinMaxSetting(String xPath) {
		this(xPath, false, false);
	}

	public MinMaxSetting(String xPath, boolean isNumber, boolean isMax) {
		this(xPath, DOT, isNumber, isMax);
	}

	public MinMaxSetting(String xPath, String xPathChild, boolean isNumber, boolean isMax) {
		this(xPath, xPathChild, EMPTY, isNumber, isMax);
	}

	public MinMaxSetting(String xPath, String xPathChild, String nameSpace, boolean isNumber, boolean isMax) {
		super(xPath, xPathChild, nameSpace, isNumber, isMax);
	}

	@Override
	public String styleSheet() {
		StringBuilder sB = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"");

		if (nameSpace.length() > 0)
			sB.append(' ').append(nameSpace);

		sB.append("><xsl:output method=\"text\" /><xsl:template match=\"/\"><xsl:for-each select=\"");

		assert xPath.length() > 0;
		sB.append(xPath);

		sB.append("\"><xsl:sort select=\"").append(xPathChild).append('"');

		if (isNumber)
			sB.append(" data-type=\"number\"");

		if (isMax)
			sB.append(" order=\"descending\"");

		sB.append(" /><xsl:if test=\"position()=1\"><xsl:value-of select=\"").append(xPathChild)
				.append("\" /></xsl:if></xsl:for-each></xsl:template></xsl:stylesheet>");

		return sB.toString();
	}
}
