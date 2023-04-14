package gwt.xml.client;

import gwt.xml.shared.ICommon;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.typography.Paragraph;
import org.dominokit.domino.ui.utils.DominoElement;

import java.util.Objects;
import java.util.function.BiPredicate;

public class AssertionsC implements ICommon {
    public static <E, A> void assertEquals(E expected, A actual, BiPredicate<E, A> predicate, String message) {
        Paragraph paragraph;
        if (predicate.test(expected, actual))
            paragraph = Paragraph.create(createMessage(expected, actual, true)).setColor(Color.GREEN);
        else
            paragraph = Paragraph.create(message == null ? createMessage(expected, actual, false) : message).
                    setColor(Color.RED);

        DominoElement.body().appendChild(paragraph);
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
        Paragraph paragraph;
        if (!predicate.test(expected, actual))
            paragraph = Paragraph.create(createMessage(expected, actual, false)).setColor(Color.BLUE);
        else
            paragraph = Paragraph.create(message == null ? createMessage(expected, actual, true) : message).
                    setColor(Color.RED);

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
