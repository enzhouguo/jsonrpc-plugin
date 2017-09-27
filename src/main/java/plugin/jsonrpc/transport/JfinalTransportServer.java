package plugin.jsonrpc.transport;

import com.jfinal.core.Controller;

import plugin.jsonrpc.jfinal.JsonRpcKit;

public class JfinalTransportServer implements TransportServer {

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}
	
	
	public class RpcBaseController extends Controller {

		public void index() {
			JsonRpcKit.handleRequest(getRequest(), getResponse());
			renderNull();
		}

	}

}
