package com.datalint.xml.server.parser;

import com.datalint.xml.server.XQuery;
import com.datalint.xml.server.common.BasePooledObjectFactory;
import net.sf.saxon.dom.DocumentBuilderImpl;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

public class DocumentParser {
	private static DocumentBuilder documentBuilderReadOnly;

	public static Document newDocument() throws Exception {
		DocumentBuilder documentBuilder = BuilderFactory.instance.borrowObject();
		try {
			return documentBuilder.newDocument();
		} finally {
			BuilderFactory.instance.returnObject(documentBuilder);
		}
	}

	public static Document newDocumentSilent() {
		try {
			return newDocument();
		} catch (Exception e) {
			return null;
		}
	}

	private static InputSource asInputSource(String xml) {
		return new InputSource(new StringReader(xml));
	}

	public static Document parse(String xml) throws Exception {
		return parse(asInputSource(xml));
	}

	public static Document parse(InputSource inputSource) throws Exception {
		DocumentBuilder documentBuilder = BuilderFactory.instance.borrowObject();
		try {
			return documentBuilder.parse(inputSource);
		} finally {
			BuilderFactory.instance.returnObject(documentBuilder);
		}
	}

	public static Document parseSilent(String xml) {
		return parseSilent(asInputSource(xml));
	}

	public static Document parseSilent(InputSource inputSource) {
		try {
			return parse(inputSource);
		} catch (Exception e) {
			return null;
		}
	}

	public static Document parseReadOnly(String xml) throws Exception {
		if (documentBuilderReadOnly == null) {
			documentBuilderReadOnly = new DocumentBuilderImpl();

			XQuery.setConfiguration((DocumentBuilderImpl) documentBuilderReadOnly);
		}

		return documentBuilderReadOnly.parse(asInputSource(xml));
	}

	public static Document parseReadOnlySilent(String xml) {
		try {
			return parseReadOnly(xml);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T extends DefaultHandler> T parse(String xml, T handler) throws Exception {
		SAXParser sAXParser = ParserFactory.instance.borrowObject();

		try {
			sAXParser.parse(asInputSource(xml), handler);

			return handler;
		} finally {
			ParserFactory.instance.returnObject(sAXParser);
		}
	}

	public static <T extends DefaultHandler> T parseSilent(String xml, T handler) throws Exception {
		try {
			return parse(xml, handler);
		} catch (Exception e) {
			return null;
		}
	}

	public static Element parse(Document owner, String xml) throws Exception {
		return parse(xml, new DocumentSaxHandler(owner)).getRootElement();
	}

	public static Element parseSilent(Document owner, String xml) {
		try {
			return parse(owner, xml);
		} catch (Exception e) {
			return null;
		}
	}

	private static class ParserFactory extends BasePooledObjectFactory<SAXParser> {
		private static ObjectPool<SAXParser> instance = new GenericObjectPool<>(new ParserFactory());

		@Override
		public SAXParser create() throws Exception {
			return SAXParserFactory.newInstance().newSAXParser();
		}

		@Override
		public void passivateObject(PooledObject<SAXParser> wrapper) {
			wrapper.getObject().reset();
		}
	}

	private static class BuilderFactory extends BasePooledObjectFactory<DocumentBuilder> {
		private static ObjectPool<DocumentBuilder> instance = new GenericObjectPool<>(new BuilderFactory());

		@Override
		public DocumentBuilder create() throws Exception {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}

		@Override
		public void passivateObject(PooledObject<DocumentBuilder> wrapper) {
			wrapper.getObject().reset();
		}
	}
}
