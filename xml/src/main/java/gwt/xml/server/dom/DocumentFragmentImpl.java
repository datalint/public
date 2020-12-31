package gwt.xml.server.dom;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

public class DocumentFragmentImpl extends ParentNode implements DocumentFragment {
	public DocumentFragmentImpl(Node owner) {
		super(owner);
	}

	@Override
	public String getNodeName() {
		return "#document-fragment";
	}

	@Override
	public short getNodeType() {
		return DOCUMENT_FRAGMENT_NODE;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new DocumentFragmentImpl(getOwnerDocument());
	}

	@Override
	public String toString() {
		if (children == null)
			return EMPTY;

		StringBuilder sB = new StringBuilder();
		for (Node child : children)
			sB.append(child);

		return sB.toString();
	}
}
