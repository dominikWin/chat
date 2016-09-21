package chat.data;

import chat.interfaces.ChatHash;

public class ChatRef {
	private String name;
	private ChatHash head;
	
	public ChatRef(String name, ChatHash head) {
		this.name = name;
		this.head = head;
	}
	
	public String getName() {
		return name;
	}
	
	public ChatHash getHead() {
		return head;
	}
	
}
