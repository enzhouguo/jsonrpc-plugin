

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import plugin.jsonrpc.rpc.JsonRpcHemaServer;
import plugin.jsonrpc.transport.HttpTransportServer;
import plugin.jsonrpc.transport.UndertowTransportServer;
import service.HelloService;
import service.HelloServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class HttpTransprotServerTest {

	public static void main(String[] args) {
		new HttpTransprotServerTest().test();
	}
	
	
	
	public void test() {
		
		JsonRpcHemaServer  hema = new JsonRpcHemaServer();		
		hema.addService("hello", new HelloServiceImpl(), HelloService.class);
		hema.addService("user", new UserServiceImpl(), UserService.class);
		
		HttpTransportServer transerver;
		try {
			transerver = new HttpTransportServer(hema,80,InetAddress.getByName("127.0.0.1"));
			transerver.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
//		UndertowTransportServer  t = new UndertowTransportServer(hema);
//		t.start();
		
	}

}
