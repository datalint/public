package gwt.xml.shared.impl;

import gwt.xml.server.dom.DocumentImpl;
import gwt.xml.server.parser.DocumentParser;
import org.w3c.dom.Document;

public class XmlParserImpl {
	private static final XmlParserImpl impl = new XmlParserImpl();

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
