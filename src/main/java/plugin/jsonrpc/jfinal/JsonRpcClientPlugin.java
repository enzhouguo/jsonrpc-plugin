package plugin.jsonrpc.jfinal;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.IPlugin;

import plugin.jsonrpc.common.ServiceDefinition;
import plugin.jsonrpc.rpc.JsonRpcHemaServer;
import plugin.jsonrpc.transport.TransportServer;

public class JsonRpcClientPlugin implements IPlugin {
	
	private List<ServiceDefinition> serviceList = new ArrayList<>();
	
	
	

	@Override
	public boolean start() {
		
		return false;
	}

	@Override
	public boolean stop() {
		return false;
	}
	
	
	public void addService(ServiceDefinition serviceDefinition) {
		serviceList.add(serviceDefinition);
	}	

	public void addService(Object handler, Class<?> remoteInterface) {
		serviceList.add(new ServiceDefinition(remoteInterface.getName(), handler, remoteInterface));
	}

	public void addService(String name, Object handler, Class<?> remoteInterface) {
		serviceList.add(new ServiceDefinition(name, handler, remoteInterface));
	}
	
	

}
