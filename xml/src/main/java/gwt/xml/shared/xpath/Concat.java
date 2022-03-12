package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;

public class Concat implements IExpression {
    private final IExpression first;
    private final IExpression[] expressions;

    public Concat(IExpression first, IExpression... expressions) {
        this.first = first;
        this.expressions = expressions;
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        first.append(target.append("concat("));

        for (IExpression expression : expressions) {
            expression.append(target.append(','));
        }

        return target.append(')');
    }
}
