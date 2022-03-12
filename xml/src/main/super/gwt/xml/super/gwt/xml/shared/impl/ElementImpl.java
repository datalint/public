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

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import gwt.xml.shared.ICommon;
import gwt.xml.shared.XmlUtil;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * This method implements the Element interface.
 */
class ElementImpl extends NodeImpl implements Element, ICommon {
    protected ElementImpl(JavaScriptObject o) {
        super(o);
    }

    /**
     * This function delegates to the native method <code>getAttribute</code> in
     * XmlParserImpl.
     */
    public String getAttribute(String tagName) {
        return nonNull(XmlParserImpl.getAttribute(this.getJsObject(), tagName));
    }

    /**
     * This function delegates to the native method <code>getAttributeNode</code>
     * in XmlParserImpl.
     */
    public Attr getAttributeNode(String tagName) {
        return (Attr) NodeImpl.build(XmlParserImpl.getAttributeNode(
                this.getJsObject(), tagName));
    }

    /**
     * This function delegates to the native method
     * <code>getElementsByTagName</code> in XmlParserImpl.
     */
    public NodeList getElementsByTagName(String tagName) {
        return new NodeListImpl(XmlParserImpl.getElementsByTagName(
                this.getJsObject(), tagName));
    }

    /**
     * This function delegates to the native method <code>getTagName</code> in
     * XmlParserImpl.
     */
    public String getTagName() {
        return XmlParserImpl.getTagName(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>hasAttribute</code> in
     * XmlParserImpl.
     */
    public boolean hasAttribute(String tagName) {
        return XmlParserImpl.hasAttribute(this.getJsObject(), tagName);
    }

    /**
     * This function delegates to the native method <code>removeAttribute</code>
     * in XmlParserImpl.
     */
    public void removeAttribute(String name) throws DOMNodeException {
        try {
            XmlParserImpl.removeAttribute(this.getJsObject(), name);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    /**
     * This function delegates to the native method <code>setAttribute</code> in
     * XmlParserImpl.
     */
    public void setAttribute(String name, String value) throws DOMNodeException {
        try {
            XmlParserImpl.setAttribute(this.getJsObject(), name, value);
        } catch (JavaScriptException e) {
            throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
        }
    }

    @Override
    public int hashCode() {
        return XmlUtil.getHashCode(this);
    }
}
