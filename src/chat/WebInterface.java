package chat;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import chat.data.ChatNode;
import chat.data.ChatRef;
import chat.data.ChatText;
import chat.interfaces.ChatDatabase;
import chat.interfaces.ChatHash;

/**
 * HTTP interface for database.
 */
public class WebInterface
{
	static HttpServer server;
	static ChatDatabase db;

	/**
	 * Sends fail JSON object to client.
	 * 
	 * @param s
	 * @param reason
	 */
	private static void fail(HttpExchange s, String reason)
	{
		JSONObject json = new JSONObject();
		json.put("fail", reason);
		String out = json.toJSONString();
		try
		{
			s.sendResponseHeaders(200, out.getBytes().length);
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			s.getResponseBody().write(out.getBytes());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			s.getResponseBody().close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebInterface(ChatDatabase db)
	{
		try
		{
			WebInterface.server = HttpServer.create(
					new InetSocketAddress(8001), 0);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebInterface.db = db;
		server.createContext("/ref", new RefHandler());
		server.createContext("/msg", new MsgHandler());
		server.createContext("/snd", new SndHandler());
		server.setExecutor(null);
		server.start();
	}

	static class RefHandler implements HttpHandler
	{

		@Override
		public void handle(HttpExchange s) throws IOException
		{
			String[] split = WebInterface.parseREST(s);

			if (split.length != 2)
			{
				fail(s, "Bad number of arguments");
				return;
			}

			if (!db.existsRef(split[1]))
			{
				fail(s, "Ref does not exist");
				return;
			}

			String out = db.getRef(split[1]).getHead().toString();

			s.sendResponseHeaders(200, out.length());

			s.getResponseBody().write(out.getBytes());
			s.getResponseBody().close();
		}

	}

	static class MsgHandler implements HttpHandler
	{

		@Override
		public void handle(HttpExchange s) throws IOException
		{
			String[] split = parseREST(s);

			if (split.length != 2)
			{
				fail(s, "Bad number of arguments");
				return;
			}

			if (!Verify.goodSHA1(split[1]))
			{
				fail(s, "Invalid SHA1");
				return;
			}

			if (!db.existsNode(new HashSHA1(split[1])))
			{
				fail(s, "No msg found");
				return;
			}

			ChatNode node = db.getNode(new HashSHA1(split[1]));
			ChatText text = db.getText(node.getTextHash());

			JSONObject json = new JSONObject();
			json.put(ChatNode.KEY_AUTHOR, node.getAuthor());
			json.put(ChatNode.KEY_TIME, new Long(node.getTime()));
			json.put(ChatNode.KEY_PARENT, node.getParentHash());
			json.put("hash", new HashSHA1(split[1]).toString());
			json.put("text", text.getContent());

			String out = json.toJSONString();

			s.sendResponseHeaders(200, out.length());

			s.getResponseBody().write(out.getBytes());
			s.getResponseBody().close();
		}
	}

	static class SndHandler implements HttpHandler
	{

		@Override
		public void handle(HttpExchange s) throws IOException
		{
			String[] split = parseREST(s);

			if (split.length != 4)
			{
				fail(s, "Bad number of arguments");
				return;
			}

			// 1: ref, 2: uname, 3: base64

			if (!db.existsRef(split[1]))
			{
				fail(s, "No ref found");
				return;
			}

			ChatRef ref = db.getRef(split[1]);
			ChatHash text = db.add(new ChatText(HTTPEncoder.decode(split[3])));

			ChatNode node = new ChatNode(split[2], UNIXTime.getUNIXTime(),
					text, ref.getHead());
			
			
			db.setRef(split[1], db.add(node));

			JSONObject json = new JSONObject();
			
			json.put("status", new Integer(0));

			String out = json.toJSONString();

			s.sendResponseHeaders(200, out.length());

			s.getResponseBody().write(out.getBytes());
			s.getResponseBody().close();
		}
	}

	public static String[] parseREST(HttpExchange s)
	{
		String uri = s.getRequestURI().toString();

		while (uri.endsWith("/"))
			uri = uri.substring(0, uri.length() - 1);

		uri = uri.substring(1, uri.length()); // Remove '/' from beginning

		String split[] = uri.split("/");

		return split;
	}

}
