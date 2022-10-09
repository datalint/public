package gwt.xml.server.dom;

import gwt.xml.shared.ICommon;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class NodeListImpl implements NodeList, ICommon {
    private final static NodeListImpl EMPTY = new NodeListImpl();
    private final static Predicate<String> TAG_ALL = name -> true;

    private final NodeImpl owner;
    private final String tagName;

    private int cacheRevision;
    private List<Node> cache;

    public NodeListImpl(NodeImpl owner, String tagName) {
        this.owner = owner;
        this.tagName = tagName;
    }

    public NodeListImpl(NodeImpl owner) {
        this(owner, null);
    }

    private NodeListImpl() {
        this(null);
    }

    public static NodeListImpl empty() {
        return EMPTY;
    }

    protected List<Node> getNodes() {
        if (owner == null)
            return Collections.emptyList();
        else if (tagName == null)
            return owner.getChildNodesImpl();

        int childNodesRevision = owner.getChildNodesRevision();

        if (cacheRevision != childNodesRevision || cache == null) {
            cacheRevision = childNodesRevision;

            cache = new ArrayList<>();
            owner.appendElementsByTagName(cache, tagName.equals(WILDCARD) ? TAG_ALL : name -> name.equals(tagName));
        }

        return cache;
    }

    @Override
    public Node item(int index) {
        return getNodes().get(index);
    }

    @Override
    public int getLength() {
        return getNodes().size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof NodeList) {
            NodeList nodeList = (NodeList) obj;

            int length = getLength();
            if (length == nodeList.getLength()) {
                for (int i = 0; i < length; i++) {
                    if (!item(i).isEqualNode(nodeList.item(i)))
                        return false;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getNodes().hashCode();
    }
}
