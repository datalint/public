package gwt.xml.client;

import gwt.xml.shared.XmlParser;
import org.w3c.dom.Element;

import static gwt.xml.client.AssertionsC.assertEquals;
import static gwt.xml.client.AssertionsC.assertNull;
import static gwt.xml.shared.XPathUtil.getText;

public class XPathUtilTestC {
    private static final XPathUtilTestC instance = new XPathUtilTestC();

    public static XPathUtilTestC getInstance() {
        return instance;
    }

    private XPathUtilTestC() {
    }

    public void testUtil() {
        String xml = "<web><domain id='1'/><domain id='2'>domain <child/> 2</domain></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertEquals("", getText(element, "domain"));
        assertEquals("", getText(element, "domain[@id='1']"));
        assertEquals("domain ", getText(element, "domain[@id='2']"));
        assertNull(getText(element, "domain[@id='3']"));
    }
}
