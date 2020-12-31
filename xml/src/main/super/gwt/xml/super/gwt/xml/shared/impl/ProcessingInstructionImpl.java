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
import org.w3c.dom.ProcessingInstruction;

/**
 * This class implements the XML DOM ProcessingInstruction interface.
 */
class ProcessingInstructionImpl extends NodeImpl implements
		ProcessingInstruction {

	protected ProcessingInstructionImpl(JavaScriptObject o) {
		super(o);
	}

	/**
	 * This function delegates to the native method <code>getData</code> in
	 * XmlParserImpl.
	 */
	public String getData() {
		return XmlParserImpl.getData(this.getJsObject());
	}

	/**
	 * This function delegates to the native method <code>setData</code> in
	 * XmlParserImpl.
	 */
	public void setData(String data) {
		try {
			XmlParserImpl.setData(this.getJsObject(), data);
		} catch (JavaScriptException e) {
			throw new DOMNodeException(DOMException.INVALID_CHARACTER_ERR, e, this);
		}
	}

	/**
	 * This function delegates to the native method <code>getTarget</code> in
	 * XmlParserImpl.
	 */
	public String getTarget() {
		return XmlParserImpl.getTarget(this.getJsObject());
	}

	@Override
	public String toString() {
		return XmlParserImpl.getInstance().toStringImpl(this);
	}
}
