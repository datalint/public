package gwt.xml.shared;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static gwt.xml.shared.XPath.evaluateNode;
import static gwt.xml.shared.XPath.evaluateText;

public class XPathUtil {
    public static String getText(Element element, String xPath) {
        Node node = evaluateNode(element, xPath);

        if (node instanceof Element)
            return evaluateText((Element) node);

        return null;
    }

    private XPathUtil() {
    }
}
