package gwt.xml.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Logger;

public class XmlDemo implements EntryPoint {
	private boolean write;

	@Override
	public void onModuleLoad() {
		Logger logger = Logger.getLogger(XmlDemo.class.getName());

		logger.info("yes");

//		FocusPanel panel = new FocusPanel();
//		panel.getElement().getStyle().setBackgroundColor("blue");
//		panel.setSize("500px", "100px");
//
//		panel.addClickHandler(event -> write = !write);
//		panel.addMouseMoveHandler(event -> logger.info(write ? (event.getX() + "\t" + event.getY()) : ""));
//
//		RootPanel.get().add(panel);

		MouseMoveHandler mouseMoveHandler = event -> logger.info("move: " + event.getX() + "\t" + event.getY());
		ClickHandler clickHandler = event -> logger.info("click: " + event.getX() + "\t" + event.getY());
		Window.ScrollHandler scrollHandler = event -> logger.info("scroll: " + Window.getScrollLeft() + "\t" + Window.getScrollTop());

//		RootPanel.get("d1").addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
//		RootPanel.get("d2").addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
//		RootPanel.get("d3").addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
//		RootPanel.get("d4").addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
//
//		RootPanel.get("d1").addDomHandler(clickHandler, ClickEvent.getType());
//		RootPanel.get("d2").addDomHandler(clickHandler, ClickEvent.getType());
//		RootPanel.get("d3").addDomHandler(clickHandler, ClickEvent.getType());
//		RootPanel.get("d4").addDomHandler(clickHandler, ClickEvent.getType());

		MouseDownHandler mouseDownHandler = event -> logger.info("down: " + event.getX() + "\t" + event.getY());
		MouseUpHandler mouseUpHandler = event -> logger.info("up: " + event.getX() + "\t" + event.getY());
		MouseOutHandler mouseOutHandler = event -> logger.info("out: " + event.getX() + "\t" + event.getY());

		RootPanel.get("d1").addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
		RootPanel.get("d1").addDomHandler(mouseOutHandler, MouseOutEvent.getType());

//		RootPanel.get().addDomHandler(mouseMoveHandler, MouseMoveEvent.getType());
//		RootPanel.get().addDomHandler(mouseDownHandler, MouseDownEvent.getType());
//		RootPanel.get().addDomHandler(mouseUpHandler, MouseUpEvent.getType());
//		RootPanel.get().addDomHandler(mouseOutHandler, MouseOutEvent.getType());
		RootPanel.get().addDomHandler(clickHandler, ClickEvent.getType());
		Window.addWindowScrollHandler(scrollHandler);

//		Dummy dummy = new Dummy();
//
//		RootPanel.get().add(new Label("Hello, world!" + dummy + XPath.evaluateString(XmlParser.parse("<a name='1'/>"), "@name")));
	}
}
