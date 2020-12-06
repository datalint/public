package com.datalint.xml.shared.impl;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;

public class XsltImpl {
	private XsltImpl() {
	}

	public static XsltImpl createInstance() {
		return new XsltImpl();
	}

	public void importStyleSheet(String styleSheet) {
		// TODO
	}

	public void importSource(Document document) {
		// TODO
	}

	public void importSource(String contents) {
		// TODO
	}

	public void setParameter(String name, String value) {
		// TODO
	}

	public String transformToString() {
		// TODO
		return null;
	}

	public Document transformToDocument() {
		// TODO
		return null;
	}

	public DocumentFragment transformToFragment() {
		// TODO
		return null;
	}
}
