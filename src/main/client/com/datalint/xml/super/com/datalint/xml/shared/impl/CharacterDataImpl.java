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
package com.datalint.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

/**
 * This class implements the CharacterData interface.
 */
abstract class CharacterDataImpl extends NodeImpl implements
    CharacterData {

  protected CharacterDataImpl(JavaScriptObject o) {
    super(o);
  }

  /**
   * This function delegates to the native method <code>appendData</code> in
   * XmlParserImpl.
   */
  public void appendData(String arg) {
    try {
      XmlParserImpl.appendData(this.getJsObject(), arg);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
    }
  }

  /**
   * This function delegates to the native method <code>deleteData</code> in
   * XmlParserImpl.
   */
  public void deleteData(int offset, int count) {
    try {
      XmlParserImpl.deleteData(this.getJsObject(), offset, count);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
    }
  }

  /**
   * This function delegates to the native method <code>getData</code> in
   * XmlParserImpl.
   */
  public String getData() {
    return XmlParserImpl.getData(this.getJsObject());
  }

  /**
   * This function delegates to the native method <code>getLength</code> in
   * XmlParserImpl.
   */
  public int getLength() {
    return XmlParserImpl.getLength(this.getJsObject());
  }

  /**
   * This function delegates to the native method <code>insertData</code> in
   * XmlParserImpl.
   */
  public void insertData(int offset, String arg) {
    try {
      XmlParserImpl.insertData(this.getJsObject(), offset, arg);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
    }
  }

  /**
   * This function delegates to the native method <code>replaceData</code> in
   * XmlParserImpl.
   */
  public void replaceData(int offset, int count, String arg) {
    try {
      XmlParserImpl.replaceData(this.getJsObject(), offset, count, arg);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
    }
  }

  /**
   * This function delegates to the native method <code>setData</code> in
   * XmlParserImpl.
   */
  public void setData(String data) {
    try {
      XmlParserImpl.setData(this.getJsObject(), data);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_MODIFICATION_ERR, e, this);
    }
  }

  /**
   * This function delegates to the native method <code>substringData</code>
   * in XmlParserImpl.
   */
  public String substringData(final int offset, final int count) {
    try {
      return XmlParserImpl.substringData(this.getJsObject(), offset, count);
    } catch (JavaScriptException e) {
      throw new DOMNodeException(DOMException.INVALID_ACCESS_ERR, e, this);
    }
  }
}
