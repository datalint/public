package gwt.xml.shared;

import gwt.xml.shared.expression.IExpression;
import gwt.xml.shared.xml.XmlAttribute;
import gwt.xml.shared.xml.XmlElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlBuilder {
    private XmlBuilder() {
    }

    public static IExpression createDivElement(String text) {
        return createXmlElement(ICommon.DIV, text);
    }

    public static IExpression createDivElement(IExpression... children) {
        return createXmlElement(ICommon.DIV, children);
    }

    public static IExpression createDivElement(List<String> attributes) {
        return createXmlElement(ICommon.DIV, attributes);
    }

    public static IExpression createDivElement(String dataAttributeValue, String text) {
        return createXmlElement(ICommon.DIV, Arrays.asList(ICommon.DATA_DIV, dataAttributeValue), text);
    }

    public static IExpression createDivElement(List<String> attributes, List<IExpression> children) {
        return createXmlElement(ICommon.DIV, attributes, children);
    }

    public static IExpression createXmlElement(String tagName, String text) {
        return new XmlElement(tagName, text);
    }

    public static IExpression createXmlElement(String tagName, IExpression... children) {
        return new XmlElement(tagName, children);
    }

    public static IExpression createXmlElement(String tagName, List<String> attributes) {
        return new XmlElement(tagName, createAttrExpressions(attributes));
    }

    public static IExpression createXmlElement(String tagName, List<String> attributes, String text) {
        return new XmlElement(tagName, createAttrExpressions(attributes), text);
    }

    public static IExpression createXmlElement(String tagName, List<String> attributes, List<IExpression> children) {
        return new XmlElement(tagName, createAttrExpressions(attributes), children);
    }

    private static List<IExpression> createAttrExpressions(List<String> attributes) {
        List<IExpression> expressions = new ArrayList<>(attributes.size() >> 1);

        for (int i = 1; i < attributes.size(); i += 2) {
            expressions.add(new XmlAttribute(attributes.get(i - 1), attributes.get(i)));
        }

        return expressions;
    }
}
