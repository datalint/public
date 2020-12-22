package com.datalint.xml.server.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

import com.datalint.xml.shared.XmlUtil;

public class AttrImpl extends NodeImpl implements Attr {
	private final String name;
	private String value;

	public static String toString(String name, String value) {
		return new StringBuilder(name).append(_EQUALS).append(_QUOTE).append(XmlUtil.escapeAttr(value)).append(_QUOTE)
				.toString();
	}

	public AttrImpl(Node owner, String name, String value) {
		super(owner);

		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return getNodeName();
	}

	@Override
	public boolean getSpecified() {
		return value != null;
	}

	@Override
	public String getValue() {
		return getNodeValue();
	}

	@Override
	public void setValue(String value) {
		setNodeValue(value);
	}

	@Override
	public short getNodeType() {
		return ATTRIBUTE_NODE;
	}

	@Override
	public String getNodeName() {
		return name;
	}

	@Override
	public String getNodeValue() {
		return value;
	}

	@Override
	public void setNodeValue(String nodeValue) {
		value = nodeValue;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new AttrImpl(getOwnerDocument(), name, value);
	}

	@Override
	public Element getOwnerElement() {
		throw iCreateUoException("getOwnerElement");
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw iCreateUoException("getSchemaTypeInfo");
	}

	@Override
	public boolean isId() {
		throw iCreateUoException("isId");
	}

	@Override
	public String toString() {
		return toString(getNodeName(), getNodeValue());
	}
}
