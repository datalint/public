package gwt.xml.shared.impl;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import gwt.xml.shared.XPath;

class XmlUtilImplIE extends XmlUtilImpl {
	private static final String ATTRIBUTE_HASH_CODE = "_hashCode";
	private static final String ELEMENTS_WITH_HASH_CODE = "//*[@" + ATTRIBUTE_HASH_CODE + ']';

	private static int sHashCode;

	XmlUtilImplIE() {
	}

	@Override
	public int getHashCode(Element element) {
		String hashCode = element.getAttribute(ATTRIBUTE_HASH_CODE);

		if (hashCode != null) {
			try {
				return Integer.parseInt(hashCode);
			} catch (NumberFormatException e) {
			}
		}

		element.setAttribute(ATTRIBUTE_HASH_CODE, String.valueOf(++sHashCode));

		return sHashCode;
	}

	@Override
	public boolean isTemporaryNode(String nodeName) {
		return nodeName.startsWith("_");
	}

	@Override
	public String toString(Node node) {
		Node clone = node.cloneNode(true);

		if (clone instanceof Element) {
			List<Element> elements = XPath.evaluateNodes((Element) clone, ELEMENTS_WITH_HASH_CODE);
			for (Element target : elements) {
				target.removeAttribute(ATTRIBUTE_HASH_CODE);
			}
		}

		return super.toString(clone);
	}
}
