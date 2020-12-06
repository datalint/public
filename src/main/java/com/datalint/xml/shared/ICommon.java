package com.datalint.xml.shared;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface ICommon {
	char _APOSTROPHE = '\'';
	char _EQUALS = '=';
	char _GREATER_THAN = '>';
	char _LESS_THAN = '<';
	char _SLASH = '/';
	char _SPACE = ' ';

	String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String AT = "@";
	String DOT = ".";
	String EMPTY = "";
	String SPACE = " ";
	String WILDCARD = "*";

	default UnsupportedOperationException createUnsupportedOperationException(String operatonName) {
		return new UnsupportedOperationException(getClass().getName() + " does not support operation " + operatonName);
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
