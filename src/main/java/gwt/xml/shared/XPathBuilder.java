package gwt.xml.shared;

import gwt.xml.shared.xpath.*;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

public class XPathBuilder implements ICommon {
	public static final IXPathExpression LAST = new Lit(LAST_F);
	public static final IXPathExpression POSITION = new Lit(POSITION_F);
	public static final IXPathExpression TEXT = new Lit(TEXT_F);

	private XPathBuilder() {
	}

	public static IXPathExpression all(IXPathExpression... expressions) {
		return join(WILDCARD, expressions);
	}

	public static IXPathExpression and(IXPathExpression first, IXPathExpression... expressions) {
		return new And(first, expressions);
	}

	public static IXPathExpression attr(String value) {
		return new Lit(AT + value);
	}

	public static IXPathExpression attributeNotStartsWith(String attribute, String query) {
		return and(attr(attribute), not(startsWith(attr(attribute), quote(query))));
	}

	public static String attributeNotStartsWith(String xPath, String attribute, String query) {
		return join(xPath, predicate(attributeNotStartsWith(attribute, query))).build();
	}

	public static IXPathExpression attributeStartsWith(String attribute, String query) {
		return startsWith(attr(attribute), quote(query));
	}

	public static IXPathExpression concat(IXPathExpression first, IXPathExpression... expressions) {
		return new Concat(first, expressions);
	}

	public static IXPathExpression contains(IXPathExpression first, IXPathExpression second) {
		return new Contains(first, second);
	}

	public static IXPathExpression count(IXPathExpression expression) {
		return new Count(expression);
	}

	public static String count(String xPath) {
		return count(lit(xPath)).build();
	}

	public static IXPathExpression[] createAttrExpressions(String[] arguments) {
		return createExpressions(argument -> attr(argument), arguments);
	}

	public static IXPathExpression[] createExpressions(Function<String, IXPathExpression> function,
													   String[] arguments) {
		IXPathExpression[] expressions = new IXPathExpression[arguments.length];

		for (int i = 0; i < expressions.length; i++) {
			expressions[i] = function.apply(arguments[i]);
		}

		return expressions;
	}

	public static IXPathExpression[] createLitExpressions(String[] arguments) {
		return createExpressions(argument -> lit(argument), arguments);
	}

	public static <T> IXPathExpression createParenthesesAnd(Collection<T> arguments,
															Function<T, IXPathExpression> creator) {
		int size = arguments.size();

		if (size == 0)
			return null;
		else if (size == 1)
			return creator.apply(arguments.iterator().next());

		IXPathExpression first = null;
		IXPathExpression[] expressions = new IXPathExpression[size - 1];

		int index = 0;
		for (T argument : arguments) {
			IXPathExpression expression = creator.apply(argument);

			if (first == null)
				first = parentheses(expression);
			else
				expressions[index++] = parentheses(expression);
		}

		return and(first, expressions);
	}

	public static IXPathExpression descendants(IXPathExpression first, IXPathExpression... expressions) {
		return new Descendants(first, expressions);
	}

	public static IXPathExpression descendants(String first, IXPathExpression... expressions) {
		return descendants(lit(first), expressions);
	}

	public static String descendantsWithTagNames(String xPath, String tagName, String... tagNames) {
		return descendants(xPath, join(lit(WILDCARD), predicate(or(equalTagName(tagName), equalTagNames(tagNames)))))
				.build();
	}

	public static IXPathExpression equal(IXPathExpression first, IXPathExpression second) {
		return new Equal(first, second);
	}

	public static IXPathExpression equalAttribute(String name, String value) {
		return equal(attr(name), quote(value));
	}

	public static IXPathExpression equalChild(String childTagName, String value) {
		return equal(lit(childTagName), quote(value));
	}

	public static IXPathExpression equalTagName(String tagName) {
		return equal(name(null), quote(tagName));
	}

	public static IXPathExpression[] equalTagNames(String... tagNames) {
		return createExpressions(tagName -> equalTagName(tagName), tagNames);
	}

	public static IXPathExpression greater(int second) {
		return greater(POSITION, second);
	}

	public static IXPathExpression greater(IXPathExpression first, int second) {
		return greater(first, lit(second));
	}

	public static IXPathExpression greater(IXPathExpression first, IXPathExpression second) {
		return new Greater(first, second);
	}

	public static String hasAttributeNames(String firstAttributeName, String... attributeNames) {
		return hasXPathAttributeNames(WILDCARD, firstAttributeName, attributeNames);
	}

	public static String hasXPathAttributeNames(String xPath, String firstAttributeName, String... attributeNames) {
		return join(lit(xPath), predicate(or(attr(firstAttributeName), createAttrExpressions(attributeNames)))).build();
	}

	public static IXPathExpression join(IXPathExpression first, IXPathExpression... expressions) {
		return new Join(first, expressions);
	}

	public static IXPathExpression join(String first, IXPathExpression... expressions) {
		return join(lit(first), expressions);
	}

	public static IXPathExpression less(int second) {
		return less(POSITION, second);
	}

	public static IXPathExpression less(IXPathExpression first, int second) {
		return less(first, lit(second));
	}

	public static IXPathExpression less(IXPathExpression first, IXPathExpression second) {
		return new Less(first, second);
	}

	public static IXPathExpression lit(int value) {
		return lit(String.valueOf(value));
	}

	public static IXPathExpression lit(String value) {
		return new Lit(value);
	}

	public static IXPathExpression lowerCase(IXPathExpression expression) {
		return translate(expression, quote(ALPHABET_UPPERCASE), quote(ALPHABET_LOWERCASE));
	}

	public static IXPathExpression minus(int second) {
		return new Minus(LAST, lit(second));
	}

	public static IXPathExpression minus(IXPathExpression first, int second) {
		return new Minus(first, lit(second));
	}

	public static IXPathExpression minus(IXPathExpression first, IXPathExpression second) {
		return new Minus(first, second);
	}

	public static IXPathExpression name(@Nullable IXPathExpression expression) {
		return new Name(expression);
	}

	public static IXPathExpression not(IXPathExpression expression) {
		return new Not(expression);
	}

	public static IXPathExpression or(IXPathExpression first, IXPathExpression... expressions) {
		return new Or(first, expressions);
	}

	public static IXPathExpression parentheses(IXPathExpression expression) {
		return new UnaryExpression(expression);
	}

	public static String parenthesesPredicate(String xPath, String predicate) {
		return join(parentheses(lit(xPath)), predicate(lit(predicate))).build();
	}

	public static IXPathExpression path(IXPathExpression first, IXPathExpression... expressions) {
		return new Path(first, expressions);
	}

	public static IXPathExpression path(String first, IXPathExpression... expressions) {
		return path(lit(first), expressions);
	}

	public static String pathes(String first, String... others) {
		return path(first, createLitExpressions(others)).build();
	}

	public static IXPathExpression predicate(IXPathExpression expression) {
		return new Predicate(expression);
	}

	public static IXPathExpression quote(String value) {
		return new Lit(XPathUtil.quote(value));
	}

	public static IXPathExpression self() {
		return parentheses(lit("."));
	}

	public static IXPathExpression startsWith(IXPathExpression first, IXPathExpression second) {
		return new StartsWith(first, second);
	}

	public static IXPathExpression translate(IXPathExpression first, IXPathExpression second, IXPathExpression third) {
		return new Translate(first, second, third);
	}

	public static IXPathExpression union(IXPathExpression first, IXPathExpression... expressions) {
		return new Union(first, expressions);
	}

	public static IXPathExpression union(String first, IXPathExpression... expressions) {
		return union(lit(first), expressions);
	}

	public static String unions(String first, IXPathExpression... others) {
		return union(first, others).build();
	}

	public static String unions(String first, String... others) {
		return unions(first, createLitExpressions(others));
	}
}
