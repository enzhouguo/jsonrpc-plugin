
import static com.googlecode.jsonrpc4j.JsonRpcBasicServer.ID;
import static com.googlecode.jsonrpc4j.JsonRpcBasicServer.PARAMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.RequestIDGenerator;

public class JsonRpcClientTest2 {

	private ByteArrayOutputStream byteArrayOutputStream;
	private JsonRpcClient client;

	@Before
	public void setUp() {
		client = new JsonRpcClient();
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() {
		client = null;
	}

	@Test
	public void testInvokeNoParams() throws Throwable {

		client.invoke("test", new Object[0], byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);
		System.out.println(node.isArray());
		assertFalse(node.has(PARAMS));
	}

	private JsonNode readJSON(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
		return client.getObjectMapper().readTree(byteArrayOutputStream.toString(StandardCharsets.UTF_8.name()));
	}

	@Test
	public void testInvokeArrayParams() throws Throwable {
		client.invoke("test", new Object[]{1, 2}, byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);

		assertTrue(node.has(PARAMS));
		assertTrue(node.get(PARAMS).isArray());
		assertEquals(1, node.get(PARAMS).get(0).intValue());
		assertEquals(2, node.get(PARAMS).get(1).intValue());
	}

	@Test
	public void testInvokeAdditionalJsonContent() throws Throwable {
		final String auth = "auth";
		final String authValue = "secret";
		client.setAdditionalJsonContent(new HashMap<String, Object>() {
			{
				put(auth, authValue);
			}
		});
		client.invoke("test", new Object[]{1, 2}, byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);

		assertTrue(node.has(auth));
		assertEquals(node.get(auth).textValue(), authValue);
	}

	@Test
	public void testInvokeHashParams() throws Throwable {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hello", "test");
		params.put("x", 1);
		client.invoke("test", params, byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);

		assertTrue(node.has(PARAMS));
		assertTrue(node.get(PARAMS).isObject());
		assertEquals("test", node.get(PARAMS).get("hello").textValue());
		assertEquals(1, node.get(PARAMS).get("x").intValue());
	}

	@Test
	public void testIDGeneration() throws IOException {
		client.setRequestIDGenerator(new RequestIDGenerator() {
			public String generateID() {
				return "test";
			}
		});

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hello", "test");
		params.put("x", 1);
		client.invoke("test", params, byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);

		assertTrue(node.has(PARAMS));
		assertTrue(node.get(PARAMS).isObject());
		assertEquals("test", node.get(ID).asText());
	}

	@Test
	public void testRandomIDGeneration() throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hello", "test");
		params.put("x", 1);
		client.invoke("test", params, byteArrayOutputStream);
		JsonNode node = readJSON(byteArrayOutputStream);

		assertTrue(node.has(PARAMS));
		assertTrue(node.get(PARAMS).isObject());
		try {
			Long.parseLong(node.get(ID).asText());
		} catch (NumberFormatException e) {
			fail();
		}
	}

}
