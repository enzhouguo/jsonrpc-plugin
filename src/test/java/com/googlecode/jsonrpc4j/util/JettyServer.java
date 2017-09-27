package com.googlecode.jsonrpc4j.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.googlecode.jsonrpc4j.AnnotationsErrorResolver;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.util.JettyServer.JsonRpcTestServlet;

public class JettyServer implements AutoCloseable {

	public static final String SERVLET = "someSunnyServlet";
	private static final String PROTOCOL = "http";
	private static final String DEFAULT_LOCAL_HOSTNAME = "127.0.0.1";

	private final Class<?> service;
	private Server jetty;
	private int port;

	JettyServer(Class<?> service) {
		this.service = service;
	}

	public String getCustomServerUrlString(final String servletName) {
		return PROTOCOL + "://" + DEFAULT_LOCAL_HOSTNAME + ":" + port + "/" + servletName;
	}

	public void startup() throws Exception {
		port = 10000 + new Random().nextInt(30000);
		jetty = new Server(port);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");
		jetty.setHandler(context);
		ServletHolder servlet = context.addServlet(JsonRpcTestServlet.class, "/" + SERVLET);
		servlet.setInitParameter("class", service.getCanonicalName());
		jetty.start();
	}

	@Override
	public void close() throws Exception {
		this.stop();
	}

	public void stop() throws Exception {
		jetty.stop();
	}

	public class JsonRpcTestServlet extends HttpServlet {

		static final long serialVersionUID = 1L;
		private transient JsonRpcServer jsonRpcServer;

		@Override
		public void init() {
			try {
				final Class<?> aClass = Class.forName(getInitParameter("class"));
				final Object instance = aClass.getConstructor().newInstance();
				jsonRpcServer = new JsonRpcServer(instance);
				jsonRpcServer.setErrorResolver(AnnotationsErrorResolver.INSTANCE);
			} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException
					| IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			jsonRpcServer.handle(request, response);
		}
	}

}
