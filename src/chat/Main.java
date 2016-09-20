package chat;

import java.util.Scanner;

import chat.data.ChatNode;
import chat.data.ChatText;
import chat.interfaces.ChatSerializer;

public class Main {
	public static void main(String[] args) {
		ChatText c = new ChatText(new Scanner(System.in).nextLine());
		
		
		
		HashSHA1 h = new  HashSHA1(c);
		System.out.println(h + ":" + h.toString().length());
		
		ChatSerializer serializer = new SerializeJSON();
		System.out.println(new String(serializer.serialize(new ChatNode("Chat", 2, new HashSHA1(), new HashSHA1()))));
	}
}
