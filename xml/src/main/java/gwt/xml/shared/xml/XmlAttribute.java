package gwt.xml.shared.xml;

import gwt.xml.shared.expression.IExpression;

public class XmlAttribute implements IExpression {
    private final String name;
    private final String escapedValue;

    public XmlAttribute(String name, String value) {
        this.name = name;
        this.escapedValue = escapeAttr(value);
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        return target.append(_SPACE).append(name).append(_EQUALS).append(_APOSTROPHE).append(escapedValue)
                .append(_APOSTROPHE);
    }
}
