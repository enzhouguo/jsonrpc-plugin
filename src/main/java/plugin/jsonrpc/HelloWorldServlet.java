package plugin.jsonrpc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;

import service.UserService;
import service.UserServiceImpl;

public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 3638336826344504848L;

    private JsonRpcServer rpcService = null;

    @Override

    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        ObjectMapper  mapper = new ObjectMapper();
        rpcService = new JsonRpcServer(mapper,new UserServiceImpl(), UserService.class);
        System.out.println("****");

    }

    @Override

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.println("hellowordl Servlet");  
    	if(req.getParameter("var").isEmpty()){
        rpcService.handleRequest(req.getInputStream(), resp.getOutputStream());
    	} else {
    		resp.getWriter().print("hello world");
    		resp.getWriter().close();
    	}

    }

}