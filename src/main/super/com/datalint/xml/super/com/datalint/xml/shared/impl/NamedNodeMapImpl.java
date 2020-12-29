/*
 * Copyright 2007 Google Inc.
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
package com.datalint.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class implements the NamedNodeMap interface.
 */
class NamedNodeMapImpl extends NodeListImpl implements NamedNodeMap {

	protected NamedNodeMapImpl(JavaScriptObject o) {
		super(o);
	}

	/**
	 * Gets the number of nodes in this object.
	 *
	 * @return the number of nodes in this object.
	 * @see com.datalint.xml.shared.impl.NodeListImpl#getLength()
	 */
	@Override
	public int getLength() {
		return super.getLength();
	}

	/**
	 * This method gets the item at the index position.
	 *
	 * @param name - the name of the item
	 * @return the item retrieved from the name
	 */
	public Node getNamedItem(String name) {
		return NodeImpl.build(XmlParserImpl.getNamedItem(this.getJsObject(), name));
	}

	@Override
	public Node item(int index) {
		return super.item(index);
	}

	/**
	 * This function delegates to the native method <code>removeNamedItem</code>
	 * in XmlParserImpl.
	 */
	public Node removeNamedItem(String name) {
		try {
			return NodeImpl.build(XmlParserImpl.removeNamedItem(this.getJsObject(),
					name));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method <code>setNamedItem</code> in
	 * XmlParserImpl.
	 */
	public Node setNamedItem(Node arg) {
		try {
			return NodeImpl.build(XmlParserImpl.setNamedItem(this.getJsObject(),
					((DOMItem) arg).getJsObject()));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
		}
	}
}
