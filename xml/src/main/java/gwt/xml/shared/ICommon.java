package gwt.xml.shared;

import com.google.common.xml.XmlEscapers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public interface ICommon {
    char _AMPERSAND = '&';
    char _APOSTROPHE = '\'';
    char _AT = '@';
    char _COLON = ':';
    char _COMMA = ',';
    char _DOT = '.';
    char _EQUALS = '=';
    char _GREATER_THAN = '>';
    char _HYPHEN = '-';
    char _LEFT_BRACKET = '[';
    char _LESS_THAN = '<';
    char _LINE_FEED = '\n';
    char _NULL = '\u0000';
    char _PERCENT = '%';
    char _QUERY = '?';
    char _QUOTE = '"';
    char _RIGHT_BRACKET = ']';
    char _SEMICOLON = ';';
    char _SLASH = '/';
    char _SPACE = ' ';
    char _TAB = '\t';
    char _UNDERSCORE = '_';

    char _a = 'a';
    char _p = 'p';
    char _v = 'v';
    char _w = 'w';
    char _x = 'x';

    String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String AXIS_ANCESTOR = "ancestor::";
    String AXIS_SELF = "self::";
    String AND_WITH_SPACE = " and ";
    String AT = "@";
    String COMMA_SPACE = ", ";
    String COUNT_ALL = "count(*)";
    String DATA_DIV = "data-div";
    String DIV = "div";
    String DOLLAR = "$";
    String DOT = ".";
    String DOUBLE_DOT = "..";
    String EMPTY = "";
    String GREATER_EQUAL_THAN = ">=";
    String HASH = "#";
    String HYPHEN = "-";
    String HYPHEN_WITH_SPACE = " - ";
    String ID = "id";
    String LESS_EQUAL_THAN = "<=";
    String NULL = "null";
    String ONE = "1";
    String OR_WITH_SPACE = " or ";
    String PLUS = "+";
    String PLUS_WITH_SPACE = " + ";
    String SPACE = " ";
    String UNDERSCORE = "_";
    String WILDCARD = "*";
    String ZERO = "0";

    default String afterIndex(String source, int index) {
        return index < 0 ? source : source.substring(index + 1);
    }

    default String after(String source, char c) {
        return afterIndex(source, source.indexOf(c));
    }

    default String afterLast(String source, char c) {
        return afterIndex(source, source.lastIndexOf(c));
    }

    default String beforeIndex(String source, int index) {
        return index < 0 ? source : source.substring(0, index);
    }

    default String before(String source, char c) {
        return beforeIndex(source, source.indexOf(c));
    }

    default String beforeLast(String source, char c) {
        return beforeIndex(source, source.lastIndexOf(c));
    }

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

    default <T extends Node> T append(T node, Node... children) {
        for (Node child : children) {
            node.appendChild(child);
        }

        return node;
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
