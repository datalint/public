package gwt.xml.shared;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.xml.XmlAttribute;
import gwt.xml.shared.xml.XmlElement;

import java.util.Arrays;
import java.util.List;

public class XmlBuilder {
    private XmlBuilder() {
    }

    public static XmlElement createXmlElement(String tagName, List<String> attributes) {
        return new XmlElement(tagName, createAttrExpressions(attributes));
    }

    public static XmlElement createXmlElement(String tagName, String... attributes) {
        return createXmlElement(tagName, Arrays.asList(attributes));
    }

    private static IExpression[] createAttrExpressions(List<String> attributes) {
        IExpression[] expressions = new IExpression[attributes.size() >> 1];

        for (int i = 0, j = 0; i < expressions.length; i++, j++) {
            expressions[i] = new XmlAttribute(attributes.get(j++), attributes.get(j));
        }

        return expressions;
    }
}
