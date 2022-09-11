package gwt.xml.server.dom;

import gwt.xml.shared.XmlUtil;
import org.w3c.dom.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class ElementImpl extends ParentNode implements Element {
    private final String tagName;

    private Map<String, String> attributes;
    private int attributesRevision;

    private int childNodesRevision;

    public ElementImpl(Node owner, String tagName) {
        super(owner);

        this.tagName = tagName;
    }

    public int getAttributesRevision() {
        return attributesRevision;
    }

    public Map<String, String> getAttributesImpl() {
        return attributes == null ? Collections.emptyMap() : attributes;
    }

    @Override
    public int getChildNodesRevision() {
        return childNodesRevision;
    }

    @Override
    public void onChildNodesChanged(Node removed, Node appended) {
        Node parent = getParentNode();

        if (parent instanceof NodeImpl)
            ((NodeImpl) parent).onChildNodesChanged(null, null);

        childNodesRevision++;

        if (removed == null && appended == null)
            return;

        Document ownerDocument = getOwnerDocument();

        if (ownerDocument instanceof DocumentImpl) {
            Element element;

            if (removed instanceof Element && (element = (Element) removed).hasAttribute(ID))
                ((DocumentImpl) ownerDocument).updateIdMap(element.getAttribute(ID), null);

            if (appended instanceof Element && (element = (Element) appended).hasAttribute(ID))
                ((DocumentImpl) ownerDocument).updateIdMap(element.getAttribute(ID), element);
        }
    }

    @Override
    public void appendElementsByTagName(List<Node> holder, Predicate<String> predicate) {
        if (children == null)
            return;

        for (Node child : children) {
            if (child instanceof ElementImpl) {
                if (predicate.test(child.getNodeName()))
                    holder.add(child);

                ((ElementImpl) child).appendElementsByTagName(holder, predicate);
            }
        }
    }

    @Override
    public String getNodeName() {
        return tagName;
    }

    @Override
    public short getNodeType() {
        return ELEMENT_NODE;
    }

    @Override
    public String getTagName() {
        return getNodeName();
    }

    @Override
    public String getAttribute(String name) {
        return attributes == null ? EMPTY : nonNull(attributes.get(name));
    }

    @Override
    public void setAttribute(String name, String value) {
        if (attributes == null)
            attributes = new HashMap<>();

        value = nonNull(value);

        String oldValue;

        if (!value.equals((oldValue = attributes.put(name, value)))) {
            attributesRevision++;

            if (ID.equals(name) && getParentNode() != null) {
                Document ownerDocument = getOwnerDocument();

                if (ownerDocument instanceof DocumentImpl) {
                    ((DocumentImpl) ownerDocument).updateIdMap(oldValue, this);
                    ((DocumentImpl) ownerDocument).updateIdMap(value, this);
                }
            }
        }
    }

    @Override
    public void removeAttribute(String name) {
        String value;

        if (attributes != null && (value = attributes.remove(name)) != null) {
            if (attributes.isEmpty())
                attributes = null;

            attributesRevision++;

            if (ID.equals(name)) {
                Document ownerDocument = getOwnerDocument();

                if (ownerDocument instanceof DocumentImpl)
                    ((DocumentImpl) ownerDocument).updateIdMap(value, this);
            }
        }
    }

    @Override
    public Attr getAttributeNode(String name) {
        return attributes == null ? null : new AttrImpl(this, name, nonNull(attributes.get(name)));
    }

    @Override
    public boolean hasAttributes() {
        return attributes != null;
    }

    @Override
    public NodeList getElementsByTagName(String tagName) {
        return new NodeListImpl(this, tagName);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes != null && attributes.containsKey(name);
    }

    @Override
    public Node cloneNode(boolean deep) {
        ElementImpl clone = new ElementImpl(getOwnerDocument(), tagName);

        if (attributes != null) {
            clone.attributes = new HashMap<>(attributes.size());
            clone.attributes.putAll(attributes);
        }

        if (deep && children != null) {
            clone.children = new ArrayList<>(children.size());

            for (Node child : children) {
                clone.children.add(child.cloneNode(true));
            }
        }

        return clone;
    }

    @Override
    public Node getParentNode() {
        return owner instanceof Document && this != ((Document) owner).getDocumentElement() ? null : owner;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new NamedNodeMapImpl(this);
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();

        sB.append(_LESS_THAN).append(getNodeName());

        if (attributes != null)
            for (Entry<String, String> entry : attributes.entrySet())
                sB.append(_SPACE).append(XmlUtil.serializeToString(entry.getKey(), entry.getValue()));

        if (children == null)
            sB.append(_SLASH).append(_GREATER_THAN);
        else {
            sB.append(_GREATER_THAN);

            for (Node child : children)
                sB.append(child);

            sB.append(_LESS_THAN).append(_SLASH).append(getNodeName()).append(_GREATER_THAN);
        }

        return sB.toString();
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) {
        throw createUoException("setAttributeNode");
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) {
        throw createUoException("removeAttributeNode");
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) {
        throw createUoException("getAttributeNS");
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
        throw createUoException("setAttributeNS");
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) {
        throw createUoException("removeAttributeNS");
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) {
        throw createUoException("getAttributeNodeNS");
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) {
        throw createUoException("setAttributeNodeNS");
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw createUoException("getElementsByTagNameNS");
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) {
        throw createUoException("hasAttributeNS");
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        throw createUoException("getSchemaTypeInfo");
    }

    @Override
    public void setIdAttribute(String name, boolean isId) {
        throw createUoException("setIdAttribute");
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
        throw createUoException("setIdAttributeNS");
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) {
        throw createUoException("setIdAttributeNode");
    }
}
