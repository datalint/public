package gwt.xml;

import gwt.xml.server.parser.DocumentSerializer;
import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import gwt.xml.shared.XmlUtil;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTest {
    @Test
    public void testAll() throws Exception {
        String xml = Files.readString(Paths.get("src/test/resources/test.xml"));

        Document oldDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        oldDocument.normalizeDocument();
        Document newDocument = XmlParser.parse(xml);

        assertEquals(newDocument.toString(), XmlUtil.serializeToString(newDocument));

        assertEquals(DocumentSerializer.serialize(oldDocument).length(),
                DocumentSerializer.serialize(newDocument).length());
        assertEquals(DocumentSerializer.serialize(oldDocument).length(), newDocument.toString().length());

        assertEquals(6, oldDocument.getElementsByTagName("*").getLength());
        assertEquals(5, oldDocument.getDocumentElement().getElementsByTagName("*").getLength());
        assertEquals(3, oldDocument.getElementsByTagName("two").getLength());
        assertEquals(6, newDocument.getElementsByTagName("*").getLength());
        assertEquals(5, newDocument.getDocumentElement().getElementsByTagName("*").getLength());
        assertEquals(3, newDocument.getElementsByTagName("two").getLength());

        assertEquals(oldDocument.getDocumentElement(), XPath.evaluateNode(oldDocument, "."));
        assertEquals(oldDocument.getDocumentElement(), XPath.evaluateNode(oldDocument, "//root"));
        assertEquals(newDocument.getDocumentElement(), XPath.evaluateNode(newDocument, "."));
        assertEquals(newDocument.getDocumentElement(), XPath.evaluateNode(newDocument, "//root"));

        assertEquals(3, XPath.evaluateNodes(oldDocument, "one/two").size());
        assertEquals(XmlUtil.getFirstChildElement(oldDocument.getDocumentElement()),
                XPath.evaluateNode(oldDocument, "one"));
        assertEquals(3, XPath.evaluateNodes(XPath.<Element>evaluateNode(oldDocument, "one"), "two").size());
        assertEquals(3, XPath.evaluateNodes(newDocument, "one/two").size());
        assertEquals(XmlUtil.getFirstChildElement(newDocument.getDocumentElement()),
                XPath.evaluateNode(newDocument, "one"));
        assertEquals(3, XPath.evaluateNodes(XPath.<Element>evaluateNode(newDocument, "one"), "two").size());

        assertEquals("root", XPath.evaluateString(oldDocument, "@id"));
        assertEquals("root", XPath.evaluateString(newDocument, "@id"));

        assertEquals("3", XPath.evaluateString(oldDocument, "//two[@id='two 3']"));
        assertEquals("3", XPath.evaluateString(newDocument, "//two[@id='two 3']"));

        assertEquals(3.0, XPath.evaluateNumber(oldDocument, "count(//two)"), .0);
        assertEquals(3.0, XPath.evaluateNumber(newDocument, "count(//two)"), .0);

        assertEquals(3, XPath.evaluatePosition(XPath.<Element>evaluateNode(oldDocument, "one"),
                XPath.evaluateNode(oldDocument, "one/two[@id='two 3']")));
        assertEquals(3, XPath.evaluatePosition(XPath.<Element>evaluateNode(newDocument, "one"),
                XPath.evaluateNode(newDocument, "one/two[@id='two 3']")));
        assertEquals(2, XPath.evaluatePosition(oldDocument, "one/two", XPath.evaluateNode(oldDocument, "one/two[2]")));
        assertEquals(2, XPath.evaluatePosition(newDocument, "one/two", XPath.evaluateNode(newDocument, "one/two[2]")));

        assertEquals(3, XPath.evaluateListAttrValues(oldDocument, "//*/@name").size());
        assertEquals(3, XPath.evaluateListAttrValues(newDocument, "//*/@name").size());

        assertEquals(2, XPath.evaluateAttrValues(oldDocument, "//two/@name").size());
        assertEquals(2, XPath.evaluateAttrValues(newDocument, "//two/@name").size());

        assertEquals(2, XPath.evaluateTextValues(oldDocument, "one/two/text()").size());
        assertEquals(2, XPath.evaluateTextValues(newDocument, "one/two/text()").size());

        assertEquals(2, XPath.evaluateElements(oldDocument, "//two[@name]").size());
        assertEquals(2, XPath.evaluateElements(newDocument, "//two[@name]").size());

        assertEquals(2, XPath.evaluateElements(oldDocument, "//two[@name]").size());
        assertEquals(2, XPath.evaluateElements(newDocument, "//two[@name]").size());

        assertEquals(XPath.evaluateElementsMap(oldDocument, ".//two[@name]", "name").keySet(),
                XPath.evaluateElementsMap(newDocument, ".//two[@name]", "name").keySet());

        assertEquals(XPath.evaluateListElementsMap(oldDocument, "//*", "name").keySet(),
                XPath.evaluateListElementsMap(newDocument, "//*", "name").keySet());

        assertEquals(XPath.evaluateElement(oldDocument).getTagName(), XPath.evaluateElement(newDocument).getTagName());

        assertEquals(XPath.evaluateNumber(oldDocument), XPath.evaluateNumber(newDocument));

        assertEquals(XPath.evaluateText(oldDocument), XPath.evaluateText(newDocument));

        assertEquals(oldDocument.getDocumentElement().getTextContent(), newDocument.getDocumentElement().getTextContent());
    }

    @Test
    public void testDetermineLevel() {
        Document document = XmlParser.parse("<root><a name='1_1'><a name='2_1'></a><a name='2_2'><a name='3_1'>" +
                "</a></a><a name='2_3'></a></a><a name='1_2'></a><a name='1_3'></a></root>");

        assertEquals(0, XmlUtil.determineLevel(document.getDocumentElement()));
        assertEquals(2, XmlUtil.determineLevel(document.getDocumentElement().getFirstChild().getFirstChild()));
    }

    @Test
    public void testCompareDocumentPosition() {
        Document document = XmlParser.parse("<root><a name='1_1'><a name='2_1'></a><a name='2_2'><a name='3_1'>" +
                "</a></a><a name='2_3'></a></a><a name='1_2'></a><a name='1_3'></a></root>");

        Element a1 = XPath.evaluateNode(document, "a[1]");
        Element a1_1 = XPath.evaluateNode(document, "a[1]/a[1]");
        Element a1_2 = XPath.evaluateNode(document, "a[1]/a[2]");

        assertEquals(0, a1.compareDocumentPosition(XPath.evaluateNode(document, "a[1]")));
        assertEquals(0, a1_1.compareDocumentPosition(a1_1));
        assertEquals(20, a1.compareDocumentPosition(a1_1));
        assertEquals(10, a1_1.compareDocumentPosition(a1));
        assertEquals(4, a1_1.compareDocumentPosition(a1_2));
        assertEquals(2, a1_2.compareDocumentPosition(a1_1));
        assertEquals(1, a1_2.compareDocumentPosition(XmlParser.parse("<a/>").getDocumentElement()));

        assertEquals(10, a1.compareDocumentPosition(document));
        assertEquals(20, document.compareDocumentPosition(a1));
        assertEquals(10, document.getDocumentElement().compareDocumentPosition(document));
        assertEquals(20, document.compareDocumentPosition(document.getDocumentElement()));
    }
}
