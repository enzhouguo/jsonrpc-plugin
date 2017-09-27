package plugin.jsonrpc.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.ErrorResolver.JsonError;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.ReadContext;

/**
 * A multiple service dispatcher that supports JSON-RPC "method" names
 * that use dot-notation to select a server endpoint.  For example:
 * <pre>
 * {
 *    "jsonrpc": "2.0",
 *    "method": "service.method",
 *    "params": {"foo", "bar"},
 *    "id": 1
 * }
 * </pre>
 * An example of using this class is:
 * <code>
 * JsonRpcMultiServer rpcServer = new JsonRpcMultiServer();
 * rpcServer.addService("Foo", new FooService())
 * .addService("Bar", new BarService());
 * </code>
 * A client can then call a <i>test(String, String)</i> method on the Foo service
 * like this:
 * <pre>
 * {
 *    "jsonrpc": "2.0",
 *    "method": "Foo.test",
 *    "params": ["some", "thing"],
 *    "id": 1
 * }
 * </pre>
 */
public class JsonRpcHemaServer extends JsonRpcServer {
	
	public static final char DEFAULT_SEPARATOR = '.';
	private static final Logger logger = LoggerFactory.getLogger(JsonRpcHemaServer.class);
	
	private final Map<String, Object> handlerMap;
	private final Map<String, Class<?>> interfaceMap;
	private char separator = DEFAULT_SEPARATOR;
	private final ObjectMapper mapper;
	
	public JsonRpcHemaServer() {
		this(new ObjectMapper());
		logger.debug("created empty multi server");
	}
	
	public JsonRpcHemaServer(ObjectMapper mapper) {
		super(mapper, null);
		this.mapper = mapper;		
		this.handlerMap = new HashMap<String, Object>();
		this.interfaceMap = new HashMap<String, Class<?>>();
	}
	
	public int handleRequest(final InputStream input, final OutputStream output) throws IOException {
		final ReadContext readContext = ReadContext.getReadContext(input, mapper);
		try {
			readContext.assertReadable();
			final JsonNode jsonNode = readContext.nextValue();
			return handleJsonNodeRequest(jsonNode, output).code;
		} catch (JsonParseException e) {
			return writeAndFlushValueError(output, createResponseError(JSONRPC, NULL, JsonError.PARSE_ERROR)).code;
		}
	}
	
	private JsonError writeAndFlushValueError(OutputStream output, ErrorObjectWithJsonError value) throws IOException {
		logger.debug("failed {}", value);
		writeAndFlushValue(output, value.node);
		return value.error; 
	}
	
	/**
	 * Writes and flushes a value to the given {@link OutputStream}
	 * and prevents Jackson from closing it. Also writes newline.
	 *
	 * @param output the {@link OutputStream}
	 * @param value  the value to write
	 * @throws IOException on error  
	 */
	private void writeAndFlushValue(OutputStream output, Object value) throws IOException {
		logger.debug("Response: {}", value);
		mapper.writeValue(output, value);
		output.write('\n');
	}
	
	private static class ErrorObjectWithJsonError {
		private final ObjectNode node;
		private final JsonError error;
		
		public ErrorObjectWithJsonError(ObjectNode node, JsonError error) {
			this.node = node;
			this.error = error;
		}
		
		@Override
		public String toString() {
			return "ErrorObjectWithJsonError{" +
					"node=" + node +
					", error=" + error +
					'}';
		}
	}
	
	/**
	 * Convenience method for creating an error response.
	 *
	 * @param jsonRpc     the jsonrpc string
	 * @param id          the id
	 * @param errorObject the error data (if any)
	 * @return the error response
	 */
	private ErrorObjectWithJsonError createResponseError(String jsonRpc, Object id, JsonError errorObject) {
		ObjectNode response = mapper.createObjectNode();
		ObjectNode error = mapper.createObjectNode();
		error.put(ERROR_CODE, errorObject.code);
		error.put(ERROR_MESSAGE, errorObject.message);
		if (errorObject.data != null) {
			error.set(DATA, mapper.valueToTree(errorObject.data));
		}
		response.put(JSONRPC, jsonRpc);
		if (Integer.class.isInstance(id)) {
			response.put(ID, Integer.class.cast(id).intValue());
		} else if (Long.class.isInstance(id)) {
			response.put(ID, Long.class.cast(id).longValue());
		} else if (Float.class.isInstance(id)) {
			response.put(ID, Float.class.cast(id).floatValue());
		} else if (Double.class.isInstance(id)) {
			response.put(ID, Double.class.cast(id).doubleValue());
		} else if (BigDecimal.class.isInstance(id)) {
			response.put(ID, BigDecimal.class.cast(id));
		} else {
			response.put(ID, String.class.cast(id));
		}
		response.set(ERROR, error);
		return new ErrorObjectWithJsonError(response, errorObject);
	}
	
	
	public JsonRpcHemaServer addService(String name, Object handler) {
		return addService(name, handler, null);
	}
	
	public JsonRpcHemaServer addService(String name, Object handler, Class<?> remoteInterface) {
		logger.debug("add service interface {} with handler {}", remoteInterface, handler);
		handlerMap.put(name, handler);
		if (remoteInterface != null) {
			interfaceMap.put(name, remoteInterface);
		}
		return this;
	}
	
	public ObjectMapper getMapper(){
		return this.mapper;
	}
	
	public char getSeparator() {
		return this.separator;
	}
	
	public void setSeparator(char separator) {
		this.separator = separator;
	}
	
	/**
	 * Returns the handler's class or interfaces.  The serviceName is used
	 * to look up a registered handler.
	 *
	 * @param serviceName the optional name of a service
	 * @return the class
	 */
	@Override
	protected Class<?>[] getHandlerInterfaces(String serviceName) {
		Class<?> remoteInterface = interfaceMap.get(serviceName);
		if (remoteInterface != null) {
			return new Class<?>[]{remoteInterface};
		} else if (Proxy.isProxyClass(getHandler(serviceName).getClass())) {
			return getHandler(serviceName).getClass().getInterfaces();
		} else {
			return new Class<?>[]{getHandler(serviceName).getClass()};
		}
	}
	
	/**
	 * Get the service name from the methodNode.  JSON-RPC methods with the form
	 * Service.method will result in "Service" being returned in this case.
	 *
	 * @param methodName method name
	 * @return the name of the service, or <code>null</code>
	 */
	@Override
	protected String getServiceName(final String methodName) {
		if (methodName != null) {
			int ndx = methodName.indexOf(this.separator);
			if (ndx > 0) {
				return methodName.substring(0, ndx);
			}
		}
		return methodName;
	}
	
	/**
	 * Get the method name from the methodNode, stripping off the service name.
	 *
	 * @param methodName the JsonNode for the method
	 * @return the name of the method that should be invoked
	 */
	@Override
	protected String getMethodName(final String methodName) {
		if (methodName != null) {
			int ndx = methodName.indexOf(this.separator);
			if (ndx > 0) {
				return methodName.substring(ndx + 1);
			}
		}
		return methodName;
	}
	
	/**
	 * Get the handler (object) that should be invoked to execute the specified
	 * RPC method based on the specified service name.
	 *
	 * @param serviceName the service name
	 * @return the handler to invoke the RPC call against
	 */
	@Override
	protected Object getHandler(String serviceName) {
		Object handler = handlerMap.get(serviceName);
		if (handler == null) {
			logger.error("Service '{}' is not registered in this multi-server", serviceName);
			throw new RuntimeException("Service '" + serviceName + "' does not exist");
		}
		return handler;
	}
}

