package gwt.xml.shared.xml;

import gwt.xml.shared.IXmlExpression;

public class XmlElement implements IXmlExpression {
	private final String tagName;

	private IXmlExpression child;
	private IXmlExpression[] attributes;

	public XmlElement(String tagName) {
		this.tagName = tagName;
	}

	public XmlElement(String tagName, String text) {
		this.tagName = tagName;

		setChild(new XmlText(text));
	}

	public XmlElement(String tagName, IXmlExpression... attributes) {
		this.tagName = tagName;

		this.attributes = attributes;
	}

	public void setChild(IXmlExpression child) {
		this.child = child;
	}

	public XmlElement withChild(IXmlExpression child) {
		setChild(child);

		return this;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		target.append(_LESS_THAN).append(tagName);

		if (attributes != null) {
			for (IXmlExpression attribute : attributes) {
				attribute.append(target);
			}
		}

		if (child == null || child.isEmpty())
			target.append(_SLASH).append(_GREATER_THAN);
		else
			child.append(target.append(_GREATER_THAN)).append(_LESS_THAN).append(_SLASH).append(tagName)
					.append(_GREATER_THAN);

		return target;
	}

}
