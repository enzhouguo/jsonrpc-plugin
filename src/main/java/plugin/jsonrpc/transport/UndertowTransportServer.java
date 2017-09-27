package plugin.jsonrpc.transport;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import plugin.jsonrpc.rpc.JsonRpcHemaServer;

public class UndertowTransportServer implements TransportServer {
	
	private Undertow server;
	
	//private JsonRpcHemaServer  hema;
	
	

	public UndertowTransportServer(final JsonRpcHemaServer  hema) {
		 server = Undertow.builder()
        		.addListener(80, "127.0.0.1")
                .setHandler(new HttpHandler() {
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                    	hema.handleRequest(exchange.getInputStream(), exchange.getOutputStream());
                    }
                }).build();
        
	}




	@Override
	public void start() {
		server.start();
		
	}
	
	
	

	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		//new UndertowTransportServer().start();
	}

}


