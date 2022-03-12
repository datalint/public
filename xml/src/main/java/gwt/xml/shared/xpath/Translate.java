package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.ArityExpression;
import gwt.xml.shared.expression.IExpression;

public class Translate extends ArityExpression {
    public Translate(IExpression first, IExpression second, IExpression third) {
        super(first, second, third);
    }

    @Override
    protected StringBuilder open(StringBuilder target) {
        return target.append("translate(");
    }
}
