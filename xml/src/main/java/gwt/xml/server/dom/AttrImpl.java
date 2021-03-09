package gwt.xml.server.dom;

import gwt.xml.shared.XmlUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public class AttrImpl extends NodeImpl implements Attr {
	private final String name;
	private final String value;

	public AttrImpl(Node owner, String name, String value) {
		super(owner);

		this.name = name;
		this.value = value;
	}

	public static String toString(String name, String value) {
		return name + _EQUALS + _QUOTE + XmlUtil.escapeAttrStatic(value) + _QUOTE;
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
		throw createUoException("setValue");
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
		throw createUoException("setNodeValue");
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new AttrImpl(getOwnerDocument(), name, value);
	}

	@Override
	public String toString() {
		return toString(getNodeName(), getNodeValue());
	}

	@Override
	public Element getOwnerElement() {
		throw createUoException("getOwnerElement");
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw createUoException("getSchemaTypeInfo");
	}

	@Override
	public boolean isId() {
		throw createUoException("isId");
	}
}
