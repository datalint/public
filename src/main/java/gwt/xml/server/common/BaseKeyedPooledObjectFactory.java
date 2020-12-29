package gwt.xml.server.common;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public abstract class BaseKeyedPooledObjectFactory<K, V>
		extends org.apache.commons.pool2.BaseKeyedPooledObjectFactory<K, V> {
	@Override
	public PooledObject<V> wrap(V value) {
		return new DefaultPooledObject<>(value);
	}
}
