package com.datalint.xml.server;

import com.datalint.xml.shared.XPath;
import com.datalint.xml.shared.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Tomcat {
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 2) {
			System.out.println("Please provide exactly two argument: ");
			System.out.println("The first one is tomcat home directory. For example: /home/user/apache-tomcat-9.0.30");
			System.out.println("The second one is the project web target directory. For example: /home/user/project/target/project-1.0-snapshot");

			System.exit(-1);
		} else {
			System.out.println("Tomcat home: " + args[0]);
			System.out.println("Context docBase: " + args[1]);
		}

		Path tomcat = Path.of(args[0]);

		Path server = tomcat.resolve("conf/server.xml");

		Path original = tomcat.resolve("conf/server.xml.original");
		if (!original.toFile().exists())
			Files.copy(server, original);

		Document serverDocument = XmlParser.parse(Files.readString(server));

		Element localhost = XPath.evaluateNode(serverDocument, "Service/Engine/Host[@name='localhost']");
		Element context = XPath.evaluateNode(localhost, "Context[@path='/']");

		if (context == null) {
			context = serverDocument.createElement("Context");
			context.setAttribute("path", "/");
			context.setAttribute("reloadable", Boolean.TRUE.toString());

			localhost.appendChild(context);
		}

		context.setAttribute("docBase", args[1]);

		Files.writeString(server, serverDocument.toString());

		ProcessBuilder processBuilder = new ProcessBuilder(args[0] + "/bin/catalina.sh", "jpda", "run");
		ProcessHandle.current().info().command().ifPresent(
				cmd -> processBuilder.environment().putIfAbsent("JAVA_HOME", Path.of(cmd).getParent().getParent().toString())
		);
		processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		processBuilder.start().waitFor();
	}
}
