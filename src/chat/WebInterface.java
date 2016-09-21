package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import chat.interfaces.ChatDatabase;

public class WebInterface {
	HttpServer server;
	ChatDatabase db;

	public WebInterface(ChatDatabase db) {
		try {
			this.server = HttpServer.create(new InetSocketAddress(8000), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.db = db;
		server.createContext("/ref", new RefHandler());
		server.setExecutor(null);
		server.start();
	}

	static class RefHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange s) throws IOException {
			String uri = s.getRequestURI().toString();
			System.out.println(uri);

			while (uri.endsWith("/"))
				uri = uri.substring(0, uri.length() - 1);
			
			uri = uri.substring(1, uri.length()); //Remove '/' from beginning
			
			System.out.println("Trimmed " + uri);
			
			String split[] = uri.split("/");
			
			System.out.println(Arrays.toString(split));
			
			s.sendResponseHeaders(200, 0);

			s.getResponseBody().write("Text".getBytes());
			s.getResponseBody().close();
		}

	}
}
