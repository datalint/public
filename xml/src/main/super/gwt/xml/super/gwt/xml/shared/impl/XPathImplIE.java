package gwt.xml.shared.impl;

import org.w3c.dom.Element;

import gwt.xml.shared.Xslt;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.Messages;

class XPathImplIE extends XPathImpl {
    @Override
    protected native JavaScriptObject evaluateNodesImpl(JavaScriptObject element, String xPath) /*-{
		return element.selectNodes(xPath);
	}-*/;

    @Override
    protected native JsArray<JavaScriptObject> evaluateNodesImpl(JavaScriptObject nodes) /*-{
		return nodes;
	}-*/;

    @Override
    protected native JavaScriptObject evaluateNodeImpl(JavaScriptObject element, String xPath) /*-{
		// IE does not allow evaluateNode with a null xPath.
		return xPath == null ? null : element.selectSingleNode(xPath);
	}-*/;

    @Override
    protected native String evaluateStringImpl(JavaScriptObject element, String xPath) /*-{
		var result = element.selectSingleNode(xPath);

		return result == null ? "" : result.text;
	}-*/;

    @Override
    protected double evaluateNumberImpl(JavaScriptObject element, String xPath) {
        Xslt xslt = new Xslt();

        xslt.importStyleSheet(XPathImplIEMessages.INSTANCE.xsl(xPath));

        return parseFloat(xslt.transformToString((Element) XmlUtilImpl.getInstance().build(element)));
    }

    protected native double parseFloat(String value) /*-{
		return parseFloat(value);
	}-*/;

    @Override
    protected native int evaluatePosition(JavaScriptObject nodes, JavaScriptObject reference) /*-{
		for (var n = 0; n < nodes.length; n++) {
			if (nodes[n] == reference)
				return n + 1;
		}

		return 0;
	}-*/;

    interface XPathImplIEMessages extends Messages {
        static final XPathImplIEMessages INSTANCE = GWT.create(XPathImplIEMessages.class);

        @DefaultMessage("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
                + "<xsl:output method=\"text\"/>"
                + "<xsl:template match=\"*\"><xsl:value-of select=\"{0}\"/></xsl:template></xsl:stylesheet>")
        String xsl(String xPath);
    }
}
