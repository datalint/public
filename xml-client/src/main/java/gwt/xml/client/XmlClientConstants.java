package gwt.xml.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface XmlClientConstants extends Constants {
    XmlClientConstants INSTANCE = GWT.create(XmlClientConstants.class);

    String helloWorld();
}
