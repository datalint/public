package com.datalint.xml.shared;

import com.datalint.xml.shared.impl.XmlParserImpl;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This utility class is used for XML parsing. All operations in this class are
 * thread-safe.
 * <p>
 * The returned <code>Document</code> object can be used on both client and
 * server side.
 * <p>
 * On client side (browser), the returned <code>Document</code> object is
 * thread-safe due to JavaScript's single-threaded nature.
 * <p>
 * On server side, the returned <code>Document</code> object is thread-safe for
 * read-only operations, which means XPath operations can be parallel executed.
 * <p>
 * For write operations, the <code>Document</code> object needs to be external
 * synchronized. In other words, no any read and/or other write operations of
 * different thread are allowed during writing process.
 */
public class XmlParser {
	private static final XmlParserImpl impl = XmlParserImpl.getInstance();

	private XmlParser() {
	}

	/**
	 * This method creates a new document.
	 *
	 * @return the newly created <code>Document</code>
	 */
	public static Document createDocument() {
		return impl.createDocument();
	}

	/**
	 * This method parses a new document from the supplied string.
	 *
	 * @param contents the String to be parsed into a <code>Document</code>
	 * @return the newly created <code>Document</code> or null if the parse fails.
	 */
	public static Document parse(String contents) {
		return parse(contents, null);
	}

	/**
	 * This method parses a new document from the supplied string.
	 *
	 * @param contents         the String to be parsed into a <code>Document</code>
	 * @param exceptionhandler handler used if the parse fails.
	 * @return the newly created <code>Document</code> or null if the parse fails.
	 */
	public static Document parse(String contents, @Nullable Consumer<Exception> exceptionhandler) {
		try {
			return impl.parse(contents);
		} catch (Exception e) {
			if (exceptionhandler != null)
				exceptionhandler.accept(e);

			return null;
		}
	}

	/**
	 * This method removes all <code>Text</code> nodes which are made up of only
	 * white space.
	 *
	 * @param n the node which is to have all of its whitespace descendents removed.
	 */
	public static void removeWhitespace(Node n) {
		removeWhitespaceInner(n, null);
	}

	/**
	 * This method determines whether the browser supports {@link CDATASection} as
	 * distinct entities from <code>Text</code> nodes.
	 *
	 * @return true if the browser supports {@link CDATASection}, otherwise
	 * <code>false</code>.
	 */
	public static boolean supportsCDATASection() {
		return impl.supportsCDATASection();
	}

	/*
	 * The inner recursive method for removeWhitespace
	 */
	private static void removeWhitespaceInner(Node n, Node parent) {
		// This n is removed from the parent if n is a whitespace node
		if (parent != null && n instanceof Text && (!(n instanceof CDATASection))) {
			Text t = (Text) n;
			if (t.getData().matches("[ \t\n]*")) {
				parent.removeChild(t);
			}
		}
		if (n.hasChildNodes()) {
			int length = n.getChildNodes().getLength();
			List<Node> toBeProcessed = new ArrayList<Node>();
			// We collect all the nodes to iterate as the child nodes will
			// change upon removal
			for (int i = 0; i < length; i++) {
				toBeProcessed.add(n.getChildNodes().item(i));
			}
			// This changes the child nodes, but the iterator of nodes never
			// changes meaning that this is safe
			for (Node childNode : toBeProcessed) {
				removeWhitespaceInner(childNode, n);
			}
		}
	}
}
