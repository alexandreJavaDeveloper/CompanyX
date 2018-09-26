package com.companyx.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.companyx.rest.MoneyTransferService;

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

		// Tells the Jersey Servlet which REST service/class to load.
		jerseyServlet.setInitParameter(
				"jersey.config.server.provider.classnames",
				MoneyTransferService.class.getCanonicalName());

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}