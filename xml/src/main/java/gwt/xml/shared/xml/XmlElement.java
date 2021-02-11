package gwt.xml.shared.xml;

import gwt.xml.shared.expression.IExpression;

public class XmlElement implements IExpression {
	private final String tagName;

	private IExpression child;
	private IExpression[] attributes;

	public XmlElement(String tagName) {
		this.tagName = tagName;
	}

	public XmlElement(String tagName, String text) {
		this.tagName = tagName;

		setChild(new XmlText(text));
	}

	public XmlElement(String tagName, IExpression... attributes) {
		this.tagName = tagName;

		this.attributes = attributes;
	}

	public void setChild(IExpression child) {
		this.child = child;
	}

	public XmlElement withChild(IExpression child) {
		setChild(child);

		return this;
	}

	@Override
	public StringBuilder append(StringBuilder target) {
		target.append(_LESS_THAN).append(tagName);

		if (attributes != null) {
			for (IExpression attribute : attributes) {
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
