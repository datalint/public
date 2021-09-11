package gwt.xml.server;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.saxonica.xqj.SaxonXQDataSource;
import gwt.xml.server.common.BasePooledObjectFactory;
import net.sf.saxon.Configuration;
import net.sf.saxon.dom.DocumentBuilderImpl;
import net.sf.saxon.lib.StandardModuleURIResolver;
import net.sf.saxon.trans.XPathException;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xquery.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class XQuery {
	private static final Map<URL, ObjectPool<XQPreparedExpression>> INSTANCE_MAP = new ConcurrentHashMap<>();
	private static final Map<String, ObjectPool<XQPreparedExpression>> INSTANCE_QUERY_MAP = new ConcurrentHashMap<>();

	private XQuery() {
	}

	public static void setConfiguration(DocumentBuilderImpl documentBuilderImpl) {
		documentBuilderImpl.setConfiguration(XQPreparedExpressionFactory.getConfiguration());
	}

	public static XQResultSequence process(Node contextNode, String xQuery) throws Exception {
		return process(Collections.singletonMap(XQConstants.CONTEXT_ITEM, contextNode), xQuery);
	}

	public static XQResultSequence process(Map<QName, ?> parameters, String xQuery) throws Exception {
		return process(parameters, getObjectPool(xQuery));
	}

	public static XQResultSequence process(Node contextNode, URL xQueryURL) throws Exception {
		return process(Collections.singletonMap(XQConstants.CONTEXT_ITEM, contextNode), xQueryURL);
	}

	public static XQResultSequence process(Map<QName, ?> parameters, URL xQueryURL) throws Exception {
		return process(parameters, getObjectPool(xQueryURL));
	}

	private static XQResultSequence process(Map<QName, ?> parameters, ObjectPool<XQPreparedExpression> objectPool)
			throws Exception, XQException {
		XQPreparedExpression preparedExpression = objectPool.borrowObject();

		try {
			for (Entry<QName, ?> entry : parameters.entrySet()) {
				preparedExpression.bindObject(entry.getKey(), entry.getValue(), null);
			}

			return preparedExpression.executeQuery();
		} finally {
			objectPool.returnObject(preparedExpression);
		}
	}

	private static ObjectPool<XQPreparedExpression> getObjectPool(String xQuery) {
		ObjectPool<XQPreparedExpression> objectPool = INSTANCE_QUERY_MAP.get(xQuery);

		if (objectPool == null) {
			synchronized (INSTANCE_QUERY_MAP) {
				if (!INSTANCE_QUERY_MAP.containsKey(xQuery)) {
					objectPool = new GenericObjectPool<>(new XQPreparedExpressionFactory(xQuery));

					INSTANCE_QUERY_MAP.put(xQuery, objectPool);
				}
			}
		}

		return objectPool;
	}

	private static ObjectPool<XQPreparedExpression> getObjectPool(URL xQueryURL) throws IOException {
		ObjectPool<XQPreparedExpression> objectPool = INSTANCE_MAP.get(xQueryURL);

		if (objectPool == null) {
			synchronized (INSTANCE_MAP) {
				if (!INSTANCE_MAP.containsKey(xQueryURL)) {
					objectPool = new GenericObjectPool<>(new XQPreparedExpressionFactory(xQueryURL));

					INSTANCE_MAP.put(xQueryURL, objectPool);
				}
			}
		}

		return objectPool;
	}

	private static class XQPreparedExpressionFactory extends BasePooledObjectFactory<XQPreparedExpression> {
		private static final XQDataSource XQ_DATA_SOURCE = new SaxonXQDataSource();

		private static final XQConnection CONNECTION = createConnection();

		private final String query;

		private XQPreparedExpressionFactory(String query) {
			this.query = query;
		}

		private XQPreparedExpressionFactory(URL queryURL) throws IOException {
			Reader reader = new InputStreamReader(queryURL.openStream());

			try {
				query = CharStreams.toString(reader);
			} finally {
				Closeables.closeQuietly(reader);
			}

		}

		private static XQConnection createConnection() {
			getConfiguration().setModuleURIResolver(new StandardModuleURIResolver() {
				@Override
				protected StreamSource getQuerySource(URI absoluteURI) throws XPathException {
					String path = absoluteURI.getPath();

					int index = path.lastIndexOf('/');
					if (index >= 0)
						path = path.substring(index + 1);

					try {
						return new StreamSource(XQuery.class.getClassLoader().getResource(path).openStream());
					} catch (Exception e) {
						// Ignore any exceptions generated due to the
						// resource try.
					}

					return super.getQuerySource(absoluteURI);
				}
			});

			try {
				return XQ_DATA_SOURCE.getConnection();
			} catch (XQException e) {
				// Ignore
			}

			return null;
		}

		private static Configuration getConfiguration() {
			return ((SaxonXQDataSource) XQ_DATA_SOURCE).getConfiguration();
		}

		@Override
		public XQPreparedExpression create() throws Exception {
			return CONNECTION.prepareExpression(query);
		}

		@Override
		public void passivateObject(PooledObject<XQPreparedExpression> wrapper) {
			// For the future, clear binded parameters preferred.
		}
	}
}
