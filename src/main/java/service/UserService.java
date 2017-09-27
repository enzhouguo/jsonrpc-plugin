package service;

import java.util.List;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcService;

import bean.User;

//@JsonRpcService(value="user")
public interface UserService {
    public User createUser(String userName, int age);
    
    public String getVersion();
    
    @JsonRpcMethod("user.findAll")
    public List<User> findAll();
    
    public Integer getInt(Integer code); 

    public String getString(String msg);

    public void doSomething();    
    
 
    public List<User> find();
   
    
}
