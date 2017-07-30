

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.jfinal.core.Controller;

import service.HelloService;
import service.HelloServiceImpl;
import service.UserService;
import service.UserServiceImpl;

/**
 * IndexController
 */

public class JsonRPCController extends Controller {
	

	
	public void index() {
		System.out.println("begin**************");
		ObjectMapper  mapper = new ObjectMapper();
		try {
		JsonRpcServer  jsonRpcServer = new JsonRpcServer(mapper,new UserServiceImpl(), UserService.class);	
		jsonRpcServer.handleRequest(getRequest().getInputStream(), getResponse().getOutputStream());
		} catch (IOException e) {
		 renderJson("{\"code\":\"error\"}");
		}	
		renderNull();
	}
	
	
	public void composite() {
		System.out.println("composite");
		Object compositeService = ProxyUtil.createCompositeServiceProxy(
			    this.getClass().getClassLoader(),
			    new Object[] { new UserServiceImpl(), new HelloServiceImpl()},
			    new Class<?>[] { UserService.class, HelloService.class},
			    true);
		JsonRpcServer  jsonRpcServer = new JsonRpcServer(compositeService);
		try {
			jsonRpcServer.handleRequest(getRequest().getInputStream(), getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	
	public void hello(){
		renderText("hello world");
	}
	

	
	

}





