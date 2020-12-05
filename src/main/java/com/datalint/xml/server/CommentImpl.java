package com.datalint.xml.server;

import org.w3c.dom.Comment;
import org.w3c.dom.Node;

public class CommentImpl extends CharacterDataImpl implements Comment {
	public CommentImpl(Node owner) {
		super(owner);
	}

	public CommentImpl(Node owner, String data) {
		super(owner, new StringBuilder(data));
	}

	public CommentImpl(Node owner, StringBuilder builder) {
		super(owner, builder);
	}

	@Override
	public String getNodeName() {
		return "#comment";
	}

	@Override
	public short getNodeType() {
		return COMMENT_NODE;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new CommentImpl(getOwnerDocument(), new StringBuilder(builder));
	}
}
