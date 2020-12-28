package com.datalint.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptObject;

class XsltImplStandard extends XsltImpl {
	@Override
	protected void importStyleSheetImpl(String styleSheet) {
		importStyleSheetImpl(parseImpl(styleSheet));
	}

	protected native void importStyleSheetImpl(JavaScriptObject styleSheet) /*-{
		this.@com.datalint.xml.shared.impl.XsltImpl::processor = new $wnd.XSLTProcessor();
		this.@com.datalint.xml.shared.impl.XsltImpl::processor
				.importStylesheet(styleSheet);
	}-*/;

	@Override
	protected native void setParameterImpl(String name, String value) /*-{
		this.@com.datalint.xml.shared.impl.XsltImpl::processor
				.setParameter(null, name, value);
	}-*/;

	@Override
	protected native String transformToStringImpl() /*-{
		return new XMLSerializer()
				.serializeToString(this.@com.datalint.xml.shared.impl.XsltImplStandard::transformToFragmentImpl()());
	}-*/;

	@Override
	protected native JavaScriptObject transformToDocumentImpl() /*-{
		return this.@com.datalint.xml.shared.impl.XsltImpl::processor
				.transformToDocument(this.@com.datalint.xml.shared.impl.XsltImpl::document);
	}-*/;

	@Override
	protected native JavaScriptObject transformToFragmentImpl() /*-{
		var document = this.@com.datalint.xml.shared.impl.XsltImpl::document;

		return this.@com.datalint.xml.shared.impl.XsltImpl::processor
				.transformToFragment(document, document);
	}-*/;
}
