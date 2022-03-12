package gwt.xml.shared.xml;

import gwt.xml.shared.expression.IExpression;

import java.util.List;

public class XmlSequence implements IExpression {
    private final List<XmlElement> xmlElements;

    public XmlSequence(List<XmlElement> xmlElements) {
        this.xmlElements = xmlElements;
    }

    @Override
    public boolean isEmpty() {
        return xmlElements.isEmpty();
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        for (XmlElement xmlElement : xmlElements) {
            xmlElement.append(target);
        }

        return target;
    }
}
