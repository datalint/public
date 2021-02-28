package gwt.xml.server;

import gwt.xml.shared.XPath;
import gwt.xml.shared.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Tomcat {
	public static void main(String[] args) throws IOException, InterruptedException {
		String pathTomcat;
		String pathDocBase;
		String port = "8080";

		if (args.length < 2) {
			System.out.println("Please provide exactly two argument: ");
			System.out.println("The first one is tomcat home directory. For example: /home/user/apache-tomcat-9.0.30");
			System.out.println("The second one is the project web target directory. For example: /home/user/project/target/project-1.0-snapshot");

			return;
		}

		pathTomcat = args[0];

		pathDocBase = args[1];
		if (pathDocBase.charAt(0) != '/')
			pathDocBase = Path.of(pathDocBase).toAbsolutePath().toString();

		if (args.length > 2)
			port = args[2];

		System.out.println("Tomcat home: " + pathTomcat);
		System.out.println("Context docBase: " + pathDocBase);
		System.out.println("Host port: " + port);

		Path tomcat = Path.of(pathTomcat);

		Path server = tomcat.resolve("conf/server.xml");

		Path original = tomcat.resolve("conf/server.xml.original");
		if (!original.toFile().exists())
			Files.copy(server, original);

		Document serverDocument = XmlParser.parse(Files.readString(server));

		Element connector = XPath.evaluateNode(serverDocument, "Service/Connector[@protocol='HTTP/1.1']");
		connector.setAttribute("port", port);

		Element localhost = XPath.evaluateNode(serverDocument, "Service/Engine/Host[@name='localhost']");
		Element context = XPath.evaluateNode(localhost, "Context[@path='/' or @path='']");

		if (context == null) {
			context = serverDocument.createElement("Context");
			context.setAttribute("path", "");
			context.setAttribute("reloadable", Boolean.TRUE.toString());

			localhost.appendChild(context);
		} else if (context.getAttribute("path").equals("/")) {
			context.setAttribute("path", "");
		}

		context.setAttribute("docBase", pathDocBase);

		Files.writeString(server, serverDocument.toString());

		ProcessBuilder processBuilder = new ProcessBuilder(pathTomcat + "/bin/catalina.sh", "jpda", "run");
		ProcessHandle.current().info().command().ifPresent(
				cmd -> processBuilder.environment().putIfAbsent("JAVA_HOME", Path.of(cmd).getParent().getParent().toString())
		);
		processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		processBuilder.start().waitFor();
	}
}
