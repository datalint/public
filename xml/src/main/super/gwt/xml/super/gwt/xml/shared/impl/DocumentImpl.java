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
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * This class wraps the native Document object.
 */
class DocumentImpl extends NodeImpl implements Document {

	protected DocumentImpl(JavaScriptObject o) {
		super(o);
	}

	/**
	 * This function delegates to the native method
	 * <code>createCDATASection</code> in XmlParserImpl.
	 */
	public CDATASection createCDATASection(String data) {
		try {
			return (CDATASection) NodeImpl.build(XmlParserImpl.createCDATASection(
					this.getJsObject(), data));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method <code>createComment</code>
	 * in XmlParserImpl.
	 */
	public Comment createComment(String data) {
		try {
			return (Comment) NodeImpl.build(XmlParserImpl.createComment(
					this.getJsObject(), data));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method
	 * <code>createDocumentFragment</code> in XmlParserImpl.
	 */
	public DocumentFragment createDocumentFragment() {
		try {
			return (DocumentFragment) NodeImpl.build(XmlParserImpl.createDocumentFragment(this.getJsObject()));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method <code>createElement</code>
	 * in XmlParserImpl.
	 */
	public Element createElement(String tagName) {
		try {
			return (Element) NodeImpl.build(XmlParserImpl.createElement(
					this.getJsObject(), tagName));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method
	 * <code>createProcessingInstruction</code> in XmlParserImpl.
	 */
	public ProcessingInstruction createProcessingInstruction(String target,
															 String data) {
		try {
			return (ProcessingInstruction) NodeImpl.build(XmlParserImpl.createProcessingInstruction(
					this.getJsObject(), target, data));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method <code>createTextNode</code>
	 * in XmlParserImpl.
	 */
	public Text createTextNode(String data) {
		try {
			return (Text) NodeImpl.build(XmlParserImpl.createTextNode(
					this.getJsObject(), data));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method
	 * <code>getDocumentElement</code> in XmlParserImpl.
	 */
	public Element getDocumentElement() {
		return (Element) NodeImpl.build(XmlParserImpl.getDocumentElement(this.getJsObject()));
	}

	/**
	 * This function delegates to the native method <code>getElementById</code>
	 * in XmlParserImpl.
	 */
	public Element getElementById(String elementId) {
		return (Element) NodeImpl.build(XmlParserImpl.getElementById(
				this.getJsObject(), elementId));
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
	 * This function delegates to the native method <code>importNode</code> in
	 * XmlParserImpl.
	 */
	public Node importNode(Node importedNode, boolean deep) {
		try {
			return NodeImpl.build(XmlParserImpl.importNode(this.getJsObject(),
					((DOMItem) importedNode).getJsObject(), deep));
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_STATE_ERR, e, this);
		}
	}
}
