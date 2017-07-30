package service;

import java.util.ArrayList;
import java.util.List;

import bean.User;

public class UserServiceImpl implements UserService {

public User createUser(String userName, int age) {
    User user = new User();
    user.setName(userName);
    user.setAge(age +19);
    return user;
}

public String getVersion() {
	return "1.4.2";
}


public List<User> findAll() {
	List<User> list = new ArrayList<User>();
	list.add(new User("weiwei", 18));
	list.add(new User("guoenzhou", 19));
	return list;
}

public Integer getInt(Integer code) {
    return code;
}

public String getString(String msg) {
    return msg;
}

public void doSomething() {
    System.out.println("do something");
}

public List<User> find() {
	List<User> list = new ArrayList<User>();
	list.add(new User("bb", 10));
	list.add(new User("aa", 12));
	return list;
}













}