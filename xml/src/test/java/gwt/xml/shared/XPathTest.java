package gwt.xml.shared;

import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XPathTest {
    @Test
    public void testEvaluate() {
        String xml = "<web><domain id='1'/><domain id='2'/></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertTrue(XPath.evaluateNodes(element, "domain/@id").get(0) instanceof Attr);
        assertEquals(Arrays.asList("1", "2"), XPath.evaluateListAttrValues(element, "domain/@id"));
    }
}
