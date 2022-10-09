/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwt.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class wraps the native Node object.
 */
class NodeImpl extends DOMItem implements Node {

    /**
     * creates a new NodeImpl from the supplied JavaScriptObject.
     *
     * @param jso - the DOM node JavaScriptObject
     */
    protected NodeImpl(JavaScriptObject jso) {
        super(jso);
    }

    /**
     * This method creates a new node of the correct type.
     *
     * @param node - the supplied DOM JavaScript object
     * @return a Node object that corresponds to the DOM object
     */
    static Node build(JavaScriptObject node) {
        if (node == null) {
            return null;
        }
        short nodeType = XmlParserImpl.getNodeType(node);
        switch (nodeType) {
            case Node.ATTRIBUTE_NODE:
                return new AttrImpl(node);
            case Node.CDATA_SECTION_NODE:
                return new CDATASectionImpl(node);
            case Node.COMMENT_NODE:
                return new CommentImpl(node);
            case Node.DOCUMENT_FRAGMENT_NODE:
                return new DocumentFragmentImpl(node);
            case Node.DOCUMENT_NODE:
                return new DocumentImpl(node);
            case Node.ELEMENT_NODE:
                return new ElementImpl(node);
            case Node.PROCESSING_INSTRUCTION_NODE:
                return new ProcessingInstructionImpl(node);
            case Node.TEXT_NODE:
                return new TextImpl(node);
            default:
                return new NodeImpl(node);
        }
    }

    /**
     * This function delegates to the native method <code>appendChild</code> in
     * XmlParserImpl.
     */
    public Node appendChild(Node newChild) {
        try {
            final JavaScriptObject newChildJs = ((DOMItem) newChild).getJsObject();
            final JavaScriptObject appendChildResults = XmlParserImpl.appendChild(
                    this.getJsObject(), newChildJs);
            return NodeImpl.build(appendChildResults);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    /**
     * This function delegates to the native method <code>cloneNode</code> in
     * XmlParserImpl.
     */
    public Node cloneNode(boolean deep) {
        return NodeImpl.build(XmlParserImpl.cloneNode(this.getJsObject(), deep));
    }

    public NamedNodeMap getAttributes() {
        return new NamedNodeMapImpl(XmlParserImpl.getAttributes(this.getJsObject()));
    }

    public NodeList getChildNodes() {
        return new NodeListImpl(XmlParserImpl.getChildNodes(this.getJsObject()));
    }

    public Node getFirstChild() {
        return getChildNodes().item(0);
    }

    public Node getLastChild() {
        return getChildNodes().item(getChildNodes().getLength() - 1);
    }

    /**
     * This function delegates to the native method <code>getNamespaceURI</code>
     * in XmlParserImpl.
     */
    public String getNamespaceURI() {
        return XmlParserImpl.getNamespaceURI(this.getJsObject());
    }

    public Node getNextSibling() {
        return NodeImpl.build(XmlParserImpl.getNextSibling(this.getJsObject()));
    }

    public String getNodeName() {
        return XmlParserImpl.getNodeName(this.getJsObject());
    }

    public short getNodeType() {
        return XmlParserImpl.getNodeType(this.getJsObject());
    }

    public String getNodeValue() {
        return XmlParserImpl.getNodeValue(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>setNodeValue</code> in
     * XmlParserImpl.
     */
    public void setNodeValue(String nodeValue) {
        try {
            XmlParserImpl.setNodeValue(this.getJsObject(), nodeValue);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    public Document getOwnerDocument() {
        return (Document) NodeImpl.build(XmlParserImpl.getOwnerDocument(this.getJsObject()));
    }

    public Node getParentNode() {
        return NodeImpl.build(XmlParserImpl.getParentNode(this.getJsObject()));
    }

    /**
     * This function delegates to the native method <code>getPrefix</code> in
     * XmlParserImpl.
     */
    public String getPrefix() {
        return XmlParserImpl.getPrefix(this.getJsObject());
    }

    public Node getPreviousSibling() {
        return NodeImpl.build(XmlParserImpl.getPreviousSibling(this.getJsObject()));
    }

    /**
     * This function delegates to the native method <code>hasAttributes</code>
     * in XmlParserImpl.
     */
    public boolean hasAttributes() {
        return XmlParserImpl.hasAttributes(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>hasChildNodes</code>
     * in XmlParserImpl.
     */
    public boolean hasChildNodes() {
        return XmlParserImpl.hasChildNodes(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>insertBefore</code> in
     * XmlParserImpl.
     */
    public Node insertBefore(Node newChild, Node refChild) {
        try {
            final JavaScriptObject newChildJs = ((DOMItem) newChild).getJsObject();
            final JavaScriptObject refChildJs;
            if (refChild != null) {
                refChildJs = ((DOMItem) refChild).getJsObject();
            } else {
                refChildJs = null;
            }
            final JavaScriptObject insertBeforeResults = XmlParserImpl.insertBefore(
                    this.getJsObject(), newChildJs, refChildJs);
            return NodeImpl.build(insertBeforeResults);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    /**
     * This function delegates to the native method <code>normalize</code> in
     * XmlParserImpl.
     */
    public void normalize() {
        XmlParserImpl.normalize(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>removeChild</code> in
     * XmlParserImpl.
     */
    public Node removeChild(Node oldChild) {
        try {
            final JavaScriptObject oldChildJs = ((DOMItem) oldChild).getJsObject();
            final JavaScriptObject removeChildResults = XmlParserImpl.removeChild(
                    this.getJsObject(), oldChildJs);
            return NodeImpl.build(removeChildResults);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    /**
     * This function delegates to the native method <code>replaceChild</code> in
     * XmlParserImpl.
     */
    public Node replaceChild(Node newChild, Node oldChild) {
        try {
            final JavaScriptObject newChildJs = ((DOMItem) newChild).getJsObject();
            final JavaScriptObject oldChildJs = ((DOMItem) oldChild).getJsObject();
            final JavaScriptObject replaceChildResults = XmlParserImpl.replaceChild(
                    this.getJsObject(), newChildJs, oldChildJs);
            return NodeImpl.build(replaceChildResults);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    @Override
    public String toString() {
        return XmlParserImpl.getInstance().toStringImpl(this);
    }

    @Override
    public native String getTextContent() /*-{
		var jsNode = this.@gwt.xml.shared.impl.DOMItem::getJsObject()();
		return jsNode.textContent;
	}-*/;

    @Override
    public native void setTextContent(String textContent) /*-{
		var jsNode = this.@gwt.xml.shared.impl.DOMItem::getJsObject()();
		jsNode.textContent = textContent;
	}-*/;

    @Override
    public native short compareDocumentPosition(Node other) /*-{
		var jsNode = this.@gwt.xml.shared.impl.DOMItem::getJsObject()();
		var jsNodeOther = other.@gwt.xml.shared.impl.DOMItem::getJsObject()();
		return jsNode.compareDocumentPosition(jsNodeOther);
	}-*/;

    private static native boolean hasIsEqualNode(Object object) /*-{
        return !!object && !!object.isEqualNode;
    }-*/;

    private static native boolean callIsEqualNode(Object thisObject, Object thatObject) /*-{
        return thisObject.isEqualNode(thatObject);
    }-*/;

    @Override
    public boolean isEqualNode(Node arg) {
        if (isSameNode(arg))
            return true;
        else if (hasIsEqualNode(this.getJsObject()) && arg instanceof DOMItem)
            return callIsEqualNode(this.getJsObject(), ((DOMItem) arg).getJsObject());

        return equals(arg);
    }

    @Override
    public boolean isSameNode(Node other) {
        return this == other;
    }
}
