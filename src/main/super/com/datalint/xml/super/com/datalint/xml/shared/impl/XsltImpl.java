package com.datalint.xml.shared.impl;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

public abstract class XsltImpl {
	protected JavaScriptObject processor;
	protected JavaScriptObject document;

	public static XsltImpl createInstance() {
		return GWT.create(XsltImpl.class);
	}

	XmlUtilImpl xMLUtil() {
		return XmlUtilImpl.getInstance();
	}

	public void importStyleSheet(String styleSheet) {
		importStyleSheetImpl(styleSheet);
	}

	protected abstract void importStyleSheetImpl(String styleSheet);

	public void importSource(Document document) {
		this.document = xMLUtil().getJsObject(document);
	}

	public void importSource(String contents) {
		this.document = parseImpl(contents);
	}

	protected JavaScriptObject parseImpl(String contents) {
		return XmlParserImpl.getInstance().parseImpl(contents);
	}

	public void setParameter(String name, String value) {
		setParameterImpl(name, value);
	}

	protected abstract void setParameterImpl(String name, String value);

	public String transformToString() {
		return transformToStringImpl();
	}

	protected abstract String transformToStringImpl();

	public Document transformToDocument() {
		return (Document) xMLUtil().build(transformToDocumentImpl());
	}

	public DocumentFragment transformToFragment() {
		return (DocumentFragment) xMLUtil().build(transformToFragmentImpl());
	}

	protected abstract JavaScriptObject transformToDocumentImpl();

	protected abstract JavaScriptObject transformToFragmentImpl();
}
