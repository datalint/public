package gwt.xml.client;

import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.Arrays;

import static gwt.xml.client.AssertionsC.*;
import static gwt.xml.shared.XPath.*;
import static gwt.xml.shared.XmlUtil.getFirstElementChild;

public class XPathTestC {
    private static final XPathTestC instance = new XPathTestC();

    public static XPathTestC getInstance() {
        return instance;
    }

    private XPathTestC() {
    }

    public void testEvaluate() {
        String xml = "<web><domain id='1'/><domain id='2'>domain <child/> 2</domain></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertEquals("<domain id=\"2\">domain <child/> 2</domain>", evaluateNode(element, "domain[@id='2']").toString());
        assertEquals("domain ", evaluateNode(element, "domain[@id='2']/text()").toString());
        assertEquals("[<domain id=\"2\">domain <child/> 2</domain>]", evaluateNodes(element, "domain[@id='2']").toString());
        assertEquals("[domain ,  2]", evaluateNodes(element, "domain[@id='2']/text()").toString());

        assertEquals("", evaluateString(element, "domain"));
        assertEquals("domain ", evaluateString(element, "domain/text()"));
        assertEquals("", evaluateString(element, "domain[@id='1']"));
        assertEquals("", evaluateString(element, "domain[@id='1']/text()"));
        assertEquals("domain  2", evaluateString(element, "domain[@id='2']"));
        assertEquals("domain ", evaluateString(element, "domain[@id='2']/text()"));

        assertEquals("domain ", XPath.<Text>evaluateNode(element, "domain/text()").toString());
        assertNull(XPath.<Text>evaluateNode(element, "domain[@id='1']/text()"));
        assertEquals("domain ", XPath.<Text>evaluateNode(element, "domain[@id='2']/text()").toString());
        assertEquals("domain ", evaluateText(XPath.<Element>evaluateNode(element, "domain[@id='2']")));

        assertTrue(evaluateNodes(element, "domain/@id").get(0) instanceof Attr);
        assertEquals(Arrays.asList("1", "2"), evaluateListAttrValues(element, "domain/@id"));

        assertEquals(getFirstElementChild(element), evaluateElement(element));
        assertEquals(getFirstElementChild(element), element.getFirstChild());

        xml = "<web>\n<domain id='1'/><domain id='2'/></web>";
        element = XmlParser.parse(xml).getDocumentElement();
        assertEquals(getFirstElementChild(element), evaluateElement(element));
        assertEquals(evaluateNode(element, "text()"), element.getFirstChild());
    }
}
