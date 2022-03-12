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
package gwt.xml.shared.impl;

import com.google.gwt.core.client.JavaScriptObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class implements the NodeList interface using the underlying
 * JavaScriptObject's implementation.
 */
class NodeListImpl extends DOMItem implements NodeList {

    protected NodeListImpl(JavaScriptObject o) {
        super(o);
    }

    public int getLength() {
        return XmlParserImpl.getLength(this.getJsObject());
    }

    /**
     * This method gets the index item.
     *
     * @param index - the index to be retrieved
     * @return the item at this index
     * @see org.w3c.dom.NodeList#item(int)
     */
    public Node item(int index) {
        return NodeImpl.build(XmlParserImpl.item(this.getJsObject(), index));
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            b.append(item(i).toString());
        }
        return b.toString();
    }
}
