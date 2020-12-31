package gwt.xml.shared.impl;

import gwt.xml.server.common.BaseKeyedPooledObjectFactory;
import gwt.xml.shared.ICommon;
import gwt.xml.shared.common.CollectionMode;
import gwt.xml.shared.common.TriConsumer;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XPathImpl implements ICommon {
	private static final GenericKeyedObjectPool<String, XPathExpression> XPATH_EXPRESSION_POOL = new GenericKeyedObjectPool<>(
			new XPathExpressionFactory());
	private static final XPathImpl impl = new XPathImpl();

	private XPathImpl() {
	}

	public static XPathImpl getInstance() {
		return impl;
	}

	public String[] evaluateAttrValues(Element element, String xPath) {
		NodeList nodeList = evaluateNodeList(element, xPath);

		int length = nodeList.getLength();

		String[] attrValues = new String[length];

		for (int i = 0; i < length; i++) {
			attrValues[i] = nodeList.item(i).getNodeValue();
		}

		return attrValues;
	}

	public Set<String> evaluateAttrValues(Element element, String xPath, CollectionMode mode) {
		return evaluateNodeValues(element, xPath, mode);
	}

	public Set<String> evaluateTextValues(Element element, String xPath, CollectionMode mode) {
		return evaluateNodeValues(element, xPath, mode);
	}

	private Set<String> evaluateNodeValues(Element element, String xPath, CollectionMode mode) {
		NodeList nodeList = evaluateNodeList(element, xPath);

		int length = nodeList.getLength();

		Set<String> nodeValues = mode.createSet(length);

		appendNodeValues(nodeValues, nodeList, length);

		return nodeValues;
	}

	private void appendNodeValues(Collection<String> nodeValues, NodeList nodeList, int length) {
		for (int i = 0; i < length; i++) {
			nodeValues.add(nodeList.item(i).getNodeValue());
		}
	}

	public Set<Element> evaluateElements(Element element, String xPath, CollectionMode mode) {
		NodeList nodeList = evaluateNodeList(element, xPath);

		int length = nodeList.getLength();
		Set<Element> elements = mode.createSet(length);
		for (int i = 0; i < length; i++) {
			elements.add((Element) nodeList.item(i));
		}

		return elements;
	}

	public <T> Map<String, T> evaluateElementsMap(Element element, String xPath, String attributeName,
												  CollectionMode mode, TriConsumer<Map<String, T>, String, Element> consumer) {
		NodeList nodeList = evaluateNodeList(element, xPath);

		int length = nodeList.getLength();
		Map<String, T> elementsMap = mode.createMap(length);

		for (int i = 0; i < length; i++) {
			Element item = (Element) nodeList.item(i);

			consumer.accept(elementsMap, item.getAttribute(attributeName), item);
		}

		return elementsMap;
	}

	public List<Node> evaluateNodes(Element element, String xPath) {
		return iAsList(evaluateNodeList(element, xPath));
	}

	public NodeList evaluateNodeList(Element element, String xPath) {
		return (NodeList) evaluate(element, xPath, XPathConstants.NODESET);
	}

	public Node evaluateNode(Element element, String xPath) {
		return (Node) evaluate(element, xPath, XPathConstants.NODE);
	}

	public String evaluateString(Element element, String xPath) {
		return (String) evaluate(element, xPath, XPathConstants.STRING);
	}

	public double evaluateNumber(Element element, String xPath) {
		return (Double) evaluate(element, xPath, XPathConstants.NUMBER);
	}

	public int evaluatePosition(Element element, String xPath, Element reference) {
		NodeList nodeList = evaluateNodeList(element, xPath);

		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			if (nodeList.item(i) == reference)
				return i + 1;
		}

		return 0;
	}

	private Object evaluate(Element context, String xPath, QName qName) {
		try {
			XPathExpression xPathExpression = XPATH_EXPRESSION_POOL.borrowObject(xPath);

			try {
				return xPathExpression.evaluate(context, qName);
			} finally {
				XPATH_EXPRESSION_POOL.returnObject(xPath, xPathExpression);
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static class XPathExpressionFactory extends BaseKeyedPooledObjectFactory<String, XPathExpression> {
		// Update on 2015-06-07, XPathFactoryImpl is thread-safe.
		private static final XPathFactory xPathFactory = new XPathFactoryImpl();

		@Override
		public XPathExpression create(String key) throws Exception {
			return xPathFactory.newXPath().compile(key);
		}
	}
}
