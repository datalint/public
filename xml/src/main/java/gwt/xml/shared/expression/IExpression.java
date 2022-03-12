package gwt.xml.shared.expression;

import gwt.xml.shared.ICommon;

public interface IExpression extends ICommon {
    StringBuilder append(StringBuilder target);

    default boolean isEmpty() {
        return false;
    }

    default String build() {
        return append(new StringBuilder()).toString();
    }
}
