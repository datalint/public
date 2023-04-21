package gwt.xml.shared;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;

import static gwt.xml.shared.XmlUtil.getFirstElementChild;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XPathTest {
    @Test
    public void testEvaluate() {
        String xml = "<web><domain id='1'/><domain id='2'/></web>";
        Element element = XmlParser.parse(xml).getDocumentElement();

        assertTrue(XPath.evaluateNodes(element, "domain/@id").get(0) instanceof Attr);
        assertEquals(Arrays.asList("1", "2"), XPath.evaluateListAttrValues(element, "domain/@id"));

        assertEquals(getFirstElementChild(element), XPath.evaluateElement(element));
        assertEquals(getFirstElementChild(element), element.getFirstChild());

        xml = "<web>\n<domain id='1'/><domain id='2'/></web>";
        element = XmlParser.parse(xml).getDocumentElement();
        assertEquals(getFirstElementChild(element), XPath.evaluateElement(element));
        assertEquals(XPath.evaluateNode(element, "text()"), element.getFirstChild());
    }
}
