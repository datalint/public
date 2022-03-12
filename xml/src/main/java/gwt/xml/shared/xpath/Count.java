package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.UnaryExpression;

public class Count extends UnaryExpression {
    public Count(IExpression expression) {
        super(expression);
    }

    protected StringBuilder open(StringBuilder target) {
        return target.append("count(");
    }
}
