package gwt.xml.shared;

public interface IXPathExpression extends ICommon {
	StringBuilder append(StringBuilder target);

	default String build() {
		return append(new StringBuilder()).toString();
	}
}
