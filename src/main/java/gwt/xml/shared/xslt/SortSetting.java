package gwt.xml.shared.xslt;

public class SortSetting extends AbstractSetting {
	// The one used on template match
	protected final String xPathParent;

	public SortSetting(String xPathParent, String xPath, String xPathChild) {
		this(xPathParent, xPath, xPathChild, false, false);
	}

	public SortSetting(String xPathParent, String xPath, String xPathChild, boolean isNumber, boolean isMax) {
		this(xPathParent, xPath, xPathChild, EMPTY, isNumber, isMax);
	}

	public SortSetting(String xPathParent, String xPath, String xPathChild, String nameSpace, boolean isNumber,
					   boolean isMax) {
		super(xPath, xPathChild, nameSpace, isNumber, isMax);

		this.xPathParent = xPathParent;
	}

	public String getXPathParent() {
		return xPathParent;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && obj instanceof SortSetting && xPathParent.equals(((SortSetting) obj).xPathParent);
	}

	@Override
	public String toString() {
		return xPathParent + super.toString();
	}

	@Override
	public String styleSheet() {
		StringBuilder sB = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"");

		if (nameSpace.length() > 0)
			sB.append(' ').append(nameSpace);

		sB.append("><xsl:template match=\"");

		assert xPathParent.length() > 0;
		int index = xPathParent.lastIndexOf('/') + 1;
		sB.append(index > 0 ? xPathParent.substring(index) : xPathParent);

		sB.append("\"><xsl:copy><xsl:copy-of select=\"@*\" /><xsl:for-each select=\"");

		assert xPath.length() > 0;
		sB.append(xPath);

		sB.append("\"><xsl:sort select=\"");

		assert xPathChild.length() > 0;
		sB.append(xPathChild);

		sB.append('"');

		if (isNumber)
			sB.append(" data-type=\"number\"");

		if (isMax)
			sB.append(" order=\"descending\"");

		sB.append(" /><xsl:copy-of select=\".\" /></xsl:for-each></xsl:copy></xsl:template></xsl:stylesheet>");

		return sB.toString();
	}
}
