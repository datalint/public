package gwt.xml.shared;

import gwt.xml.shared.expression.*;
import gwt.xml.shared.xpath.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

import static gwt.xml.shared.CommonUtil.split;

public class XPathBuilder implements ICommon {
    public static final String NODE = "node()";

    public static final IExpression LAST = new Lit("last()");
    public static final IExpression POSITION = new Lit("position()");
    public static final IExpression TEXT = new Lit("text()");

    private XPathBuilder() {
    }

    private static RuntimeException noValuePresent() {
        return new NoSuchElementException("No value present");
    }

    private static IExpression[] wrap(Function<IExpression, IExpression> operator, IExpression... expressions) {
        IExpression[] wrap = new IExpression[expressions.length];

        for (int i = 0; i < wrap.length; i++) {
            wrap[i] = operator.apply(expressions[i]);
        }

        return wrap;
    }

    private static <T> IExpression reduce(Function<T, IExpression> mapper, BinaryOperator<IExpression> accumulator,
                                          Stream<T> stream) {
        return stream.map(mapper).reduce(accumulator).orElseThrow(XPathBuilder::noValuePresent);
    }

    private static <T> IExpression reduce(Function<T, IExpression> mapper, BinaryOperator<IExpression> accumulator,
                                          T... arguments) {
        return reduce(mapper, accumulator, Arrays.stream(arguments));
    }

    private static IExpression hasAttributes(BinaryOperator<IExpression> operator, Map<String, String> attributes) {
        return reduce(XPathBuilder::equalAttribute, operator, attributes.entrySet().stream());
    }

    public static IExpression hasAttributesAnd(Map<String, String> attributes) {
        return hasAttributes(Operator::and, attributes);
    }

    public static IExpression hasAttributesAnd(String... attributes) {
        return hasAttributesAnd(CommonUtil.createMap(attributes));
    }

    public static IExpression hasAttributesOr(Map<String, String> attributes) {
        return hasAttributes(Operator::or, attributes);
    }

    public static IExpression hasAttributesOr(String... attributes) {
        return hasAttributesOr(CommonUtil.createMap(attributes));
    }

    public static IExpression hasAttributeNamesOr(String... attributeNames) {
        return reduce(XPathBuilder::attr, Operator::or, attributeNames);
    }

    public static IExpression hasAttributeValuesOr(String name, Collection<String> values) {
        return split((head, tail) -> hasAttributeValuesOr(name, head, tail), values);
    }

    public static IExpression hasAttributeValuesOr(String name, String value, String... values) {
        IExpression equal = equalAttribute(name, value);

        if (values.length == 0)
            return equal;

        return or(equal, createExpressions(_value -> equalAttribute(name, _value), values));
    }

    public static IExpression hasAttributeValuesParenthesesOr(String name, String value, String... values) {
        IExpression hasAttributeValuesOr = hasAttributeValuesOr(name, value, values);

        return values.length == 0 ? hasAttributeValuesOr : parentheses(hasAttributeValuesOr);
    }

    public static IExpression xPathHasAnyAttributeValues(String xPath, String name, Collection<String> values) {
        return split((head, tail) -> xPathHasAnyAttributeValues(xPath, name, head, tail), values);
    }

    public static IExpression xPathHasAnyAttributeValues(IExpression xPath, String name, String value, String... values) {
        return xPathPredicate(xPath, hasAttributeValuesOr(name, value, values));
    }

    public static IExpression xPathHasAnyAttributeValues(String xPath, String name, String value, String... values) {
        return xPathHasAnyAttributeValues(lit(xPath), name, value, values);
    }

    public static IExpression hasAnyAttributeValues(String name, String value, String... values) {
        return xPathHasAnyAttributeValues(WILDCARD, name, value, values);
    }

    public static IExpression xPathHasExactAttributes(String xPath, Map<String, String> attributes) {
        return xPathPredicate(xPath, hasAttributesAnd(attributes));
    }

    public static IExpression all(IExpression... expressions) {
        return join(WILDCARD, expressions);
    }

    public static IExpression allTags(String includedTagName, String... includedTagNames) {
        return xPathPredicate(WILDCARD, or(self(includedTagName), createExpressions(XPathBuilder::self,
                includedTagNames)));
    }

    public static IExpression allTagsExcept(String excludedTagName, String... excludedTagNames) {
        return xPathPredicate(WILDCARD, not(or(self(excludedTagName), createExpressions(XPathBuilder::self,
                excludedTagNames))));
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

    public static IExpression attributeNotStartsWith(String xPath, String attribute, String query) {
        return xPathPredicate(xPath, attributeNotStartsWith(attribute, query));
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

    public static IExpression count(String expression) {
        return count(lit(expression));
    }

    public static IExpression[] createAttrExpressions(String[] arguments) {
        return createExpressions(XPathBuilder::attr, arguments);
    }

    public static IExpression[] createExpressions(Function<String, IExpression> function, String... arguments) {
        IExpression[] expressions = new IExpression[arguments.length];

        int index = 0;
        for (String argument : arguments) {
            expressions[index++] = function.apply(argument);
        }

        return expressions;
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

    public static IExpression selfDescendant(IExpression... expressions) {
        return descendant(DOT, expressions);
    }

    public static IExpression selfDescendantWildcard(IExpression... expressions) {
        return descendantWildcard(DOT, expressions);
    }

    public static IExpression descendant(IExpression first, IExpression... expressions) {
        return new Descendant(first, expressions);
    }

    public static IExpression descendant(String first, IExpression... expressions) {
        return descendant(lit(first), expressions);
    }

    public static IExpression descendantWildcard(IExpression first, IExpression... expressions) {
        return descendant(first, all(expressions));
    }

    public static IExpression descendantWildcard(String first, IExpression... expressions) {
        return descendantWildcard(lit(first), expressions);
    }

    public static IExpression equal(IExpression first, IExpression second) {
        return Operator.equal(first, second);
    }

    private static IExpression equalAttribute(Map.Entry<String, String> entry) {
        return equalAttribute(entry.getKey(), entry.getValue());
    }

    public static IExpression equalAttribute(String name, String value) {
        if (value == null)
            return attr(name);

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

    public static IExpression greaterEqual(int second) {
        return greaterEqual(POSITION, second);
    }

    public static IExpression greaterEqual(IExpression first, int second) {
        return greaterEqual(first, lit(second));
    }

    public static IExpression greaterEqual(IExpression first, IExpression second) {
        return Operator.greaterEqual(first, second);
    }

    public static IExpression hasAnyAttributeNames(String attributeName, String... attributeNames) {
        return xPathHasAnyAttributeNames(WILDCARD, attributeName, attributeNames);
    }

    public static IExpression xPathHasAnyAttributeNames(IExpression expression, String attributeName,
                                                        String... attributeNames) {
        return xPathPredicate(expression, or(attr(attributeName), createAttrExpressions(attributeNames)));
    }

    public static IExpression xPathHasAnyAttributeNames(String xPath, String attributeName, String... attributeNames) {
        return xPathHasAnyAttributeNames(lit(xPath), attributeName, attributeNames);
    }

    public static IExpression join(IExpression first, IExpression... expressions) {
        return new Join(first, expressions);
    }

    public static IExpression join(String first, IExpression... expressions) {
        return join(lit(first), expressions);
    }

    public static IExpression xPathPredicate(IExpression xPath, IExpression... expressions) {
        return join(xPath, wrap(XPathBuilder::predicate, expressions));
    }

    public static IExpression xPathPredicate(String xPath, IExpression... expressions) {
        return xPathPredicate(lit(xPath), expressions);
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

    public static IExpression lessEqual(int second) {
        return lessEqual(POSITION, second);
    }

    public static IExpression lessEqual(IExpression first, int second) {
        return lessEqual(first, lit(second));
    }

    public static IExpression lessEqual(IExpression first, IExpression second) {
        return Operator.lessEqual(first, second);
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

    public static IExpression parenthesesPredicate(IExpression xPath, IExpression expression) {
        return xPathPredicate(parentheses(xPath), expression);
    }

    public static IExpression parenthesesPredicate(IExpression xPath, String predicate) {
        return parenthesesPredicate(xPath, lit(predicate));
    }

    public static IExpression parenthesesPredicate(String xPath, String predicate) {
        return parenthesesPredicate(lit(xPath), predicate);
    }

    public static IExpression path(IExpression first, IExpression... expressions) {
        return new Path(first, expressions);
    }

    public static IExpression path(IExpression first, String... expressions) {
        return path(first, createLitExpressions(expressions));
    }

    public static IExpression path(String first, IExpression... expressions) {
        return path(lit(first), expressions);
    }

    public static IExpression path(String first, String... expressions) {
        return path(first, createLitExpressions(expressions));
    }

    public static IExpression predicate(IExpression expression) {
        return new Predicate(expression);
    }

    public static IExpression quote(String value) {
        return new Quote(value);
    }

    public static IExpression pathParent(IExpression xPath) {
        return path(xPath, lit(DOUBLE_DOT));
    }

    public static IExpression pathParent(String xPath) {
        return pathParent(lit(xPath));
    }

    public static IExpression self() {
        return parentheses(lit(DOT));
    }

    public static IExpression self(String value) {
        return new Lit(AXIS_SELF + value);
    }

    public static IExpression ancestor(String value) {
        return new Lit(AXIS_ANCESTOR + value);
    }

    public static IExpression ancestorNode() {
        return new Lit(AXIS_ANCESTOR + NODE);
    }

    public static IExpression startsWith(IExpression first, IExpression second) {
        return new StartsWith(first, second);
    }

    public static IExpression translate(IExpression first, IExpression second, IExpression third) {
        return new Translate(first, second, third);
    }

    public static IExpression translateDateTime(IExpression first) {
        return translate(first, quote("-T:"), quote(""));
    }

    public static IExpression union(IExpression first, IExpression... expressions) {
        return new Union(first, expressions);
    }

    public static IExpression union(String first, IExpression... expressions) {
        return union(lit(first), expressions);
    }

    public static IExpression union(String first, String... expressions) {
        return union(first, createLitExpressions(expressions));
    }
}
