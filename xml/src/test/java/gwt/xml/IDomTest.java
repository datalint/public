package gwt.xml;

import org.w3c.dom.Node;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface IDomTest {
    default void assertEqualNode(Node expected, Node actual) {
        assertTrue(expected.isEqualNode(actual), messageSupplier(expected, actual));
    }

    default void assertNotEqualNode(Node expected, Node actual) {
        assertTrue(!expected.isEqualNode(actual), messageSupplier(expected, actual));
    }

    default Supplier<String> messageSupplier(Node expected, Node actual) {
        return () -> "expected " + expected + ", but was " + actual;
    }
}
