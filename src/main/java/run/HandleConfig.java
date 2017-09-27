package run;

import java.io.IOException;
import java.net.UnknownHostException;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

import plugin.jsonrpc.RpcBaseController;
import plugin.jsonrpc.jfinal.JsonRpcServerPlugin;
import plugin.jsonrpc.transport.StockTransportServer;
import service.HelloService;
import service.HelloServiceImpl;
import service.UserService;
import service.UserServiceImpl;


public class HandleConfig extends JFinalConfig {



	public HandleConfig() {
		super();
	}


	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		
		//me.setMainRenderFactory(new FreeMark);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/jsonrpc", RpcBaseController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {		
		JsonRpcServerPlugin  jsonrpcPlugin = new JsonRpcServerPlugin();		
		try {
			StockTransportServer stock = new StockTransportServer("0.0.0.0", 8088);
			jsonrpcPlugin.setTransport(stock);		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		HttpTransportServer http = new HttpTransportServer(8089);
//		jsonrpcPlugin.setTransport(http);
		jsonrpcPlugin.addService("hello", new HelloServiceImpl(), HelloService.class);
		jsonrpcPlugin.addService("user", new UserServiceImpl(), UserService.class);	
		me.add(jsonrpcPlugin);
	}

	/**
	 * 配置全局拦截
	 */
	public void configInterceptor(Interceptors me) {
		//me.add(RpcFilter.class);

	}

	/**
	 * 配置处理
	 */
	public void configHandler(Handlers me) {
		

	}

	public void afterJFinalStart() {
	};

	/**
	 * 系统关闭前调�?
	 */
	public void beforeJFinalStop() {
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项�? 运行�? main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不�?定要放于�?
	 */
	public static void main(String[] args) {
		try {
			JFinal.start("src/main/webapp", 80, "/", 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	
	
}
