package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ChatClient {

	String ip;
	int port;

	public ChatClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void run() {

	}

	private String httpGet(String link) {
		URL url;
		try {
			url = new URL(link);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URLConnection connection;
		try {
			connection = url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String out = "";
		try {
			Scanner s = new Scanner(connection.getInputStream());
			while (s.hasNextLine())
				out += s.nextLine();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
