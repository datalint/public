package gwt.xml.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTest implements ICommon {
    @Test
    public void test() {
        String source = "test.pdf";

        assertEquals("test", beforeLast(source, '.'));
        assertEquals("test.pdf", beforeLast(source, ' '));
    }
}
