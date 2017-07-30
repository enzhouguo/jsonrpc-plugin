package plugin.jsonrpc;

import java.io.IOException;

import com.jfinal.core.Controller;

/**
 * IndexController
 */

public class RpcBaseController extends Controller {

	public void index() {
		JsonRpcHemaServer jsonRpcServer = JsonRpcKit.getInstance();
		try {
			jsonRpcServer.handleRequest(getRequest().getInputStream(), getResponse().getOutputStream());
		} catch (IOException e) {
			renderJson("{\"code\":\"error\"}");
		}
		renderNull();
	}

}
