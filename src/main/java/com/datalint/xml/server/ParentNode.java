package com.datalint.xml.server;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public abstract class ParentNode extends NodeImpl {
	protected List<Node> children;

	public ParentNode(Node owner) {
		super(owner);
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		if (children == null)
			children = new ArrayList<>();

		int index = children.indexOf(refChild);

		if (index < 0)
			children.add(newChild);
		else
			children.add(index, newChild);

		setOwner(newChild, this);

		return newChild;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
		if (children != null) {
			int index = children.indexOf(oldChild);

			if (index >= 0) {
				children.set(index, newChild);

				setOwner(newChild, this);
				setOwner(oldChild, getOwnerDocument());
			}
		}

		return oldChild;
	}

	@Override
	public Node removeChild(Node oldChild) {
		if (children != null && children.remove(oldChild)) {
			if (children.isEmpty())
				children = null;

			setOwner(oldChild, getOwnerDocument());
		}

		return oldChild;
	}

	@Override
	public Node appendChild(Node newChild) {
		if (children == null)
			children = new ArrayList<>();

		children.add(newChild);
		setOwner(newChild, this);

		return newChild;
	}

	@Override
	public boolean hasChildNodes() {
		return children != null;
	}
}
