package com.datalint.xml;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.datalint.xml.server.parser.DocumentSerializer;
import com.datalint.xml.shared.XmlParser;

public class Test {
	public static void main(String[] args) throws Exception {
		String xml = Files.readString(Paths.get("src/test/resources/test.xml"));

		Document oldDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));
		Document newDocument = XmlParser.parse(xml);

		System.out.println(DocumentSerializer.serializeSilent(oldDocument).length());
		System.out.println(newDocument.toString().length());
	}
}
