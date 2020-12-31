package gwt.xml.shared;

import gwt.xml.shared.impl.XsltImpl;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Xslt {
	private final XsltImpl impl = XsltImpl.createInstance();

	private XSLTWrapper wrapper;

	public void importStyleSheet(String styleSheet) {
		impl.importStyleSheet(styleSheet);
	}

	public void importSource(Document document) {
		impl.importSource(document);
	}

	public void importSource(String contents) {
		impl.importSource(contents);
	}

	public void setParameter(String name, String value) {
		impl.setParameter(name, value);
	}

	public String transformToString() {
		return impl.transformToString();
	}

	public Document transformToDocument() {
		return impl.transformToDocument();
	}

	public DocumentFragment transformToFragment() {
		return impl.transformToFragment();
	}

	private XSLTWrapper getWrapper() {
		if (wrapper == null)
			wrapper = new XSLTWrapper();

		return wrapper;
	}

	public String transformToString(Element element) {
		return getWrapper().transformToStringFE(element);
	}

	public Document transformToDocument(Element element) {
		return getWrapper().transformToDocumentFE(element);
	}

	public DocumentFragment transformToFragment(Element element) {
		return getWrapper().transformToFragmentFE(element);
	}

	public void performReplacement(Element element) {
		getWrapper().performReplacement(element);
	}

	private class XSLTWrapper {
		private final Document documentWrapper = XmlParser.createDocument();
		private Node parent;
		private Node refChild;

		private void preTransform(Element element) {
			// Copy the element to the documentWrapper
			parent = element.getParentNode();
			refChild = element.getNextSibling();

			documentWrapper.appendChild(element);

			// Import documentWrapper as a source.
			importSource(documentWrapper);
		}

		private void postTransform(Element element) {
			if (parent == null) {
				documentWrapper.removeChild(element);

				return;
			}

			// Return the element to its original parent
			if (refChild == null)
				parent.appendChild(element);
			else
				parent.insertBefore(element, refChild);
		}

		private String transformToStringFE(Element element) {
			preTransform(element);

			// Do the transform on the documentWrapper
			String result = transformToString();

			postTransform(element);

			return result;
		}

		private Document transformToDocumentFE(Element element) {
			preTransform(element);

			// Do the transform on the documentWrapper
			Document result = transformToDocument();

			postTransform(element);

			return result;
		}

		private DocumentFragment transformToFragmentFE(Element element) {
			preTransform(element);

			// Do the transform on the documentWrapper
			DocumentFragment result = transformToFragment();

			postTransform(element);

			return result;
		}

		private void performReplacement(Element element) {
			preTransform(element);

			parent.insertBefore(transformToDocument().getDocumentElement(), refChild);

			documentWrapper.removeChild(element);
		}
	}
}
