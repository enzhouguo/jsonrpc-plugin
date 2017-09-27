package plugin.jsonrpc.server;

import static org.junit.Assert.*;

import org.junit.Test;

import plugin.jsonrpc.jfinal.JsonRpcServerPlugin;
import plugin.jsonrpc.rpc.JsonRpcHemaServer;
import plugin.jsonrpc.transport.UndertowTransportServer;
import service.HelloService;
import service.HelloServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class UndertowTransportServerTest {
	
	public static void main(String[] args) {
		JsonRpcHemaServer  hema = new JsonRpcHemaServer();		
		hema.addService("hello", new HelloServiceImpl(), HelloService.class);
		hema.addService("user", new UserServiceImpl(), UserService.class);
		
		//StockTransportServer streamServer =  new StockTransportServer(hema, 50,8080,InetAddress.getByName("0.0.0.0"));
		UndertowTransportServer transport = new UndertowTransportServer(hema);
		transport.start();
	}



}
