import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

import bean.User;
import service.UserService;

public class StremServerTest {
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", 8088);
			JsonRpcClient jsonRpcClient = new JsonRpcClient();
	        UserService service = ProxyUtil.createClientProxy(UserService.class.getClassLoader(), UserService.class,jsonRpcClient, socket);
			List<User> a = service.findAll();
			for (User user : a) {
				  System.out.println(user.getName() + "" + user.getAge());
			}
			
			//System.out.println(service.getVersion());
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
