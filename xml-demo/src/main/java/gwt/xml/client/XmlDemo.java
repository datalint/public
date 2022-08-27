package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.gwtproject.i18n.client.DateTimeFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Date;

public class XmlDemo implements EntryPoint {
    @Override
    public void onModuleLoad() {
        DomGlobal.document.body.appendChild(DomGlobal.document.createTextNode(XmlDemoConstants.INSTANCE.helloWorld()
                + "\t" + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_FULL).format(new Date())));

        Document parse = XmlParser.parse("<root><a/><b/></root>");

        Node a = XPath.evaluateNode(parse, "a");
        Node b = XPath.evaluateNode(parse, "b");

        DomGlobal.console.log(String.valueOf(a));
        DomGlobal.console.log(String.valueOf(b));
        DomGlobal.console.log(String.valueOf(a.compareDocumentPosition(b)));
        DomGlobal.console.log(String.valueOf(b.compareDocumentPosition(a)));
        DomGlobal.console.log(String.valueOf(b.compareDocumentPosition(parse)));
        DomGlobal.console.log(String.valueOf(parse.getDocumentElement().compareDocumentPosition(parse)));
    }

//	public void showHistoryBug() {
//		DomGlobal.window.addEventListener("popstate", event -> DomGlobal.console.log(String.valueOf(((PopStateEvent) event).state)));
//
//		Element button = DomGlobal.document.createElement("button");
//		button.textContent = "Click here to create a state";
//		button.addEventListener("click", event -> DomGlobal.history.pushState("state: " + JsDate.now(), "title"));
//
//		DomGlobal.document.body.appendChild(button);
//	}
//
//	public void testDocument() {
//		Document document = XmlParser.createDocument();
//		Element a = document.createElement("a");
//		document.appendChild(a);
//
//		a.setAttribute("b", "'\t\n\r\"");
//
//		RootPanel.get().add(new Label(document.toString()));
//	}
}
