package gwt.xml;

import org.w3c.dom.Node;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface IDomTest {
    default void assertTrueIsEqualNode(Node expected, Node actual) {
        assertTrue(expected.isEqualNode(actual), messageSupplier(expected, actual));
    }

    default void assertFalseIsEqualNode(Node expected, Node actual) {
        assertFalse(expected.isEqualNode(actual), messageSupplier(expected, actual));
    }

    default Supplier<String> messageSupplier(Node expected, Node actual) {
        return () -> "\nexpected " + expected + "\nbut was " + actual;
    }
}
