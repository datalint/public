package com.datalint.xml.shared.impl;

import org.w3c.dom.Document;

import com.datalint.xml.server.dom.DocumentImpl;
import com.datalint.xml.server.parser.DocumentParser;

public class XmlParserImpl {
	private static XmlParserImpl impl = new XmlParserImpl();

	private XmlParserImpl() {
	}

	public static XmlParserImpl getInstance() {
		return impl;
	}

	public final Document createDocument() {
		return new DocumentImpl();
	}

	public final Document parse(String contents) throws Exception {
		Document document = new DocumentImpl();

		document.appendChild(DocumentParser.parse(document, contents));

		return document;
	}

	public boolean supportsCDATASection() {
		return true;
	}
}
