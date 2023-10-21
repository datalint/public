package gwt.xml.client;

import gwt.xml.shared.ICommon;

import static gwt.xml.client.AssertionsC.assertEquals;

public class CommonTestC implements ICommon {
    private static final CommonTestC instance = new CommonTestC();

    public static CommonTestC getInstance() {
        return instance;
    }

    private CommonTestC() {
    }

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
