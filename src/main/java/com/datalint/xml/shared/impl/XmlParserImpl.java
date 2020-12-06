package com.datalint.xml.shared.impl;

import org.w3c.dom.Document;

import com.datalint.xml.server.parser.DocumentParser;

public class XmlParserImpl {
	private static XmlParserImpl impl = new XmlParserImpl();

	private XmlParserImpl() {
	}

	public static XmlParserImpl getInstance() {
		return impl;
	}

	public final Document createDocument() {
		return DocumentParser.newDocumentSilent();
	}

	public final Document parse(String contents) {
		return DocumentParser.parseSilent(contents);
	}

	public final Document parseReadOnly(String contents) {
		return DocumentParser.parseReadOnlySilent(contents);
	}

	public boolean supportsCDATASection() {
		return true;
	}
}
