package plugin.jsonrpc.jfinal;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.ReadContext;
import com.googlecode.jsonrpc4j.ErrorResolver.JsonError;

import plugin.jsonrpc.rpc.JsonRpcHemaServer;

public class JsonRpcKit {
	
    private static JsonRpcHemaServer jsonrpc;

    public static void init(JsonRpcHemaServer hema) {
    	jsonrpc = hema;
    }
    
    
    public static JsonRpcHemaServer getInstance(){
    	 return jsonrpc;
    }
    
    
    public static void handleRequest(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		jsonrpc.handleRequest(request.getInputStream(), response.getOutputStream());    		
		} catch (IOException e) {
		}
    	
	}
    
    
    
    
    

	

}
