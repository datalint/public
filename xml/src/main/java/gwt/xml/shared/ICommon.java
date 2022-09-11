package gwt.xml.shared;

import com.google.common.xml.XmlEscapers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public interface ICommon {
    char _APOSTROPHE = '\'';
    char _AT = '@';
    char _COMMA = ',';
    char _EQUALS = '=';
    char _GREATER_THAN = '>';
    char _LESS_THAN = '<';
    char _LINE_FEED = '\n';
    char _QUOTE = '"';
    char _SLASH = '/';
    char _SPACE = ' ';
    char _TAB = '\t';
    char _UNDERSCORE = '_';

    String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String AND_WITH_SPACE = " and ";
    String AT = "@";
    String COUNT_ALL = "count(*)";
    String DOLLAR = "$";
    String DOT = ".";
    String EMPTY = "";
    String ID = "id";
    String LAST_F = "last()";
    String MINUS_WITH_SPACE = " - ";
    String ONE = "1";
    String OR_WITH_SPACE = " or ";
    String PLUS_WITH_SPACE = " + ";
    String POSITION_F = "position()";
    String SPACE = " ";
    String TEXT_F = "text()";
    String UNDERSCORE = "_";
    String WILDCARD = "*";
    String ZERO = "0";

    default int getAttributeInt(Element element, String name) {
        return parseInt(element.getAttribute(name));
    }

    default Integer getAttributeInteger(Element element, String name) {
        return parseInteger(element.getAttribute(name));
    }

    default String nonNullAttribute(Element element, String name) {
        return attribute(element, name, EMPTY);
    }

    default String attribute(Element element, String name, String substitution) {
        return element.hasAttribute(name) ? element.getAttribute(name) : substitution;
    }

    /**
     * Return the first present attribute value.
     *
     * @param element        the source element
     * @param attributeNames an array being tested with
     * @return the first present attribute value
     */
    default String coalesce(Element element, String... attributeNames) {
        for (String attributeName : attributeNames) {
            if (element.hasAttribute(attributeName))
                return element.getAttribute(attributeName);
        }

        return null;
    }

    default String simpleName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    default int parseInt(String source) {
        return parseInt(source, 0);
    }

    default int parseInt(String source, int defaultValue) {
        if (!isEmpty(source))
            try {
                return Integer.parseInt(source);
            } catch (NumberFormatException e) {
                // Ignore
            }

        return defaultValue;
    }

    default Integer parseInteger(String source) {
        if (!isEmpty(source)) {
            try {
                return Integer.valueOf(source);
            } catch (NumberFormatException e) {
                // Ignore;
            }
        }

        return null;
    }

    default boolean isEven(int x) {
        return (x & 1) == 0;
    }

    default boolean isEmpty(Object source) {
        return source == null || source.toString().isEmpty();
    }

    default String nonNull(String source) {
        return coalesce(source, EMPTY);
    }

    default <T> T coalesce(T... values) {
        for (T value : values) {
            if (value != null)
                return value;
        }

        return null;
    }

    default String nonEmpty(String source, String substitution) {
        return isEmpty(source) ? substitution : source;
    }

    default String escapeAttr(String attribute) {
        return XmlEscapers.xmlAttributeEscaper().escape(attribute);
    }

    default String escapeContent(String content) {
        return XmlEscapers.xmlContentEscaper().escape(content);
    }

    default UnsupportedOperationException createUoException(String operationName) {
        return new UnsupportedOperationException(getClass().getName() + " does not support operation " + operationName);
    }

    default NodeList asNodeList(List<Node> nodes) {
        return new NodeListWrapper(nodes);
    }

    default List<Node> asList(NodeList nodeList) {
        return new ListWrapper(nodeList);
    }

    class NodeListWrapper implements NodeList {
        private final List<Node> nodes;

        private NodeListWrapper(List<Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        public Node item(int index) {
            return nodes.get(index);
        }

        @Override
        public int getLength() {
            return nodes.size();
        }
    }

    class ListWrapper extends AbstractList<Node> {
        private final NodeList nodeList;
        private final int length;

        private List<Node> extraList;

        private ListWrapper(NodeList nodeList) {
            this.nodeList = nodeList;
            length = nodeList.getLength();
        }

        @Override
        public void add(int index, Node node) {
            if (extraList == null)
                extraList = new ArrayList<>();

            extraList.add(index - length, node);
        }

        @Override
        public Node get(int index) {
            if (index < length)
                return nodeList.item(index);

            return extraList.get(index - length);
        }

        @Override
        public int size() {
            return extraList == null ? length : length + extraList.size();
        }
    }
}
