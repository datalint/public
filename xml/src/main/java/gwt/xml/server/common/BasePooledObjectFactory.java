package gwt.xml.server.common;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public abstract class BasePooledObjectFactory<T> extends org.apache.commons.pool2.BasePooledObjectFactory<T> {
    @Override
    public PooledObject<T> wrap(T obj) {
        return new DefaultPooledObject<>(obj);
    }
}
