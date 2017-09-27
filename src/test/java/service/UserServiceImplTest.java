package service;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

import bean.User;

public class UserServiceImplTest {

	private ByteArrayOutputStream byteArrayOutputStream;
	private JsonRpcHttpClient client;

	@Before
	public void setUp() {
		//client = new JsonRpcClient();
		String url = "http://127.0.0.1/api/jsonrpc";
		try {
			client = new JsonRpcHttpClient(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() {
		client = null;
	}
	
	
	@Test
	public void getversion() throws Throwable{
		Integer[] codes = new Integer[] { 1 };
		String str = client.invoke("user.getVersion", null, String.class);
		System.out.println(str);
	}
	
	@Test
	public void getInt() throws Throwable{
		Integer[] codes = new Integer[] { 1 };
		int str = client.invoke("user.getInt", codes, Integer.class);
		System.out.println(str);
	}
	
//	@Test
//	public void getString(){
//		UserService service = ProxyUtil.createClientProxy(UserService.class.getClassLoader(),UserService.class, client);
//	
//		List<User> a = service.findAll();
//		for (User user : a) {
//			System.out.println(user.getName()+""+user.getAge());
//		}
//		//System.out.println(a);
//		
//	}


}
