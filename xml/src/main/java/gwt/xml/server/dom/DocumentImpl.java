package gwt.xml.server.dom;

import org.w3c.dom.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DocumentImpl extends NodeImpl implements Document {
    public static final String KEY_id = "id";

    private Map<String, Element> idMap;

    private ElementImpl element;

    private int childNodesRevision;

    public DocumentImpl() {
        super(null);
    }

    void updateIdMap(String oldValue, Element element) {
        if (idMap == null)
            return;

        if (oldValue != null)
            idMap.remove(oldValue);

        if (element != null)
            idMap.put(element.getAttribute(ID), element);
    }

    @Override
    public int getChildNodesRevision() {
        return childNodesRevision;
    }

    @Override
    public List<Node> getChildNodesImpl() {
        return element == null ? Collections.emptyList() : Collections.singletonList(element);
    }

    @Override
    public void onChildNodesChanged(Node removed, Node appended) {
        childNodesRevision++;
    }

    @Override
    public void appendElementsByTagName(List<Node> holder, Predicate<String> predicate) {
        if (element == null)
            return;

        if (predicate.test(element.getNodeName()))
            holder.add(element);

        element.appendElementsByTagName(holder, predicate);
    }

    @Override
    public String getNodeName() {
        return "#document";
    }

    @Override
    public short getNodeType() {
        return DOCUMENT_NODE;
    }

    @Override
    public Node cloneNode(boolean deep) {
        DocumentImpl document = new DocumentImpl();

        if (deep && element != null)
            document.element = (ElementImpl) element.cloneNode(true);

        return document;
    }

    @Override
    public Element getDocumentElement() {
        return element;
    }

    @Override
    public NodeList getChildNodes() {
        return new NodeListImpl(this);
    }

    @Override
    public Node getFirstChild() {
        return element;
    }

    @Override
    public Node getLastChild() {
        return element;
    }

    @Override
    public Element createElement(String tagName) {
        return new ElementImpl(this, tagName);
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        return new DocumentFragmentImpl(this);
    }

    @Override
    public Text createTextNode(String data) {
        return new TextImpl(this, data);
    }

    @Override
    public Comment createComment(String data) {
        return new CommentImpl(this, data);
    }

    @Override
    public CDATASection createCDATASection(String data) {
        return new CDATASectionImpl(this, data);
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) {
        return new ProcessingInstructionImpl(this, target, data);
    }

    @Override
    public Element getElementById(String elementId) {
        if (idMap == null) {
            idMap = new HashMap<>();

            NodeList nodeList = getElementsByTagName(WILDCARD);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                if (element.hasAttribute(KEY_id))
                    idMap.put(element.getAttribute(KEY_id), element);
            }
        }

        return idMap.get(elementId);
    }

    @Override
    public NodeList getElementsByTagName(String tagName) {
        return new NodeListImpl(this, tagName);
    }

    @Override
    public Node importNode(Node importedNode, boolean deep) {
        Node clonedNode = importedNode.cloneNode(deep);

        NodeImpl.setOwner(clonedNode, this);

        return clonedNode;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        element = (ElementImpl) newChild;

        onChildNodesChanged(null, newChild);

        return newChild;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) {
        element = (ElementImpl) newChild;

        onChildNodesChanged(oldChild, newChild);

        return oldChild;
    }

    @Override
    public Node removeChild(Node oldChild) {
        onChildNodesChanged(oldChild, null);

        return oldChild;
    }

    @Override
    public Node appendChild(Node newChild) {
        element = (ElementImpl) newChild;

        onChildNodesChanged(null, newChild);

        return newChild;
    }

    @Override
    public boolean hasChildNodes() {
        return element != null;
    }

    @Override
    public String getXmlEncoding() {
        return null;
    }

    @Override
    public boolean getXmlStandalone() {
        return false;
    }

    @Override
    public void setXmlStandalone(boolean xmlStandalone) {
    }

    @Override
    public String getXmlVersion() {
        return null;
    }

    @Override
    public void setXmlVersion(String xmlVersion) {
    }

    @Override
    public DocumentType getDoctype() {
        throw createUoException("getDoctype");
    }

    @Override
    public DOMImplementation getImplementation() {
        throw createUoException("getImplementation");
    }

    @Override
    public Attr createAttribute(String name) {
        throw createUoException("createAttribute");
    }

    @Override
    public EntityReference createEntityReference(String name) {
        throw createUoException("createEntityReference");
    }

    @Override
    public Element createElementNS(String namespaceURI, String qualifiedName) {
        throw createUoException("createElementNS");
    }

    @Override
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
        throw createUoException("createAttributeNS");
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw createUoException("getElementsByTagNameNS");
    }

    @Override
    public String getInputEncoding() {
        throw createUoException("getInputEncoding");
    }

    @Override
    public boolean getStrictErrorChecking() {
        throw createUoException("getStrictErrorChecking");
    }

    @Override
    public void setStrictErrorChecking(boolean strictErrorChecking) {
        throw createUoException("setStrictErrorChecking");
    }

    @Override
    public String getDocumentURI() {
        throw createUoException("getDocumentURI");
    }

    @Override
    public void setDocumentURI(String documentURI) {
        throw createUoException("setDocumentURI");
    }

    @Override
    public Node adoptNode(Node source) {
        throw createUoException("adoptNode");
    }

    @Override
    public DOMConfiguration getDomConfig() {
        throw createUoException("getDomConfig");
    }

    @Override
    public void normalizeDocument() {
        throw createUoException("normalizeDocument");
    }

    @Override
    public Node renameNode(Node n, String namespaceURI, String qualifiedName) {
        throw createUoException("renameNode");
    }

    @Override
    public String toString() {
        return element == null ? EMPTY : element.toString();
    }
}
