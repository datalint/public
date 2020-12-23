package com.datalint.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptObject;

class XsltImplIE extends XsltImpl {
	@Override
	protected native void importStyleSheetImpl(String styleSheet) /*-{
		var styleSheetDocument = new ActiveXObject(
				"MSXML2.FreeThreadedDOMDocument");
		styleSheetDocument.async = false;
		styleSheetDocument.loadXML(styleSheet);

		var processor = new ActiveXObject("Msxml2.XSLTemplate");
		processor.stylesheet = styleSheetDocument;

		this.@com.datalint.xml.shared.impl.XsltImpl::processor = processor
				.createProcessor();
	}-*/;

	@Override
	protected native void setParameterImpl(String name, String value) /*-{
		this.@com.datalint.xml.shared.impl.XsltImpl::processor
				.addParameter(name, value, "");
	}-*/;

	@Override
	protected native String transformToStringImpl() /*-{
		this.@com.datalint.xml.shared.impl.XsltImpl::processor.input = this.@com.datalint.xml.shared.impl.XsltImpl::document;

		this.@com.datalint.xml.shared.impl.XsltImpl::processor.transform();

		return this.@com.datalint.xml.shared.impl.XsltImpl::processor.output;
	}-*/;

	@Override
	protected JavaScriptObject transformToDocumentImpl() {
		return parseImpl(transformToStringImpl());
	}

	@Override
	protected JavaScriptObject transformToFragmentImpl() {
		JavaScriptObject document = transformToDocumentImpl();

		JavaScriptObject documentFragment = XmlParserImpl.createDocumentFragment(document);

		XmlParserImpl.appendChild(documentFragment, XmlParserImpl.getDocumentElement(document));

		return documentFragment;
	}
}
