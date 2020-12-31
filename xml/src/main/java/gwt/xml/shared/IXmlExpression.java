package gwt.xml.shared;

public interface IXmlExpression extends ICommon {
	StringBuilder append(StringBuilder target);

	default boolean isEmpty() {
		return false;
	}

	default String build() {
		return append(new StringBuilder()).toString();
	}
}
