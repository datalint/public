package gwt.xml.client;

import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;

import static gwt.xml.client.AssertionsC.assertEquals;

public class XPathTestC {
    private static final XPathTestC instance = new XPathTestC();

    public static XPathTestC getInstance() {
        return instance;
    }

    private XPathTestC() {
    }

    public void testEvaluate() {
        String xml = "<web><domain id='1'/><domain id='2'/></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertEquals(XPath.evaluateNodes(element, "domain/@id").get(0), actual -> actual instanceof Attr);
        assertEquals(Arrays.asList("1", "2"), XPath.evaluateListAttrValues(element, "domain/@id"));
    }
}
