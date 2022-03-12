package gwt.xml.server.parser;

import gwt.xml.server.common.BasePooledObjectFactory;
import gwt.xml.shared.ICommon;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DocumentSerializer implements ICommon {
    private static DocumentSerializer instance = new DocumentSerializer();

    private DocumentSerializer() {
    }

    public static List<String> serialize(Collection<Node> nodes) throws Exception {
        List<String> result = new ArrayList<>(nodes.size());

        serialize(nodes, result);

        return result;
    }

    public static List<String> serializeSilent(Collection<Node> nodes) {
        try {
            return serialize(nodes);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> serialize(NodeList nodeList) throws Exception {
        return serialize(instance.asList(nodeList));
    }

    public static List<String> serializeSilent(NodeList nodeList) {
        return serializeSilent(instance.asList(nodeList));
    }

    public static void serialize(Iterable<Node> nodes, Collection<String> result) throws Exception {
        Transformer transformer = PooledTransformerFactory.instance.borrowObject();
        try {
            for (Node node : nodes) {
                result.add(serialize(node, transformer));
            }
        } finally {
            PooledTransformerFactory.instance.returnObject(transformer);
        }
    }

    public static void serializeSilent(Iterable<Node> nodes, Collection<String> result) {
        try {
            serialize(nodes, result);
        } catch (Exception e) {
        }
    }

    public static void serialize(NodeList nodeList, Collection<String> result) throws Exception {
        serialize(instance.asList(nodeList), result);
    }

    public static void serializeSilent(NodeList nodeList, Collection<String> result) {
        serializeSilent(instance.asList(nodeList), result);
    }

    public static String serialize(Node node) throws Exception {
        Transformer transformer = PooledTransformerFactory.instance.borrowObject();
        try {
            return serialize(node, transformer);
        } finally {
            PooledTransformerFactory.instance.returnObject(transformer);
        }
    }

    /*
     * Update on 2015-04-19. Node object is not thread safe, synchronization is
     * required on the owner document.
     */
    private static String serialize(Node node, Transformer transformer) throws Exception {
        Object lock = node.getOwnerDocument();

        if (lock == null)
            lock = node;

        synchronized (lock) {
            if (node instanceof Attr || node instanceof Text)
                return node.getNodeValue();

            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(node), new StreamResult(writer));

            return writer.toString();
        }
    }

    public static String serializeSilent(Node node) {
        try {
            return serialize(node);
        } catch (Exception e) {
            return null;
        }
    }

    private static class PooledTransformerFactory extends BasePooledObjectFactory<Transformer> {
        private static final Properties TRANSFORMER_PROPERTIES = new Properties();
        private static ObjectPool<Transformer> instance = new GenericObjectPool<>(new PooledTransformerFactory());

        static {
            TRANSFORMER_PROPERTIES.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        }

        @Override
        public Transformer create() throws Exception {
            return TransformerFactory.newDefaultInstance().newTransformer();
        }

        @Override
        public void activateObject(PooledObject<Transformer> wrapper) throws Exception {
            wrapper.getObject().reset();

            wrapper.getObject().setOutputProperties(TRANSFORMER_PROPERTIES);
        }
    }
}
