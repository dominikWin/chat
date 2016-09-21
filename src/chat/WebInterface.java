package chat;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import chat.interfaces.ChatDatabase;

public class WebInterface {
	static HttpServer server;
	static ChatDatabase db;

	private static void fail(HttpExchange s, String reason) {
		JSONObject json = new JSONObject();
		json.put("fail", reason);
		String out = json.toJSONString();
		try {
			s.sendResponseHeaders(200, out.getBytes().length);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			s.getResponseBody().write(out.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.getResponseBody().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebInterface(ChatDatabase db) {
		try {
			WebInterface.server = HttpServer.create(new InetSocketAddress(8001), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebInterface.db = db;
		server.createContext("/ref", new RefHandler());
		server.setExecutor(null);
		server.start();
	}

	static class RefHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange s) throws IOException {
			String uri = s.getRequestURI().toString();

			while (uri.endsWith("/"))
				uri = uri.substring(0, uri.length() - 1);

			uri = uri.substring(1, uri.length()); // Remove '/' from beginning

			String split[] = uri.split("/");

			if (split.length != 2) {
				fail(s, "Invalid number of args");
				return;
			}

			if (!db.existsRef(split[1])) {
				fail(s, "Ref does not exist");
				return;
			}

			String out = db.getRef(split[1]).getHead().toString();

			s.sendResponseHeaders(200, out.length());

			s.getResponseBody().write(out.getBytes());
			s.getResponseBody().close();
		}

	}
}
