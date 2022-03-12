package gwt.xml.server.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class TextImpl extends CharacterDataImpl implements Text {
    public TextImpl(Node owner) {
        super(owner);
    }

    public TextImpl(Node owner, String data) {
        super(owner, new StringBuilder(data));
    }

    public TextImpl(Node owner, StringBuilder builder) {
        super(owner, builder);
    }

    @Override
    public String getNodeName() {
        return "#text";
    }

    @Override
    public short getNodeType() {
        return TEXT_NODE;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return createNode(getOwnerDocument(), new StringBuilder(builder));
    }

    @Override
    public Text splitText(int offset) throws DOMException {
        validate(offset);

        String data = builder.substring(offset, builder.length());

        builder.delete(offset, builder.length());

        Text text = createNode(owner, new StringBuilder(data));

        owner.insertBefore(text, getNextSibling());

        return text;
    }

    protected Text createNode(Node owner, StringBuilder builder) {
        return new TextImpl(owner, builder);
    }

    @Override
    public boolean isElementContentWhitespace() {
        throw createUoException("isElementContentWhitespace");
    }

    @Override
    public String getWholeText() {
        throw createUoException("getWholeText");
    }

    @Override
    public Text replaceWholeText(String content) {
        throw createUoException("replaceWholeText");
    }

    @Override
    public String toString() {
        return escapeContent(getNodeValue());
    }
}
