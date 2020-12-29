package gwt.xml;

import gwt.xml.server.parser.DocumentSerializer;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) throws Exception {
		String xml = Files.readString(Paths.get("src/test/resources/test.xml"));

		Document oldDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));
		Document newDocument = XmlParser.parse(xml);

		System.out.println(DocumentSerializer.serializeSilent(oldDocument));
		System.out.println(newDocument.toString());
	}
}
