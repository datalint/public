package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import org.gwtproject.i18n.client.DateTimeFormat;

import java.util.Date;

public class XmlDemo implements EntryPoint {
    @Override
    public void onModuleLoad() {
        DomGlobal.document.body.appendChild(DomGlobal.document.createTextNode(XmlDemoConstants.INSTANCE.helloWorld()
                + "\t" + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_FULL).format(new Date())));
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
