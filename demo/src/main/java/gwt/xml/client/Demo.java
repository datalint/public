package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;

public class Demo implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Dummy dummy = new Dummy();

		RootPanel.get().add(new Label("Hello, world!" + dummy + XPath.evaluateString(XmlParser.parse("<a name='1'/>"), "@name")));
	}
}
