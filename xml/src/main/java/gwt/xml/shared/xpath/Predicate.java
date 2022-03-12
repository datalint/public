package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.UnaryExpression;

public class Predicate extends UnaryExpression {
    public Predicate(IExpression expression) {
        super(expression);
    }

    @Override
    protected StringBuilder open(StringBuilder target) {
        return target.append('[');
    }

    @Override
    protected StringBuilder close(StringBuilder target) {
        return target.append(']');
    }
}
