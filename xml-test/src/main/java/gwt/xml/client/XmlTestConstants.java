package gwt.xml.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface XmlTestConstants extends Constants {
    XmlTestConstants INSTANCE = GWT.create(XmlTestConstants.class);

    String helloWorld();
}
