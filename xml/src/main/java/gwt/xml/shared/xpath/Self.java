package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;

public class Self implements IExpression {
    private final String value;

    public Self(String value) {
        this.value = value;
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        return target.append("self::").append(value);
    }
}
