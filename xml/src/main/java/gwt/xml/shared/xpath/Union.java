package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.expression.Join;

public class Union extends Join {
    public Union(IExpression first, IExpression[] expressions) {
        super(first, expressions);
    }

    @Override
    protected StringBuilder operator(StringBuilder target) {
        return target.append('|');
    }
}
