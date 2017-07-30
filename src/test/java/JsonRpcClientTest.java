

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class JsonRpcClientTest {

	static JsonRpcHttpClient client;

	public static void main(String[] args) throws Throwable {
		// String url = "http://127.0.0.1:8080/rpcdemo/json-rpc";
		String url = "http://127.0.0.1:89/jsonrpc";

		//String jsonparams = "{\"jsonrpc\":\"2.0\",\"method\":\"metric.getUserProjectList\",\"params\":[\"guoenzhou\"],\"id\":\"10\"}";
		
		String jsonparams= "{\"jsonrpc\":\"2.0\",\"method\":\"plugin.getPluginList\",\"id\":\"10\"}";
		
		//String jsonparams = "[{\"jsonrpc\":\"2.0\",\"method\":\"metric.getUserProjectList\",\"params\":[\"guoenzhou\"],\"id\":\"10\"},{\"jsonrpc\":\"2.0\",\"method\":\"plugin.getPluginList\",\"id\":\"10\"}]";
		String result = get_data(jsonparams, url);
		
		System.out.println(result);
		
//		client = new JsonRpcHttpClient(new URL(url));
//
//		JsonRpcClientTest rpcTest = new JsonRpcClientTest();
//		rpcTest.jsonHttpTest();
//		rpcTest.getInt(1000);
//		System.out.println(rpcTest.getInt(10001));
//		String res = rpcTest.reqHttp(url, jsonparams);
//		System.out.println(res);

	}

	public void jsonHttpTest() {
		try {
			// Map<String, String> headers = new HashMap<String, String>();
			// headers.put("UserKey", "hjckey");
			// client.setHeaders(headers);
			// client.setContentType("application/json-rpc;charset=utf-8");
			Integer[] codes = new Integer[] { 1 };
			String str = client.invoke("getVersion", null, String.class);
 
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void doSomething() throws Throwable {
		client.invoke("doSomething", null);
	}

	public int getInt(int code) throws Throwable {
		Integer[] codes = new Integer[] { code };
		return client.invoke("getInt", codes, Integer.class);
	}

	public String getString(String msg) throws Throwable {
		String[] msgs = new String[] { msg };
		return client.invoke("getString", msgs, String.class);
	}

	/**
	 * @param jsonArgument
	 * @return
	 */
	public static String get_data(String jsonArgument, String zabbixUrl) {
		try {
			URL httpUrl = new URL(zabbixUrl);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json-rpc");
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			// 锟斤拷锟斤拷锟斤拷
			StringBuffer params = new StringBuffer();
			params.append(jsonArgument);
			byte[] bypes = params.toString().getBytes();
			conn.getOutputStream().write(bypes);// 锟斤拷锟斤拷锟斤拷锟�
			// 锟斤拷锟斤拷
			int statusCode = conn.getResponseCode();
			System.out.println(statusCode);
			if (statusCode == 200 && conn.getInputStream() != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String result = reader.readLine();
				reader.close();
				return result;
			}
		} catch (Exception e) {

		}
		return null;
	}

	public String reqHttp(String url, String params) {
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
			// connection.setConnectTimeout(connectionTimeoutMillis);
			// connection.setReadTimeout(readTimeoutMillis);
			connection.setAllowUserInteraction(false);
			connection.setDefaultUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.connect();
			OutputStream send = connection.getOutputStream();
			byte[] bypes = params.toString().getBytes();
			connection.getOutputStream().write(bypes);
			send.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = reader.readLine();		
			reader.close();
			connection.disconnect();
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
