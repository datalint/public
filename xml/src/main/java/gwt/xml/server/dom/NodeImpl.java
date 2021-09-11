package gwt.xml.server.dom;

import gwt.xml.shared.ICommon;
import gwt.xml.shared.XmlUtil;
import org.w3c.dom.*;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class NodeImpl implements Node, ICommon {
	protected Node owner;

	public NodeImpl(Node owner) {
		this.owner = owner;
	}

	public int getChildNodesRevision() {
		return 0;
	}

	public List<Node> getChildNodesImpl() {
		return Collections.emptyList();
	}

	public void onChildNodesChanged() {
	}

	public void appendElementsByTagName(List<Node> holder, Predicate<String> predicate) {
	}

	@Override
	public String getNodeValue() {
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) {
	}

	@Override
	public Node getParentNode() {
		return owner instanceof Document ? null : owner;
	}

	@Override
	public NodeList getChildNodes() {
		return NodeListImpl.empty();
	}

	@Override
	public Node getFirstChild() {
		return null;
	}

	@Override
	public Node getLastChild() {
		return null;
	}

	@Override
	public Node getPreviousSibling() {
		if (owner instanceof ElementImpl)
			return ((ElementImpl) owner).getPreviousSibling(this);

		return null;
	}

	@Override
	public Node getNextSibling() {
		if (owner instanceof ElementImpl)
			return ((ElementImpl) owner).getNextSibling(this);

		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public Document getOwnerDocument() {
		if (this instanceof Document)
			return null;
		else if (owner instanceof Document)
			return (Document) owner;

		return owner.getOwnerDocument();
	}

	protected void setOwner(Node node, Node owner) {
		assert node instanceof NodeImpl;

		((NodeImpl) node).owner = owner;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		return newChild;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
		return oldChild;
	}

	@Override
	public Node removeChild(Node oldChild) {
		return oldChild;
	}

	@Override
	public Node appendChild(Node newChild) {
		return newChild;
	}

	@Override
	public boolean hasChildNodes() {
		return false;
	}

	@Override
	public void normalize() {
	}

	@Override
	public String getNamespaceURI() {
		return null;
	}

	@Override
	public String getPrefix() {
		String name = getNodeName();

		if (name != null) {
			int index = name.indexOf(':');

			if (index >= 0)
				return name.substring(0, index);
		}

		return null;
	}

	@Override
	public void setPrefix(String prefix) {
		throw createUoException("setPrefix");
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String getLocalName() {
		return getNodeName();
	}

	@Override
	public boolean isSameNode(Node other) {
		return equals(other);
	}

	@Override
	public short compareDocumentPosition(Node other) {
		List<Element> ancestors = XmlUtil.getAncestors(this);
		List<Element> otherAncestors = XmlUtil.getAncestors(other);

		for (Element ancestor : ancestors) {
			if (otherAncestors.contains(ancestor)) {
				ElementImpl ancestorImpl = (ElementImpl) ancestor;

				int compare = Integer.compare(ancestorImpl.getChildIndex(this), ancestorImpl.getChildIndex(other));
				if (compare < 0)
					return DOCUMENT_POSITION_PRECEDING;
				else if (compare > 0)
					return DOCUMENT_POSITION_FOLLOWING;
				else
					return 0;
			}
		}

		return DOCUMENT_POSITION_DISCONNECTED;
	}

	protected Node getAncestor(Node node) {
		Node parent = node.getParentNode();

		while (parent != null) {
			node = node.getParentNode();
		}

		return null;
	}

	@Override
	public boolean isSupported(String feature, String version) {
		throw createUoException("isSupported");
	}

	@Override
	public String getBaseURI() {
		throw createUoException("getBaseURI");
	}

	@Override
	public String getTextContent() {
		throw createUoException("getTextContent");
	}

	@Override
	public void setTextContent(String textContent) {
		throw createUoException("textContent");
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		throw createUoException("namespaceURI");
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		throw createUoException("isDefaultNamespace");
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		throw createUoException("lookupNamespaceURI");
	}

	@Override
	public boolean isEqualNode(Node arg) {
		throw createUoException("isEqualNode");
	}

	@Override
	public Object getFeature(String feature, String version) {
		throw createUoException("getFeature");
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		throw createUoException("setUserData");
	}

	@Override
	public Object getUserData(String key) {
		throw createUoException("getUserData");
	}

	@Override
	public String toString() {
		return '[' + getNodeName() + ": " + getNodeValue() + ']';
	}
}
