package gwt.xml.client;

import gwt.xml.shared.ICommon;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.typography.Paragraph;
import org.dominokit.domino.ui.utils.DominoElement;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class AssertionsC implements ICommon {
    public static <A> void assertEquals(A actual, Predicate<A> predicate, String message) {
        Paragraph paragraph;
        if (predicate.test(actual))
            paragraph = Paragraph.create(createMessage(actual, true)).setColor(Color.GREEN);
        else
            paragraph = Paragraph.create(message == null ? createMessage(actual, false) : message).
                    setColor(Color.RED);

        DominoElement.body().appendChild(paragraph);
    }

    public static <A> void assertEquals(A actual, Predicate<A> predicate) {
        assertEquals(actual, predicate, (String) null);
    }

    public static <E, A> void assertEquals(E expected, A actual, BiPredicate<E, A> predicate, String message) {
        appendMessage(expected, actual, predicate.test(expected, actual), false, message);
    }

    public static <E, A> void assertEquals(E expected, A actual, BiPredicate<E, A> predicate) {
        assertEquals(expected, actual, predicate, null);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, (String) null);
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        assertEquals(expected, actual, Objects::equals, message);
    }

    public static <E, A> void assertNotEquals(E expected, A actual, BiPredicate<E, A> predicate, String message) {
        appendMessage(expected, actual, !predicate.test(expected, actual), true, message);
    }

    private static <E, A> void appendMessage(E expected, A actual, boolean isPositive, boolean mask, String message) {
        Paragraph paragraph;
        if (isPositive)
            paragraph = Paragraph.create(createMessage(expected, actual, !mask)).setColor(Color.GREEN);
        else
            paragraph = Paragraph.create(message == null ? createMessage(expected, actual, mask) : message)
                    .setColor(Color.RED);

        DominoElement.body().appendChild(paragraph);
    }

    public static <E, A> void assertNotEquals(E expected, A actual, BiPredicate<E, A> predicate) {
        assertNotEquals(expected, actual, predicate, null);
    }

    public static void assertNotEquals(Object expected, Object actual) {
        assertNotEquals(expected, actual, (String) null);
    }

    public static void assertNotEquals(Object expected, Object actual, String message) {
        assertNotEquals(expected, actual, Objects::equals, message);
    }

    public static void assertNull(Object actual) {
        assertEquals(null, actual);
    }

    public static void assertTrue(Object actual) {
        assertEquals(Boolean.TRUE, actual);
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
