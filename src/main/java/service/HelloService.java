package service;

import java.util.List;

import com.googlecode.jsonrpc4j.JsonRpcService;

import bean.User;


@JsonRpcService(value="hello")
public interface HelloService {
	
	public List<User> find();
	
	public List  query();
	
	

}
