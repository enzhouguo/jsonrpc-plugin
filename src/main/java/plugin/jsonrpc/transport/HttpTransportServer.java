package plugin.jsonrpc.transport;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.googlecode.jsonrpc4j.AnnotationsErrorResolver;
import com.googlecode.jsonrpc4j.JsonRpcServer;

import plugin.jsonrpc.rpc.JsonRpcHemaServer;

public class HttpTransportServer implements AutoCloseable, TransportServer {

	public static final String SERVLET = "jsonrpc";
	private  JsonRpcServer jsonRpcServer;
	private Server jetty;
	private int port;


	public HttpTransportServer(int port) {		
		this.port = port;
	}

	public void start() {
		try {
			jetty = new Server(port);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			context.setContextPath("/");
			jetty.setHandler(context);
			ServletHolder servlet = context.addServlet(JsonRpcTestServlet.class, "/" + SERVLET);
			//servlet.setInitParameter("class", service.getCanonicalName());
			jetty.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void stop() {
		try {
			jetty.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class JsonRpcTestServlet extends HttpServlet {

		static final long serialVersionUID = 1L;
		//private transient JsonRpcServer jsonRpcServer;
		
//		@Override
//		public void init() {
//			try {
//				final Class<?> aClass = Class.forName(getInitParameter("class"));
//				final Object instance = aClass.getConstructor().newInstance();
//				jsonRpcServer = new JsonRpcServer(instance);
//				jsonRpcServer.setErrorResolver(AnnotationsErrorResolver.INSTANCE);
//			} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}

		@Override
		protected void service(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {			
			//response.getWriter().println("aaaa");
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/json;charset=utf-8");
			System.out.println("***");
			jsonRpcServer.handleRequest(request.getInputStream(), response.getOutputStream());
		}
	}

	@Override
	public void setJsonRpc(JsonRpcServer jsonRpcServer) {
		this.jsonRpcServer = jsonRpcServer;
		
	}

	@Override
	public void close() throws Exception {
		this.stop();
		
	}


}
