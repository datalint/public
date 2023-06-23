package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import static gwt.xml.client.AssertionsC.assertEquals;
import static gwt.xml.client.AssertionsC.assertNotEquals;
import static org.w3c.dom.Node.*;

public class XmlClient implements EntryPoint {
    private static void testCompareDocumentPosition() {
        Document parse = XmlParser.parse("<root><a/><b/></root>");

        Node a = XPath.evaluateNode(parse, "a");
        Node b = XPath.evaluateNode(parse, "b");

        assertEquals("<a/>", a.toString());
        assertEquals("<b/>", b.toString());
        assertEquals(DOCUMENT_POSITION_FOLLOWING, a.compareDocumentPosition(b));
        assertEquals(DOCUMENT_POSITION_PRECEDING, b.compareDocumentPosition(a));
        assertEquals(DOCUMENT_POSITION_PRECEDING | DOCUMENT_POSITION_CONTAINS,
                (int) b.compareDocumentPosition(parse));
        assertEquals(DOCUMENT_POSITION_FOLLOWING | DOCUMENT_POSITION_CONTAINED_BY,
                (int) parse.compareDocumentPosition(parse.getDocumentElement()));
    }

    private static void testTextContent() {
        String xml = "<div id=\"divA\">This is <span>some</span> text!</div>";
        Element element = XmlParser.parse(xml).getDocumentElement();
        assertEquals("<div id=\"divA\">This is <span>some</span> text!</div>", element.toString());
        assertEquals("This is some text!", element.getTextContent());

        String textContent = "This text is different!";
        element.setTextContent(textContent);
        assertEquals("<div id=\"divA\">This text is different!</div>", element.toString());
        assertEquals("This text is different!", element.getTextContent());
    }

    private static void testIsEqualNode() {
        String xml = "<div id='divA'>This is <span>some</span> text!</div>";
        Document document = XmlParser.parse(xml);
        Element element = document.getDocumentElement();

        assertEquals(document, element.getOwnerDocument(), Node::isEqualNode);

        Document documentTwo;
        documentTwo = XmlParser.parse(xml);
        assertEquals(document, documentTwo, Node::isEqualNode);
        assertEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);
        Element span = XPath.evaluateNode(document, "span");
        Element spanTwo = XPath.evaluateNode(documentTwo, "span");
        assertEquals(span, spanTwo, Node::isEqualNode);

        spanTwo.setAttribute("name", "a name");
        assertNotEquals(span, spanTwo, Node::isEqualNode);
        assertNotEquals(document, documentTwo, Node::isEqualNode);
        assertNotEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);

        span.setAttribute("name", "a different name");
        assertNotEquals(span, spanTwo, Node::isEqualNode);
        assertNotEquals(document, documentTwo, Node::isEqualNode);
        assertNotEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);

        span.setAttribute("name", "a name");
        assertEquals(span, spanTwo, Node::isEqualNode);
        assertEquals(document, documentTwo, Node::isEqualNode);
        assertEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);

        Text text = XPath.evaluateNode(span, "text()");
        Text textTwo = XPath.evaluateNode(spanTwo, "text()");
        assertEquals(text, textTwo, Node::isEqualNode);

        textTwo.setNodeValue("some some");
        assertNotEquals(text, textTwo, Node::isEqualNode);
        assertNotEquals(document, documentTwo, Node::isEqualNode);
        assertNotEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);

        text.setNodeValue("some some");
        assertEquals(text, textTwo, Node::isEqualNode);
        assertEquals(document, documentTwo, Node::isEqualNode);
        assertEquals(element, documentTwo.getDocumentElement(), Node::isEqualNode);
    }

    public void testImportNode() {
        String xml = "<a id='a1'><b name='b1'><c name='c1'/></b></a>";
        Document document1 = XmlParser.parse(xml);
        Document document2 = XmlParser.parse(xml);
        Document document3 = XmlParser.parse(xml);

        assertEquals(document1, document2, Node::isEqualNode);
        assertEquals(document1, document3, Node::isEqualNode);

        Node importNode = document3.importNode(document1.getDocumentElement().getFirstChild(), true);
        assertEquals(document1, document2, Node::isEqualNode);
        assertEquals(document1, document3, Node::isEqualNode);

        assertEquals(document3, importNode.getOwnerDocument());

        document3.getDocumentElement().appendChild(importNode);
        assertEquals(document1, document2, Node::isEqualNode);
        assertNotEquals(document1, document3, Node::isEqualNode);

        assertEquals("<a id=\"a1\"><b name=\"b1\"><c name=\"c1\"/></b><b name=\"b1\"><c name=\"c1\"/></b></a>",
                document3.toString());
    }

    @Override
    public void onModuleLoad() {
        CommonTestC.getInstance().testAfterAndBefore();

        testCompareDocumentPosition();
        testTextContent();
        testIsEqualNode();
        testImportNode();

        XmlBuilderTestC.getInstance().testBuild();
        XPathBuilderTestC.getInstance().testBuild();
        XPathTestC.getInstance().testEvaluate();
        XPathUtilTestC.getInstance().testUtil();
    }
}
