package plugin.jsonrpc;

import com.jfinal.core.Controller;

import plugin.jsonrpc.jfinal.JsonRpcKit;

/**
 * IndexController
 */

public class RpcBaseController extends Controller {

	public void index() {
		JsonRpcKit.handleRequest(getRequest(), getResponse());
		renderNull();
	}

}
