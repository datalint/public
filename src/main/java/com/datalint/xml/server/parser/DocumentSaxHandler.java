package com.datalint.xml.server.parser;

import java.util.ArrayDeque;
import java.util.Deque;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DocumentSaxHandler extends DefaultHandler {
	protected final Document document;
	protected final Deque<Element> dequeElement = new ArrayDeque<>();
	protected final StringBuilder text = new StringBuilder();

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

		if (text.length() > 0) {
			last.appendChild(document.createTextNode(text.toString()));

			text.setLength(0);
		}

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

		if (text.length() > 0) {
			last.appendChild(document.createTextNode(text.toString()));
			text.setLength(0);
		}

		return last;
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		text.append(ch, start, length);
	}

	public Element getRootElement() {
		return rootElement;
	}
}
