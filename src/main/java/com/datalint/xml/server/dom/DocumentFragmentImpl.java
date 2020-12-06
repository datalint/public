package com.datalint.xml.server.dom;

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
}
