package chat;

import java.util.Scanner;

import chat.data.ChatNode;
import chat.data.ChatText;
import chat.interfaces.ChatHash;
import chat.interfaces.ChatSerializer;

public class Main {
	public static void main(String[] args) {
//		ChatText c = new ChatText(new Scanner(System.in).nextLine());
		
		LocalDB db = new LocalDB(new SerializeJSON(), new CompressZLIB());
		ChatHash hash = db.add(new ChatText("Text Text"));
		db.add(new ChatNode("Auth", 5, hash, null));
		
		
//		HashSHA1 h = new  HashSHA1(c);
//		System.out.println(h + ":" + h.toString().length());
//		
//		ChatSerializer serializer = new SerializeJSON();
//		String s;
//		System.out.println(s = new String(serializer.serialize(new ChatNode("Chat", 2, new HashSHA1(), new HashSHA1()))));
//		System.out.println(((ChatNode) serializer.deserialize(s.getBytes())).getTime());
	}
}
