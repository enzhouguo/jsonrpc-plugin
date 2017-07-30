package plugin.jsonrpc;

public class JsonRpcKit {
	
    private static JsonRpcHemaServer hema1;

    public static void init(JsonRpcHemaServer hema) {
    	hema1 = hema;
    }
    
    
    public static JsonRpcHemaServer getInstance(){
    	 return hema1;
    }
    
    

	

}
