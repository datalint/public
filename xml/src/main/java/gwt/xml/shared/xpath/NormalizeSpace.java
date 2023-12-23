package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.UnaryExpression;

public class NormalizeSpace extends UnaryExpression {
    public NormalizeSpace() {
        this(null);
    }

    public NormalizeSpace(IExpression expression) {
        super(expression);
    }

    protected StringBuilder open(StringBuilder target) {
        return target.append("normalize-space(");
    }
}
