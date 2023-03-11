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

import com.google.gwt.core.client.JavaScriptObject;
import org.w3c.dom.Attr;

/**
 * This class implements the XML Attr interface.
 */
class AttrImpl extends NodeImpl implements Attr {
    protected AttrImpl(JavaScriptObject o) {
        super(o);
    }

    /**
     * This function delegates to the native method <code>getName</code> in
     * XmlParserImpl.
     */
    public String getName() {
        return XmlParserImpl.getName(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>getSpecified</code> in
     * XmlParserImpl.
     */
    @Deprecated
    public boolean getSpecified() {
        return XmlParserImpl.getSpecified(this.getJsObject());
    }

    /**
     * This function delegates to the native method <code>getValue</code> in
     * XmlParserImpl.
     */
    public String getValue() {
        return XmlParserImpl.getValue(this.getJsObject());
    }
}
