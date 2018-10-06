package com.companyx.app;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerConfiguration {

	private static final int PORT = 8080;

	public void run() throws Exception {
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		final Server jettyServer = new Server(ServerConfiguration.PORT);
		jettyServer.setHandler(context);

		final ServletHolder jerseyServlet = context.addServlet(
				org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		final Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("jersey.config.server.provider.packages", "com.companyx.service");

		// Tells the Jersey Servlet which REST service/class to load.
		jerseyServlet.setInitParameters(parametersMap);

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}