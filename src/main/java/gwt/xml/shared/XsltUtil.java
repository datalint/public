package gwt.xml.shared;

import gwt.xml.shared.xslt.AbstractSetting;
import gwt.xml.shared.xslt.MinMaxSetting;
import gwt.xml.shared.xslt.SortSetting;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;

public class XsltUtil {
	private static final HashMap<AbstractSetting, Xslt> cachedMap = new HashMap<AbstractSetting, Xslt>();

	private XsltUtil() {
	}

	private static Xslt getXSLT(AbstractSetting setting) {
		Xslt xslt = cachedMap.get(setting);

		if (xslt == null) {
			xslt = new Xslt();
			xslt.importStyleSheet(setting.styleSheet());

			cachedMap.put(setting, xslt);
		}

		return xslt;
	}

	public static void performSort(Element element, SortSetting setting) {
		element = XPath.evaluateNode(element, setting.getXPathParent());

		if (element == null)
			return;

		getXSLT(setting).performReplacement(element);
	}

	public static String getMinMax(Document document, MinMaxSetting setting) {
		Xslt xslt = getXSLT(setting);

		xslt.importSource(document);

		return xslt.transformToString();
	}

	public static String getMinMax(Element element, MinMaxSetting setting) {
		return getXSLT(setting).transformToString(element);
	}
}
