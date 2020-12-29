package com.datalint.xml.shared;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class XPathUtil {
	private static final String ancestor = "ancestor";
	private static final String last = "last()";
	private static final String self = ".";

	private static String cacheCurrentAllChildren;

	private XPathUtil() {
	}

	public static String ancestor(String tagName) {
		return operatorAxis(ancestor, tagName);
	}

	public static String last() {
		return last;
	}

	public static String self() {
		return self;
	}

	public static String dateTimeGreaterOrEqualThan(String xPath, String attributeName, String referenceValue) {
		StringBuilder sB = new StringBuilder(xPath);

		Function.predicate.open(sB);

		Function.translate.open(sB);

		toAttribute(sB, attributeName);

		sB.append(", '-T:', ''");

		Function.translate.close(sB);

		sB.append(" >= ");

		quote(sB, referenceValue);

		Function.predicate.close(sB);

		return sB.toString();
	}

	public static String parent(String xPath) {
		return concat(xPath, "..");
	}

	public static String xPathCurrentAllChildren() {
		if (cacheCurrentAllChildren == null)
			cacheCurrentAllChildren = xPathAllChildren(self);

		return cacheCurrentAllChildren;
	}

	public static String xPathAllChildren(String parent) {
		return xPathAllChildren(parent, "*");
	}

	public static String xPathCurrentAllChildren(String children) {
		return xPathAllChildren(self, children);
	}

	public static String xPathAllChildren(String parent, String children) {
		return parent + "/" + children;
	}

	public static String xPathCurrentAllDescendants() {
		return xPathAllDescendants(self);
	}

	public static String xPathCurrentAllDescendants(String descendants) {
		return xPathAllDescendants(self, descendants);
	}

	public static String xPathAllDescendants(String parent) {
		return xPathAllDescendants(parent, "*");
	}

	public static String xPathAllDescendants(String parent, String descendants) {
		return parent + "//" + descendants;
	}

	public static String xPathTagAllDescendants(String parent, String... tagNames) {
		String[] operandNames = new String[tagNames.length];
		for (int i = 0; i < tagNames.length; i++) {
			operandNames[i] = "name(.)=" + quote(tagNames[i]);
		}

		return predicate(xPathAllDescendants(parent, "*"), operatorOr(operandNames));
	}

	public static String elementAllHasAttribute(String attributeName) {
		return elementAllHasAttribute("*", attributeName);
	}

	public static String elementAllHasAttribute(String tagName, String attributeName) {
		return predicate(xPathCurrentAllDescendants(tagName), toAttribute(attributeName));
	}

	public static String xPathHasAttributeNames(String xPath, String... attributeNames) {
		StringBuilder sB = createPredicate(Arrays.asList(attributeNames));

		return sB.length() == 0 ? xPath : predicate(xPath, sB.toString());
	}

	private static StringBuilder createPredicate(Collection<String> attributeNames) {
		StringBuilder sB = new StringBuilder();
		for (String attributeName : attributeNames) {
			if (sB.length() > 0)
				sB.append(" or ");

			toAttribute(sB, attributeName);
		}

		return sB;
	}

	public static String xPathHasAttributeValue(String xPath, String attributeName, String... attributeValues) {
		return xPathHasAttributeValue(xPath, attributeName, Arrays.asList(attributeValues));
	}

	public static String xPathHasAttributeValue(String xPath, String attributeName,
												Collection<String> attributeValues) {
		StringBuilder sB = createPredicate(attributeName, attributeValues);

		return sB.length() == 0 ? xPath : predicate(xPath, sB.toString());
	}

	public static String xPathNotAttributeValue(String xPath, Map<String, String> attributes) {
		StringBuilder sB = createPredicate(attributes, true);

		return sB.length() == 0 ? xPath : predicate(xPath, not(sB.toString()));
	}

	public static String xPathNotAttributeValue(String xPath, String attributeName, String... attributeValues) {
		return xPathNotAttributeValue(xPath, attributeName, Arrays.asList(attributeValues));
	}

	public static String xPathNotAttributeValue(String xPath, String attributeName,
												Collection<String> attributeValues) {
		StringBuilder sB = createPredicate(attributeName, attributeValues);

		return sB.length() == 0 ? xPath : predicate(xPath, not(sB.toString()));
	}

	public static String xPathHasAttributeValue(String xPath, Map<String, String> attributes) {
		StringBuilder sB = createPredicate(attributes);

		return sB.length() == 0 ? xPath : predicate(xPath, sB.toString());
	}

	public static StringBuilder createPredicate(String attributeName, Collection<String> attributeValues) {
		StringBuilder sB = new StringBuilder();

		if (attributeValues.isEmpty())
			return toAttribute(sB, attributeName);

		for (String attributeValue : attributeValues) {
			if (sB.length() > 0)
				sB.append(" or ");

			appendAttributePredicate(sB, attributeName, attributeValue);
		}
		return sB;
	}

	private static StringBuilder createPredicate(Map<String, String> attributes) {
		return createPredicate(attributes, false);
	}

	private static StringBuilder createPredicate(Map<String, String> attributes, boolean isOr) {
		StringBuilder sB = new StringBuilder();
		for (Entry<String, String> entry : attributes.entrySet()) {
			if (sB.length() > 0)
				sB.append(isOr ? " or " : " and ");

			appendAttributePredicate(sB, entry.getKey(), entry.getValue());
		}
		return sB;
	}

	public static void appendAttributePredicate(StringBuilder target, String attributeName, String attributeValue) {
		target.append('@').append(attributeName);

		// Bug found on 2016-06-12 for a recordName containing single quote,
		// advanced handling required by using appendXPathLiteral.
		if (attributeValue != null)
			quote(target.append('='), attributeValue);
	}

	public static String xPathHasChildValue(String xPath, String childName, String childValue) {
		StringBuilder sB = new StringBuilder(xPath);

		Function.predicate.open(sB);
		sB.append(childName).append('=');

		quote(sB, childValue);

		Function.predicate.close(sB);

		return sB.toString();
	}

	public static String elementsAllExcept(String excludedTagName) {
		return predicate("*", "name(.)!=" + quote(excludedTagName));
	}

	public static String contains(String xPath, String arg1, String arg2) {
		return predicate(xPath, "contains(" + arg1 + ',' + arg2 + ')');
	}

	public static String positionLast(String xPath) {
		return predicate(xPath, last);
	}

	public static String positionLastTwo(String xPath) {
		return predicate(xPath, "position() > " + last + "-2");
	}

	public static String position(String xPath, int position) {
		return predicate(xPath, "" + position);
	}

	public static String positionLessThan(String xPath, int position) {
		return predicate(xPath, "position() < " + position);
	}

	public static String positionGreaterThan(String xPath, int position) {
		return predicate(xPath, "position() > " + position);
	}

	public static String positionBetween(String xPath, int from, int to) {
		return predicate(xPath, operatorAnd("position() > " + from, "position() <= " + to));
	}

	public static String toAttribute(String attributeName) {
		return toAttribute(new StringBuilder(attributeName.length() + 1), attributeName).toString();
	}

	public static StringBuilder toAttribute(StringBuilder target, String attributeName) {
		return target.append('@').append(attributeName);
	}

	public static String attribute(String xPath, String attributeName) {
		return concat(xPath, toAttribute(attributeName));
	}

	public static String count(String xPath) {
		return "count(" + xPath + ')';
	}

	public static String text(String xPath) {
		return concat(xPath, "text()");
	}

	public static String concat(String parent, String... children) {
		StringBuilder sB = new StringBuilder(parent);

		for (String child : children) {
			if (child.length() > 0)
				sB.append('/').append(child);
		}

		return sB.toString();
	}

	public static String not(String source) {
		return not(new StringBuilder(source.length() + 5), source).toString();
	}

	public static StringBuilder not(StringBuilder target, String source) {
		return function(target, Function.not, source);
	}

	public static String parentheses(String source) {
		return parentheses(new StringBuilder(source.length() + 2), source).toString();
	}

	public static StringBuilder parentheses(StringBuilder target, String source) {
		return function(target, Function.parentheses, source);
	}

	public static String predicate(String xPath, String predicate) {
		return predicate(new StringBuilder(xPath.length() + predicate.length() + 2), xPath, predicate).toString();
	}

	public static StringBuilder predicate(StringBuilder target, String xPath, String predicate) {
		return function(target.append(xPath), Function.predicate, predicate);
	}

	public static String quoteImplicit(String prefix, String source) {
		if (source == null)
			source = "";

		return quote(new StringBuilder(prefix), source).append(']').toString();
	}

	public static String quote(String prefix, String source, String suffix) {
		if (source == null)
			source = "";

		return quote(new StringBuilder(prefix), source).append(suffix).toString();
	}

	public static String quote(String source) {
		if (source == null)
			source = "";

		return quote(new StringBuilder(source.length() + 2), source).toString();
	}

	public static StringBuilder quote(StringBuilder target, String source) {
		int s;
		int d;

		if ((s = source.indexOf('\'')) < 0)
			target.append('\'').append(source).append('\'');
		else if ((d = source.indexOf('"')) < 0)
			target.append('"').append(source).append('"');
		else {
			target.append("concat(");

			char p = s < d ? '\'' : '"';
			char m = p == '\'' ? '"' : '\'';
			int index = 0;
			for (int i = Math.min(s, d) + 1; i < source.length(); i++) {
				if (source.charAt(i) == m) {
					if (index > 0)
						target.append(',');

					target.append(m).append(source.substring(index, i)).append(m);

					char t = p;
					p = m;
					m = t;

					index = i;
				}
			}

			if (index < source.length())
				target.append(',').append(m).append(source.substring(index)).append(m);

			target.append(')');
		}

		return target;
	}

	public static String startsWith(String source) {
		return startsWith(new StringBuilder(source.length() + 13), source).toString();
	}

	public static StringBuilder startsWith(StringBuilder target, String source) {
		return function(target, Function.startsWith, source);
	}

	public static String translate(String source) {
		return translate(new StringBuilder(source.length() + 13), source).toString();
	}

	public static StringBuilder translate(StringBuilder target, String source) {
		return function(target, Function.translate, source);
	}

	public static StringBuilder function(StringBuilder target, Function function,
										 Consumer<StringBuilder> sourceAppender) {
		function.open(target);

		sourceAppender.accept(target);

		return function.close(target);
	}

	public static StringBuilder function(StringBuilder target, Function function, String source) {
		return function.close(function.open(target).append(source));
	}

	public static StringBuilder function(StringBuilder target, String source, Function... functions) {
		for (Function function : functions) {
			function.open(target);
		}

		target.append(source);

		for (int i = functions.length - 1; i >= 0; i--) {
			functions[i].close(target);
		}

		return target;
	}

	public static String operatorAnd(String... operands) {
		return operator(Operator.and, operands);
	}

	public static String operatorAxis(String... operands) {
		return operator(Operator.axis, operands);
	}

	public static String operatorOr(String... operands) {
		return operator(Operator.or, operands);
	}

	public static String operatorUnion(String... operands) {
		return operator(Operator.union, operands);
	}

	private static String operator(Operator operator, String... operands) {
		assert operands.length > 1 : "Unexpected error on operator, name: [" + operator.name() + ']';

		StringBuilder sB = new StringBuilder(operands[0]);
		for (int i = 1; i < operands.length; i++) {
			if (operands[i] == null || operands[i].isEmpty())
				continue;

			operator.append(sB).append(operands[i]);
		}

		return sB.toString();
	}

	public enum Function {
		not {
			@Override
			public StringBuilder open(StringBuilder target) {
				return target.append("not(");
			}
		},
		parentheses {
			@Override
			public StringBuilder open(StringBuilder target) {
				return target.append('(');
			}
		},
		predicate {
			@Override
			public StringBuilder open(StringBuilder target) {
				return target.append('[');
			}

			@Override
			public StringBuilder close(StringBuilder target) {
				return target.append(']');
			}
		},
		startsWith {
			@Override
			public StringBuilder open(StringBuilder target) {
				return target.append("starts-with(");
			}
		},
		translate {
			@Override
			public StringBuilder open(StringBuilder target) {
				return target.append("translate(");
			}
		};

		public abstract StringBuilder open(StringBuilder target);

		public StringBuilder close(StringBuilder target) {
			return target.append(')');
		}
	}

	public enum Operator {
		and, axis {
			@Override
			public StringBuilder append(StringBuilder target) {
				return target.append("::");
			}
		},
		or, union {
			@Override
			public StringBuilder append(StringBuilder target) {
				return target.append('|');
			}
		};

		public StringBuilder append(StringBuilder target) {
			return target.append(' ').append(name()).append(' ');
		}
	}
}
