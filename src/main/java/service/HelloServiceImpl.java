package service;

import java.util.ArrayList;
import java.util.List;

import bean.User;

public class HelloServiceImpl implements HelloService {

	public List<User> find() {
		List<User> list = new ArrayList<User>();
		list.add(new User("xiaohua", 10));
		list.add(new User("xiaoguo", 12));
		return list;
	}
	
	
	public List  query() {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		return list;
	}

}
