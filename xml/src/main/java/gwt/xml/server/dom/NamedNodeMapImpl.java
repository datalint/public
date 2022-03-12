package gwt.xml.server.dom;

import gwt.xml.shared.ICommon;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NamedNodeMapImpl implements NamedNodeMap, ICommon {
    private final ElementImpl owner;

    private int cacheRevision;
    private List<Node> cache;

    public NamedNodeMapImpl(ElementImpl owner) {
        this.owner = owner;
    }

    protected Map<String, String> getAttributes() {
        return owner.getAttributesImpl();
    }

    protected Attr createAttributeNode(String name, String value) {
        return new AttrImpl(owner.getOwnerDocument(), name, value);
    }

    @Override
    public Node item(int index) {
        int attributesRevision = owner.getAttributesRevision();

        if (cacheRevision != attributesRevision || cache == null) {
            cacheRevision = attributesRevision;

            Map<String, String> attributes = getAttributes();

            cache = new ArrayList<>(attributes.size());

            for (Entry<String, String> entry : attributes.entrySet()) {
                cache.add(createAttributeNode(entry.getKey(), entry.getValue()));
            }
        }

        return cache.get(index);
    }

    @Override
    public int getLength() {
        return getAttributes().size();
    }

    @Override
    public Node getNamedItem(String name) {
        String value = getAttributes().get(name);

        return value == null ? null : createAttributeNode(name, value);
    }

    @Override
    public Node setNamedItem(Node arg) throws DOMException {
        throw createUoException("setNamedItem");
    }

    @Override
    public Node removeNamedItem(String name) throws DOMException {
        throw createUoException("removeNamedItem");
    }

    @Override
    public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
        throw createUoException("getNamedItemNS");
    }

    @Override
    public Node setNamedItemNS(Node arg) throws DOMException {
        throw createUoException("setNamedItemNS");
    }

    @Override
    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
        throw createUoException("removeNamedItemNS");
    }
}
