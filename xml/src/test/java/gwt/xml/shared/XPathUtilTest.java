package gwt.xml.shared;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import static gwt.xml.shared.XPathUtil.getText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class XPathUtilTest {
    @Test
    public void testUtil() {
        String xml = "<web><domain id='1'/><domain id='2'>domain <child/> 2</domain></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertEquals("", getText(element, "domain"));
        assertEquals("", getText(element, "domain[@id='1']"));
        assertEquals("domain ", getText(element, "domain[@id='2']"));
        assertNull(getText(element, "domain[@id='3']"));
    }
}
