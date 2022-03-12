package gwt.xml.shared.expression;

public class UnaryExpression implements IExpression {
    protected final IExpression expression;

    public UnaryExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        return close(appendExpression(open(target)));
    }

    protected StringBuilder appendExpression(StringBuilder target) {
        if (expression == null)
            return target;

        return expression.append(target);
    }

    protected StringBuilder open(StringBuilder target) {
        return target.append('(');
    }

    protected StringBuilder close(StringBuilder target) {
        return target.append(')');
    }
}
