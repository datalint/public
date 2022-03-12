package gwt.xml.server.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public abstract class CharacterDataImpl extends NodeImpl implements CharacterData {
    protected StringBuilder builder;

    public CharacterDataImpl(Node owner) {
        this(owner, new StringBuilder());
    }

    public CharacterDataImpl(Node owner, StringBuilder builder) {
        super(owner);

        this.builder = builder;
    }

    @Override
    public String getNodeValue() {
        return builder.toString();
    }

    @Override
    public String getData() {
        return getNodeValue();
    }

    @Override
    public void setData(String data) {
        builder = new StringBuilder(data);
    }

    @Override
    public int getLength() {
        return builder.length();
    }

    @Override
    public String substringData(int offset, int count) throws DOMException {
        validate(offset, count);

        return builder.substring(offset, getEnd(offset, count));
    }

    @Override
    public void appendData(String data) {
        builder.append(data);
    }

    @Override
    public void insertData(int offset, String data) throws DOMException {
        validate(offset);

        builder.insert(offset, data);
    }

    @Override
    public void deleteData(int offset, int count) throws DOMException {
        validate(offset, count);

        builder.delete(offset, getEnd(offset, count));
    }

    @Override
    public void replaceData(int offset, int count, String data) throws DOMException {
        validate(offset, count);

        builder.replace(offset, getEnd(offset, count), data);
    }

    protected int getEnd(int offset, int count) {
        return Math.min(builder.length(), offset + count);
    }

    protected void validate(int offset) throws DOMException {
        if (offset < 0 || offset > builder.length())
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "validate failed with offset: [" + offset + ']');
    }

    protected void validate(int offset, int count) throws DOMException {
        validate(offset);

        if (count < 0)
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "validate failed with count: [" + count + ']');
    }
}
