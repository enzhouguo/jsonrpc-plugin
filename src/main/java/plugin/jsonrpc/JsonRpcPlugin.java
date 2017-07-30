package plugin.jsonrpc;

import com.jfinal.plugin.IPlugin;

public class JsonRpcPlugin implements IPlugin {

	private JsonRpcHemaServer hema;

	public JsonRpcPlugin() {
		hema = new JsonRpcHemaServer();
	}

	public boolean start() {
		JsonRpcKit.init(hema);
		return true;
	}

	public boolean stop() {
		return true;
	}

	public void addService(Object handler, Class<?> remoteInterface) {
		hema.addService(remoteInterface.getName(), handler, remoteInterface);
	}

	public void addService(String name, Object handler, Class<?> remoteInterface) {
		hema.addService(name, handler, remoteInterface);
	}
	
	public void addService(String name, Object handler) {
		hema.addService(name, handler);
	}

}
