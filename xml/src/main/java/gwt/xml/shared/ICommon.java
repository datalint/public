package gwt.xml.shared;

import com.google.common.xml.XmlEscapers;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public interface ICommon {
	char _APOSTROPHE = '\'';
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
	String AT = "@";
	String COUNT_ALL = "count(*)";
	String DOT = ".";
	String EMPTY = "";
	String LAST_F = "last()";
	String POSITION_F = "position()";
	String SPACE = " ";
	String TEXT_F = "text()";
	String WILDCARD = "*";

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

	default boolean isEven(int x) {
		return (x & 1) == 0;
	}

	default boolean isEmpty(@Nullable String source) {
		return source == null || source.isEmpty();
	}

	default String nonNull(@Nullable String source) {
		return nonNull(source, EMPTY);
	}

	default String nonNull(@Nullable String source, String substitution) {
		return source == null ? substitution : source;
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

	default List<Node> asList(NodeList nodeList) {
		return new NodeListWrapper(nodeList);
	}

	class NodeListWrapper extends AbstractList<Node> {
		private final NodeList nodeList;
		private final int length;

		private List<Node> extraList;

		private NodeListWrapper(NodeList nodeList) {
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
