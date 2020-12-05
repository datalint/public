package com.datalint.xml.shared;

import javax.annotation.Nullable;

import com.datalint.xml.shared.xpath.*;

public interface IXPathExpression extends IBasicUtil {
	abstract StringBuilder append(StringBuilder target);

	default String build() {
		return append(new StringBuilder()).toString();
	}

	static IXPathExpression and(IXPathExpression first, IXPathExpression... expressions) {
		return new And(first, expressions);
	}

	static IXPathExpression all(IXPathExpression... expressions) {
		return join(WILDCARD, expressions);
	}

	static IXPathExpression attr(String value) {
		return new Lit(AT + value);
	}

	static IXPathExpression concat(IXPathExpression first, IXPathExpression... expressions) {
		return new Concat(first, expressions);
	}

	static IXPathExpression contains(IXPathExpression first, IXPathExpression second) {
		return new Contains(first, second);
	}

	static IXPathExpression count(IXPathExpression expression) {
		return new Count(expression);
	}

	static IXPathExpression descendants(IXPathExpression first, IXPathExpression... expressions) {
		return new Descendants(first, expressions);
	}

	static IXPathExpression descendants(String first, IXPathExpression... expressions) {
		return descendants(lit(first), expressions);
	}

	static IXPathExpression equal(IXPathExpression first, IXPathExpression second) {
		return new Equal(first, second);
	}

	static IXPathExpression greater(IXPathExpression first, IXPathExpression second) {
		return new Greater(first, second);
	}

	static IXPathExpression greater(IXPathExpression first, int second) {
		return greater(first, lit(second));
	}

	static IXPathExpression greater(int second) {
		return greater(position(), second);
	}

	static IXPathExpression join(IXPathExpression first, IXPathExpression... expressions) {
		return new Join(first, expressions);
	}

	static IXPathExpression join(String first, IXPathExpression... expressions) {
		return join(lit(first), expressions);
	}

	static IXPathExpression last() {
		return Lit.last;
	}

	static IXPathExpression less(IXPathExpression first, IXPathExpression second) {
		return new Less(first, second);
	}

	static IXPathExpression less(IXPathExpression first, int second) {
		return less(first, lit(second));
	}

	static IXPathExpression less(int second) {
		return less(position(), second);
	}

	static IXPathExpression lit(String value) {
		return new Lit(value);
	}

	static IXPathExpression lit(int value) {
		return lit(String.valueOf(value));
	}

	static IXPathExpression lowerCase(IXPathExpression expression) {
		return translate(expression, quote(ALPHABET_UPPERCASE), quote(ALPHABET_LOWERCASE));
	}

	static IXPathExpression minus(IXPathExpression first, IXPathExpression second) {
		return new Minus(first, second);
	}

	static IXPathExpression minus(IXPathExpression first, int second) {
		return new Minus(first, lit(second));
	}

	static IXPathExpression minus(int second) {
		return new Minus(last(), lit(second));
	}

	static IXPathExpression name(@Nullable IXPathExpression expression) {
		return new Name(expression);
	}

	static IXPathExpression not(IXPathExpression expression) {
		return new Not(expression);
	}

	static IXPathExpression or(IXPathExpression first, IXPathExpression... expressions) {
		return new Or(first, expressions);
	}

	static IXPathExpression parentheses(IXPathExpression expression) {
		return new UnaryExpression(expression);
	}

	static IXPathExpression path(IXPathExpression first, IXPathExpression... expressions) {
		return new Path(first, expressions);
	}

	static IXPathExpression path(String first, IXPathExpression... expressions) {
		return path(lit(first), expressions);
	}

	static IXPathExpression position() {
		return Lit.position;
	}

	static IXPathExpression quote(String value) {
		return new Lit(XPathUtil.quote(value));
	}

	static IXPathExpression predicate(IXPathExpression expression) {
		return new Predicate(expression);
	}

	static IXPathExpression self() {
		return parentheses(lit("."));
	}

	static IXPathExpression startsWith(IXPathExpression first, IXPathExpression second) {
		return new StartsWith(first, second);
	}

	static IXPathExpression text() {
		return Lit.text;
	}

	static IXPathExpression translate(IXPathExpression first, IXPathExpression second, IXPathExpression third) {
		return new Translate(first, second, third);
	}

	static IXPathExpression union(IXPathExpression first, IXPathExpression... expressions) {
		return new Union(first, expressions);
	}

	static IXPathExpression union(String first, IXPathExpression... expressions) {
		return union(lit(first), expressions);
	}
}
