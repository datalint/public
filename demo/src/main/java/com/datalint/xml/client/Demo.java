package com.datalint.xml.client;

import com.datalint.xml.shared.XmlParser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Demo implements EntryPoint {
	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new Label("Hello, world!" + XmlParser.parse("<a/>")));
	}
}
