package gwt.xml.server;

import gwt.xml.shared.XmlParser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XmlTestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("Hello, world (from xmlTestServlet)" + XmlParser.parse("<a/>"));
    }
}
