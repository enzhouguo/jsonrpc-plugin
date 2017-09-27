package plugin.jsonrpc.transport;

import com.googlecode.jsonrpc4j.JsonRpcServer;

public interface TransportServer {
	
	
	public void setJsonRpc(JsonRpcServer jsonRpcServer);
	
	public void start();
	
	public void stop();

}
