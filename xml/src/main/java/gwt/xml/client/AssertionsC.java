package gwt.xml.client;

import gwt.xml.shared.ICommon;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.typography.Paragraph;
import org.dominokit.domino.ui.utils.DominoElement;
import org.jboss.elemento.IsElement;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AssertionsC implements ICommon {
    private static Consumer<IsElement<?>> appendChildConsumer;

    public static void setAppendChildConsumer(Consumer<IsElement<?>> appendChildConsumer) {
        AssertionsC.appendChildConsumer = appendChildConsumer;
    }

    public static void appendChild(IsElement<?> child) {
        if (appendChildConsumer == null)
            DominoElement.body().appendChild(child);
        else
            appendChildConsumer.accept(child);
    }

    public static <A> boolean assertEquals(A actual, Predicate<A> predicate, String message) {
        boolean isPositive = predicate.test(actual);
        Paragraph paragraph;
        if (isPositive)
            paragraph = Paragraph.create(createMessage(actual, true)).setColor(Color.GREEN);
        else
            paragraph = Paragraph.create(message == null ? createMessage(actual, false) : message).
                    setColor(Color.RED);

        appendChild(paragraph);

        return isPositive;
    }

    public static <A> boolean assertEquals(A actual, Predicate<A> predicate) {
        return assertEquals(actual, predicate, (String) null);
    }

    public static <E, A> boolean assertEquals(E expected, A actual, BiPredicate<E, A> predicate, String message) {
        return appendMessage(expected, actual, predicate.test(expected, actual), false, message);
    }

    public static <E, A> boolean assertEquals(E expected, A actual, BiPredicate<E, A> predicate) {
        return assertEquals(expected, actual, predicate, null);
    }

    public static boolean assertEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, (String) null);
    }

    public static boolean assertEquals(Object expected, Object actual, String message) {
        return assertEquals(expected, actual, Objects::equals, message);
    }

    public static <E, A> boolean assertNotEquals(E expected, A actual, BiPredicate<E, A> predicate, String message) {
        return appendMessage(expected, actual, !predicate.test(expected, actual), true, message);
    }

    private static <E, A> boolean appendMessage(E expected, A actual, boolean isPositive, boolean mask, String message) {
        Paragraph paragraph;
        if (isPositive)
            paragraph = Paragraph.create(createMessage(expected, actual, !mask)).setColor(Color.GREEN);
        else
            paragraph = Paragraph.create(message == null ? createMessage(expected, actual, mask) : message)
                    .setColor(Color.RED);

        appendChild(paragraph);

        return isPositive;
    }

    public static <E, A> boolean assertNotEquals(E expected, A actual, BiPredicate<E, A> predicate) {
        return assertNotEquals(expected, actual, predicate, null);
    }

    public static boolean assertNotEquals(Object expected, Object actual) {
        return assertNotEquals(expected, actual, (String) null);
    }

    public static boolean assertNotEquals(Object expected, Object actual, String message) {
        return assertNotEquals(expected, actual, Objects::equals, message);
    }

    public static boolean assertNull(Object actual) {
        return assertEquals(null, actual);
    }

    public static boolean assertFalse(Object actual) {
        return assertEquals(Boolean.FALSE, actual);
    }

    public static boolean assertTrue(Object actual) {
        return assertEquals(Boolean.TRUE, actual);
    }

    private static String createMessage(Object actual, boolean equals) {
        StringBuilder builder = new StringBuilder();

        builder.append(_APOSTROPHE).append(actual).append(_APOSTROPHE);

        if (equals)
            builder.append(" EXPECTED");
        else
            builder.append(" NOT EXPECTED");

        return builder.toString();
    }

    private static String createMessage(Object expected, Object actual, boolean equals) {
        StringBuilder builder = new StringBuilder();

        builder.append(_APOSTROPHE).append(expected).append(_APOSTROPHE);

        if (equals)
            builder.append(" EQUALS ");
        else
            builder.append(" NOT EQUALS ");

        return builder.append(_APOSTROPHE).append(actual).append(_APOSTROPHE).toString();
    }

    private AssertionsC() {
    }
}
