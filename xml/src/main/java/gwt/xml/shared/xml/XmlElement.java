package gwt.xml.shared.xml;

import gwt.xml.shared.expression.IExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XmlElement implements IExpression {
    private final String tagName;
    private final List<IExpression> attributes;
    private final List<IExpression> children;

    public XmlElement(String tagName, String text) {
        this(tagName, new XmlText(text));
    }

    public XmlElement(String tagName, IExpression... children) {
        this(tagName, null, Arrays.asList(children));
    }

    public XmlElement(String tagName, List<IExpression> attributes) {
        this(tagName, attributes, Collections.emptyList());
    }

    public XmlElement(String tagName, List<IExpression> attributes, String text) {
        this(tagName, attributes, Collections.singletonList(new XmlText(text)));
    }

    public XmlElement(String tagName, List<IExpression> attributes, List<IExpression> children) {
        this.tagName = tagName;
        this.attributes = attributes;
        this.children = children;
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        target.append(_LESS_THAN).append(tagName);

        if (attributes != null) {
            for (IExpression attribute : attributes) {
                attribute.append(target);
            }
        }

        if (children == null || children.isEmpty()) {
            target.append(_SLASH).append(_GREATER_THAN);
        } else {
            target.append(_GREATER_THAN);

            for (IExpression child : children) {
                child.append(target);
            }

            target.append(_LESS_THAN).append(_SLASH).append(tagName).append(_GREATER_THAN);
        }

        return target;
    }
}
