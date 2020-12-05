package com.datalint.xml;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.datalint.xml.server.DocumentImpl;

public class XmlTest {
	@Test
	public void test() throws Exception {
		run(new DocumentImpl());
		run(DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument());

		assertEquals(true, true);
	}

	private void run(Document document) {
		Element a = document.createElement("a");
		System.out.println(a.getOwnerDocument() + "\t" + a.getParentNode());
		document.appendChild(a);
		System.out.println(a.getOwnerDocument() + "\t" + a.getParentNode());

		a.setAttribute("x", "1");
		a.setAttribute("y", "2");

		Text text = document.createTextNode("data");

		a.appendChild(text);
		System.out.println(text.getParentNode());
		System.out.println(text.splitText(2).getParentNode());

		System.out.println(document.getElementById("1"));

		Element b = document.createElement("b");

		b.setAttribute("z", "2");
		a.appendChild(b);

		System.out.println(document.getElementById("2"));
	}
}
