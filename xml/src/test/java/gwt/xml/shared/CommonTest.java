package gwt.xml.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTest implements ICommon {
    @Test
    public void testAfterAndBefore() {
        String source = "com.datalint.core";

        assertEquals("datalint.core", after(source, '.'));
        assertEquals(source, after(source, ' '));

        assertEquals("core", afterLast(source, '.'));
        assertEquals(source, afterLast(source, ' '));

        assertEquals("com", before(source, '.'));
        assertEquals(source, before(source, ' '));

        assertEquals("com.datalint", beforeLast(source, '.'));
        assertEquals(source, beforeLast(source, ' '));
    }
}
