package gwt.xml.shared;

import gwt.xml.shared.expression.*;
import gwt.xml.shared.xpath.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public class XPathBuilder implements ICommon {
    public static final IExpression LAST = new Lit(LAST_F);
    public static final IExpression POSITION = new Lit(POSITION_F);
    public static final IExpression TEXT = new Lit(TEXT_F);

    private static final Supplier<RuntimeException> noValuePresent = () -> new NoSuchElementException("No value present");

    private XPathBuilder() {
    }

    private static IExpression hasAttribute(Map<String, String> attributes, BinaryOperator<IExpression> operator) {
        return attributes.entrySet().stream().map(XPathBuilder::equalAttribute).reduce(operator).orElseThrow(noValuePresent);
    }

    public static IExpression hasAttribute(Map<String, String> attributes) {
        return hasAttribute(attributes, Operator::and);
    }

    public static IExpression hasAttributes(String... attributes) {
        return hasAttribute(CommonUtil.createMap(attributes));
    }

    public static IExpression hasAnyAttributes(Map<String, String> attributes) {
        return hasAttribute(attributes, Operator::or);
    }

    public static IExpression hasAnyAttributes(String... attributes) {
        return hasAnyAttributes(CommonUtil.createMap(attributes));
    }

    public static IExpression hasAnyAttributeValues(String name, String value, List<String> values) {
        IExpression equal = equalAttribute(name, value);

        if (values.size() == 0)
            return equal;

        return or(equal, createExpressions(_value -> equalAttribute(name, _value), values));
    }

    private static List<String> skipFirst(List<String> values) {
        return values.size() == 1 ? Collections.emptyList() : values.subList(1, values.size());
    }

    public static IExpression xPathHasAnyAttributeValues(String xPath, String name, List<String> values) {
        return join(xPath, predicate(hasAnyAttributeValues(name, values.get(0), skipFirst(values))));
    }

    public static IExpression xPathHasAnyAttributeValues(String xPath, String name, String value, String... values) {
        return join(xPath, predicate(hasAnyAttributeValues(name, value, Arrays.asList(values))));
    }

    public static IExpression allHasAnyAttributeValues(String name, String value, String... values) {
        return xPathHasAnyAttributeValues(WILDCARD, name, value, values);
    }

    public static IExpression all(IExpression... expressions) {
        return join(WILDCARD, expressions);
    }

    public static String allTagsExcept(String excludedTagName, String... excludedTagNames) {
        return join(WILDCARD, predicate(not(or(self(excludedTagName), createExpressions(XPathBuilder::self, excludedTagNames))))).build();
    }

    public static IExpression and(IExpression first, IExpression... expressions) {
        return Operator.and(first, expressions);
    }

    public static IExpression attr(String value) {
        return new Lit(AT + value);
    }

    public static IExpression attributeNotStartsWith(String attribute, String query) {
        return and(attr(attribute), not(startsWith(attr(attribute), quote(query))));
    }

    public static String attributeNotStartsWith(String xPath, String attribute, String query) {
        return join(xPath, predicate(attributeNotStartsWith(attribute, query))).build();
    }

    public static IExpression attributeStartsWith(String attribute, String query) {
        return startsWith(attr(attribute), quote(query));
    }

    public static IExpression concat(IExpression first, IExpression... expressions) {
        return new Concat(first, expressions);
    }

    public static IExpression contains(IExpression first, IExpression second) {
        return new Contains(first, second);
    }

    public static IExpression count(IExpression expression) {
        return new Count(expression);
    }

    public static String count(String xPath) {
        return count(lit(xPath)).build();
    }

    public static IExpression[] createAttrExpressions(String[] arguments) {
        return createExpressions(XPathBuilder::attr, arguments);
    }

    public static IExpression[] createExpressions(Function<String, IExpression> function, List<String> arguments) {
        IExpression[] expressions = new IExpression[arguments.size()];

        for (int i = 0; i < expressions.length; i++) {
            expressions[i] = function.apply(arguments.get(i));
        }

        return expressions;
    }

    public static IExpression[] createExpressions(Function<String, IExpression> function, String[] arguments) {
        return createExpressions(function, Arrays.asList(arguments));
    }

    public static IExpression[] createLitExpressions(String[] arguments) {
        return createExpressions(XPathBuilder::lit, arguments);
    }

    public static <T> IExpression createParenthesesAnd(Collection<T> arguments, Function<T, IExpression> creator) {
        int size = arguments.size();

        if (size == 0)
            return null;
        else if (size == 1)
            return creator.apply(arguments.iterator().next());

        IExpression first = null;
        IExpression[] expressions = new IExpression[size - 1];

        int index = 0;
        for (T argument : arguments) {
            IExpression expression = creator.apply(argument);

            if (first == null)
                first = parentheses(expression);
            else
                expressions[index++] = parentheses(expression);
        }

        return and(first, expressions);
    }

    public static IExpression descendants(IExpression first, IExpression... expressions) {
        return new Descendants(first, expressions);
    }

    public static IExpression descendants(String first, IExpression... expressions) {
        return descendants(lit(first), expressions);
    }

    public static IExpression descendantsSelf(IExpression... expressions) {
        return descendants(DOT, expressions);
    }

    public static IExpression descendantsAll(IExpression first) {
        return descendants(first, lit(WILDCARD));
    }

    public static IExpression descendantsAll(String first) {
        return descendants(first, lit(WILDCARD));
    }

    public static String descendantsWithTagNames(String xPath, String tagName, String... tagNames) {
        return descendants(xPath, join(lit(WILDCARD), predicate(or(equalTagName(tagName), equalTagNames(tagNames)))))
                .build();
    }

    public static IExpression equal(IExpression first, IExpression second) {
        return Operator.equal(first, second);
    }

    public static IExpression equalAttribute(Map.Entry<String, String> entry) {
        return equalAttribute(entry.getKey(), entry.getValue());
    }

    public static IExpression equalAttribute(String name, String value) {
        return equal(attr(name), quote(value));
    }

    public static IExpression equalChild(String childTagName, String value) {
        return equal(lit(childTagName), quote(value));
    }

    public static IExpression equalTagName(String tagName) {
        return equal(name(null), quote(tagName));
    }

    public static IExpression[] equalTagNames(String... tagNames) {
        return createExpressions(XPathBuilder::equalTagName, tagNames);
    }

    public static IExpression greater(int second) {
        return greater(POSITION, second);
    }

    public static IExpression greater(IExpression first, int second) {
        return greater(first, lit(second));
    }

    public static IExpression greater(IExpression first, IExpression second) {
        return Operator.greater(first, second);
    }

    public static String hasAttributeNames(String firstAttributeName, String... attributeNames) {
        return hasXPathAttributeNames(WILDCARD, firstAttributeName, attributeNames);
    }

    public static String hasXPathAttributeNames(IExpression expression, String firstAttributeName, String... attributeNames) {
        return join(expression, predicate(or(attr(firstAttributeName), createAttrExpressions(attributeNames)))).build();
    }

    public static String hasXPathAttributeNames(String xPath, String firstAttributeName, String... attributeNames) {
        return hasXPathAttributeNames(lit(xPath), firstAttributeName, attributeNames);
    }

    public static IExpression join(IExpression first, IExpression... expressions) {
        return new Join(first, expressions);
    }

    public static IExpression join(String first, IExpression... expressions) {
        return join(lit(first), expressions);
    }

    public static IExpression xPathPredicate(String xPath, Object predicate) {
        return join(lit(xPath), predicate(lit(predicate)));
    }

    public static IExpression less(int second) {
        return less(POSITION, second);
    }

    public static IExpression less(IExpression first, int second) {
        return less(first, lit(second));
    }

    public static IExpression less(IExpression first, IExpression second) {
        return Operator.less(first, second);
    }

    public static IExpression lit(Object value) {
        return new Lit(value);
    }

    public static IExpression lowerCase(IExpression expression) {
        return translate(expression, quote(ALPHABET_UPPERCASE), quote(ALPHABET_LOWERCASE));
    }

    public static IExpression minus(int second) {
        return Operator.minus(LAST, lit(second));
    }

    public static IExpression minus(IExpression first, int second) {
        return Operator.minus(first, lit(second));
    }

    public static IExpression minus(IExpression first, IExpression second) {
        return Operator.minus(first, second);
    }

    public static IExpression name(IExpression expression) {
        return new Name(expression);
    }

    public static IExpression not(IExpression expression) {
        return new Not(expression);
    }

    public static IExpression or(IExpression first, IExpression... expressions) {
        return Operator.or(first, expressions);
    }

    public static IExpression parentheses(IExpression expression) {
        return new UnaryExpression(expression);
    }

    public static String parenthesesPredicate(String xPath, String predicate) {
        return join(parentheses(lit(xPath)), predicate(lit(predicate))).build();
    }

    public static IExpression path(IExpression first, IExpression... expressions) {
        return new Path(first, expressions);
    }

    public static IExpression path(String first, IExpression... expressions) {
        return path(lit(first), expressions);
    }

    public static String paths(String first, String... others) {
        return path(first, createLitExpressions(others)).build();
    }

    public static IExpression predicate(IExpression expression) {
        return new Predicate(expression);
    }

    public static IExpression quote(String value) {
        return new Lit(XPathUtil.quote(value));
    }

    public static IExpression self() {
        return parentheses(lit(DOT));
    }

    public static IExpression self(String value) {
        return new Self(value);
    }

    public static IExpression startsWith(IExpression first, IExpression second) {
        return new StartsWith(first, second);
    }

    public static IExpression translate(IExpression first, IExpression second, IExpression third) {
        return new Translate(first, second, third);
    }

    public static IExpression union(IExpression first, IExpression... expressions) {
        return new Union(first, expressions);
    }

    public static IExpression union(String first, IExpression... expressions) {
        return union(lit(first), expressions);
    }

    public static String unions(String first, IExpression... others) {
        return union(first, others).build();
    }

    public static String unions(String first, String... others) {
        return unions(first, createLitExpressions(others));
    }
}
