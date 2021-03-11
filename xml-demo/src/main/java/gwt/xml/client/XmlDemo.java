package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlDemo implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Document document = XmlParser.createDocument();
		Element a = document.createElement("a");
		document.appendChild(a);

		a.setAttribute("b", "'\t\n\r\"");

		RootPanel.get().add(new Label(document.toString()));
	}
}
