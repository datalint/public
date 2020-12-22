package com.datalint.xml.server.dom;

import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;

public class EntityReferenceImpl extends NodeImpl implements EntityReference {
	private final String name;

	public EntityReferenceImpl(Node owner, String name) {
		super(owner);

		this.name = name;
	}

	@Override
	public String getNodeName() {
		return name;
	}

	@Override
	public short getNodeType() {
		return ENTITY_REFERENCE_NODE;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new EntityReferenceImpl(getOwnerDocument(), name);
	}
}
