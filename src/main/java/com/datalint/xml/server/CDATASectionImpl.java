package com.datalint.xml.server;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class CDATASectionImpl extends TextImpl implements CDATASection {
	public CDATASectionImpl(Node owner) {
		super(owner);
	}

	public CDATASectionImpl(Node owner, String data) {
		super(owner, new StringBuilder(data));
	}

	public CDATASectionImpl(Node owner, StringBuilder builder) {
		super(owner, builder);
	}

	@Override
	public String getNodeName() {
		return "#cdata-section";
	}

	@Override
	public short getNodeType() {
		return CDATA_SECTION_NODE;
	}

	@Override
	protected Text createNode(Node owner, StringBuilder builder) {
		return new CDATASectionImpl(owner, builder);
	}
}
