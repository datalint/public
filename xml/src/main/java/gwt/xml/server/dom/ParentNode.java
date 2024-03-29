package gwt.xml.server.dom;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ParentNode extends NodeImpl {
    protected List<Node> children;

    public ParentNode(Node owner) {
        super(owner);
    }

    public int getChildIndex(Node child) {
        return children == null ? -1 : children.indexOf(child);
    }

    @Override
    public List<Node> getChildNodesImpl() {
        return children == null ? Collections.emptyList() : children;
    }

    protected void preAppendChild(Node child) {
        if (child.getParentNode() != null)
            child.getParentNode().removeChild(child);

        children.remove(child);
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        if (refChild == null)
            return appendChild(newChild);

        preAppendChild(newChild);

        children.add(children.indexOf(refChild), newChild);

        setOwner(newChild, this);

        onChildNodesChanged(null, newChild);

        return newChild;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) {
        preAppendChild(newChild);

        children.set(children.indexOf(oldChild), newChild);

        setOwner(newChild, this);
        setOwner(oldChild, getOwnerDocument());

        onChildNodesChanged(oldChild, newChild);

        return oldChild;
    }

    @Override
    public Node removeChild(Node oldChild) {
        children.remove(oldChild);

        if (children.isEmpty())
            children = null;

        setOwner(oldChild, getOwnerDocument());

        onChildNodesChanged(oldChild, null);

        return oldChild;
    }

    @Override
    public Node appendChild(Node newChild) {
        if (children == null)
            children = new ArrayList<>();

        preAppendChild(newChild);

        children.add(newChild);
        setOwner(newChild, this);

        onChildNodesChanged(null, newChild);

        return newChild;
    }

    @Override
    public boolean hasChildNodes() {
        return children != null;
    }

    @Override
    public String getTextContent() {
        if (children == null)
            return EMPTY;

        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < children.size(); i++) {
            String textContent = children.get(i).getTextContent();

            if (textContent != null)
                sB.append(textContent);
        }

        return sB.toString();
    }

    @Override
    public void setTextContent(String textContent) {
        children = new ArrayList<>();

        children.add(getOwnerDocument().createTextNode(textContent));
    }

    @Override
    public NodeList getChildNodes() {
        return new NodeListImpl(this);
    }

    @Override
    public Node getFirstChild() {
        return children == null ? null : children.get(0);
    }

    @Override
    public Node getLastChild() {
        return children == null ? null : children.get(children.size() - 1);
    }

    public Node getPreviousSibling(Node refChild) {
        int index = getChildIndex(refChild);

        if (index > 0)
            return children.get(index - 1);

        return null;
    }

    public Node getNextSibling(Node refChild) {
        int index = getChildIndex(refChild);

        if (index >= 0 && index < children.size() - 1)
            return children.get(index + 1);

        return null;
    }
}
