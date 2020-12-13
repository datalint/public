package com.datalint.xml;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.datalint.xml.shared.XPath;
import com.datalint.xml.shared.XmlParser;
import com.datalint.xml.shared.XmlUtil;

public class XmlTest {
	@Test
	public void testAll() throws Exception {
		String xml = Files.readString(Paths.get("src/test/resources/test.xml"));

		Document oldDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));
		oldDocument.normalizeDocument();
		Document newDocument = XmlParser.parse(xml);

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
	}
}
