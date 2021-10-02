package gwt.xml.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface XmlDemoConstants extends Constants {
	XmlDemoConstants INSTANCE = GWT.create(XmlDemoConstants.class);

	String helloWorld();
}
