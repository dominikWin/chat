package chat.data;

import chat.interfaces.ChatHash;

/**
 * Class that represents a ref object. Stores name and head (hash).
 */
public class ChatRef {
	private String name;
	private ChatHash head;
	
	/**
	 * Creates ChatRef from parameters.
	 * @param name
	 * @param head
	 */
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
