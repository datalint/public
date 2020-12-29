package com.datalint.xml.server.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;
import java.util.Deque;

public class DocumentSaxHandler extends DefaultHandler {
	protected final Document document;
	protected final Deque<Element> dequeElement = new ArrayDeque<>();
	protected final StringBuilder textBuilder = new StringBuilder();

	protected Element rootElement;

	public DocumentSaxHandler(Document document) {
		this.document = document;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		Element element = document.createElement(qName);

		for (int i = 0; i < attributes.getLength(); i++) {
			element.setAttribute(attributes.getQName(i), attributes.getValue(i));
		}

		Element last = dequeElement.peekLast();

		appendTextNode(last);

		if (last == null)
			rootElement = element;
		else
			last.appendChild(element);

		dequeElement.add(element);
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		endElement();
	}

	protected Element endElement() {
		Element last = dequeElement.pollLast();

		appendTextNode(last);

		return last;
	}

	protected void appendTextNode(Element last) {
		if (textBuilder.length() > 0) {
			last.appendChild(document.createTextNode(textBuilder.toString()));

			textBuilder.setLength(0);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		textBuilder.append(ch, start, length);
	}

	public Element getRootElement() {
		return rootElement;
	}
}
