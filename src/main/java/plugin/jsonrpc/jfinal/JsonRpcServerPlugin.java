package plugin.jsonrpc.jfinal;


import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.IPlugin;

import plugin.jsonrpc.common.ServiceDefinition;
import plugin.jsonrpc.rpc.JsonRpcHemaServer;
import plugin.jsonrpc.transport.TransportServer;

public class JsonRpcServerPlugin implements IPlugin {

	private JsonRpcHemaServer hema;
	private TransportServer  transport;	
	
    private List<ServiceDefinition> serviceList = new ArrayList<>();
	
//	private String id;
//	private String export;
//	private String groupName;
//	private String module;
	
	
	public void setTransport(TransportServer transport) {
		this.transport = transport;
	}

	public JsonRpcServerPlugin() {
		hema = new JsonRpcHemaServer();
	}

	public boolean start() { 
		
		
		if(null != transport) {
			transport.setJsonRpc(hema);
			transport.start();
		}
		
		for (ServiceDefinition serviceDefinition : serviceList) {
			hema.addService(serviceDefinition.getServiceName(), serviceDefinition.getHandler(), serviceDefinition.getRemoteInterface());
		}
		
		JsonRpcKit.init(hema);

		return true;
	}

	public boolean stop() {
		if(null != transport) {
			transport.stop();
		}
		return true;
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
