package gwt.xml.shared.expression;

public class Join implements IExpression {
    private final IExpression first;
    private final IExpression[] expressions;

    public Join(IExpression first, IExpression... expressions) {
        this.first = first;
        this.expressions = expressions;
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        first.append(target);

        for (IExpression expression : expressions) {
            expression.append(operator(target));
        }

        return target;
    }

    protected StringBuilder operator(StringBuilder target) {
        return target;
    }
}
