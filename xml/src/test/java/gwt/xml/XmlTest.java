package gwt.xml;

import gwt.xml.server.parser.DocumentSerializer;
import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import gwt.xml.shared.XmlUtil;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class XmlTest implements IDomTest {
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

    @Test
    public void testGetElementById() {
        String xml = "<root><a id='a1' name='na1'><b id='b1' name='nb1'/></a></root>";

        Document document = XmlParser.parse(xml);
        Element a1 = document.getElementById("a1");
        assertNotNull(a1);

        assertNotNull(document.getElementById("b1"));
        assertNull(document.getElementById("b2"));

        document.getElementById("b1").setAttribute("name", "nb2");
        assertNotNull(document.getElementById("b1"));

        document.getElementById("b1").setAttribute("id", "b2");
        assertNull(document.getElementById("b1"));
        assertNotNull(document.getElementById("b2"));

        Element b2 = document.getElementById("b2");
        b2.removeAttribute("id");
        assertNull(document.getElementById("b2"));

        Element b3 = document.createElement("b");
        b3.setAttribute("id", "b3");
        assertNull(document.getElementById("b3"));
        a1.appendChild(b3);
        assertNotNull(document.getElementById("b3"));

        Element b4 = document.createElement("b");
        b4.setAttribute("id", "b4");
        a1.replaceChild(b4, b3);
        assertNull(document.getElementById("b3"));
        assertNotNull(document.getElementById("b4"));

        a1.removeChild(b4);
        assertNull(document.getElementById("b4"));

        a1.setAttribute("id", "a2");
        assertNull(document.getElementById("a1"));
        assertNotNull(document.getElementById("a2"));
    }

    @Test
    public void testTextContent() {
        String xml = "<div id=\"divA\">This is <span>some</span> text!</div>";
        Element element = XmlParser.parse(xml).getDocumentElement();
        assertEquals(xml, element.toString());
        assertEquals("This is some text!", element.getTextContent());

        String textContent = "This text is different!";
        element.setTextContent(textContent);
        assertEquals("<div id=\"divA\">This text is different!</div>", element.toString());
        assertEquals(textContent, element.getTextContent());
    }

    @Test
    public void testEquals() {
        String xml = "<div id='divA'>This is <span>some</span> text!</div>";
        Document document = XmlParser.parse(xml);
        Element element = document.getDocumentElement();

        assertEqualNode(document, element.getOwnerDocument());

        Document documentTwo;
        documentTwo = XmlParser.parse(xml);
        assertEqualNode(document, documentTwo);
        assertEqualNode(element, documentTwo.getDocumentElement());
        Element span = XPath.evaluateNode(document, "span");
        Element spanTwo = XPath.evaluateNode(documentTwo, "span");
        assertEqualNode(span, spanTwo);

        spanTwo.setAttribute("name", "a name");
        assertNotEqualNode(span, spanTwo);
        assertNotEqualNode(document, documentTwo);
        assertNotEqualNode(element, documentTwo.getDocumentElement());

        span.setAttribute("name", "a different name");
        assertNotEqualNode(span, spanTwo);
        assertNotEqualNode(document, documentTwo);
        assertNotEqualNode(element, documentTwo.getDocumentElement());

        span.setAttribute("name", "a name");
        assertEqualNode(span, spanTwo);
        assertEqualNode(document, documentTwo);
        assertEqualNode(element, documentTwo.getDocumentElement());


        Text text = XPath.evaluateNode(span, "text()");
        Text textTwo = XPath.evaluateNode(spanTwo, "text()");
        assertEqualNode(text, textTwo);

        textTwo.setNodeValue("some some");
        assertNotEqualNode(text, textTwo);
        assertNotEqualNode(document, documentTwo);
        assertNotEqualNode(element, documentTwo.getDocumentElement());

        text.setNodeValue("some some");
        assertEqualNode(text, textTwo);
        assertEqualNode(document, documentTwo);
        assertEqualNode(element, documentTwo.getDocumentElement());
    }

    @Test
    public void testNamedNodeMapImpl() {
        String xml = "<div id='divA' name=''/>";
        Element element = XmlParser.parse(xml).getDocumentElement();
        NamedNodeMap namedNodeMap = element.getAttributes();

        assertEquals(2, namedNodeMap.getLength());
        assertNull(namedNodeMap.removeNamedItem("wrongId"));

        Node id = namedNodeMap.removeNamedItem("id");
        assertEquals("divA", id.getNodeValue());
        assertEquals(1, namedNodeMap.getLength());

        element.setAttribute("newValue", "newValue");
        assertEquals("newValue", element.getAttribute("newValue"));
        assertEquals(2, namedNodeMap.getLength());

        namedNodeMap.setNamedItem(id.cloneNode(false));
        assertEquals("divA", element.getAttribute("id"));
        assertEquals(3, namedNodeMap.getLength());
    }

    @Test
    public void testImportNode() {
        String xml = "<a id='a1'><b name='b1'><c name='c1'/></b></a>";
        Document document1 = XmlParser.parse(xml);
        Document document2 = XmlParser.parse(xml);
        Document document3 = XmlParser.parse(xml);

        assertTrue(document1.isEqualNode(document2));
        assertTrue(document1.isEqualNode(document3));

        Node importNode = document3.importNode(document1.getDocumentElement().getFirstChild(), true);
        assertTrue(document1.isEqualNode(document2));
        assertTrue(document1.isEqualNode(document3));

        assertEquals(document3, importNode.getOwnerDocument());

        document3.getDocumentElement().appendChild(importNode);
        assertTrue(document1.isEqualNode(document2));
        assertFalse(document1.isEqualNode(document3));

        assertEquals("<a id=\"a1\"><b name=\"b1\"><c name=\"c1\"/></b><b name=\"b1\"><c name=\"c1\"/></b></a>",
                document3.toString());
    }
}
