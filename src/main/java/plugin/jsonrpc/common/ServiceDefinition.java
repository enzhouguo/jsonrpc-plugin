package plugin.jsonrpc.common;

public class ServiceDefinition {
  
	
	private String ServiceName;
	private Object handler;
	private Class<?> remoteInterface;
	
	
	
	public ServiceDefinition(String serviceName, Object handler, Class<?> remoteInterface) {
		super();
		ServiceName = serviceName;
		this.handler = handler;
		this.remoteInterface = remoteInterface;
	}
	
	
	
	public ServiceDefinition(Object handler, Class<?> remoteInterface) {
		super();
		this.handler = handler;
		this.remoteInterface = remoteInterface;
	}



	public String getServiceName() {
		return ServiceName;
	}
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public Object getHandler() {
		return handler;
	}
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	public Class<?> getRemoteInterface() {
		return remoteInterface;
	}
	public void setRemoteInterface(Class<?> remoteInterface) {
		this.remoteInterface = remoteInterface;
	}
	

}
