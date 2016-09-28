package chat;

import chat.client.ChatClient;
import chat.data.ChatNode;
import chat.data.ChatText;
import chat.interfaces.ChatHash;

public class Main {
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("No arguments set");
			System.exit(1);
		}
		
		if(args.length == 1) {
			if(args[0].equals("--server") || args[0].equals("-s"))
				server();
			else {
				System.err.println("Unknown argument " + args[0]);
			}
		}
		
		if(args.length == 2) {
			client(args[0], Integer.parseInt(args[1]));
		}
		
		System.err.println("Invalid arguments");
	}
	
	private static void client(String ip, int port) {
		ChatClient c = new ChatClient(ip, port);
		c.run();
	}

	private static void server() {
		LocalDB db = new LocalDB(new SerializeJSON(), new CompressZLIB());

		if (!db.existsRef("master")) {
			ChatHash hash = db.add(new ChatText("Text Text"));
			hash = db.add(new ChatNode("Auth", 5, hash, null));
			db.setRef("master", hash);
		}

		WebInterface webInterface = new WebInterface(db);
	}
}
