package gwt.xml.shared.expression;

public class Operator {
    private Operator() {
    }

    public static IExpression and(IExpression first, IExpression... expressions) {
        return new And(first, expressions);
    }

    public static IExpression comma(IExpression first, IExpression... expressions) {
        return new Comma(first, expressions);
    }

    public static IExpression equal(IExpression first, IExpression second) {
        return new Equal(first, second);
    }

    public static IExpression greater(IExpression first, IExpression second) {
        return new Greater(first, second);
    }

    public static IExpression less(IExpression first, IExpression second) {
        return new Less(first, second);
    }

    public static IExpression minus(IExpression first, IExpression second) {
        return new Minus(first, second);
    }

    public static IExpression or(IExpression first, IExpression... expressions) {
        return new Or(first, expressions);
    }

    public static IExpression plus(IExpression first, IExpression second) {
        return new Plus(first, second);
    }

    private static class And extends Join {
        private And(IExpression first, IExpression... expressions) {
            super(first, expressions);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(AND_WITH_SPACE);
        }
    }

    private static class Comma extends Join {
        private Comma(IExpression first, IExpression... expressions) {
            super(first, expressions);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(_COMMA);
        }
    }

    private static class Equal extends Join {
        private Equal(IExpression first, IExpression second) {
            super(first, second);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(_EQUALS);
        }
    }

    private static class Greater extends Join {
        private Greater(IExpression first, IExpression second) {
            super(first, second);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(_GREATER_THAN);
        }
    }

    private static class Less extends Join {
        private Less(IExpression first, IExpression second) {
            super(first, second);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(_LESS_THAN);
        }
    }

    private static class Minus extends Join {
        private Minus(IExpression first, IExpression second) {
            super(first, second);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(HYPHEN_WITH_SPACE);
        }
    }

    private static class Or extends Join {
        private Or(IExpression first, IExpression... expressions) {
            super(first, expressions);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(OR_WITH_SPACE);
        }
    }

    private static class Plus extends Join {
        private Plus(IExpression first, IExpression second) {
            super(first, second);
        }

        @Override
        protected StringBuilder operator(StringBuilder target) {
            return target.append(PLUS_WITH_SPACE);
        }
    }
}
