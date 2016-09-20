package chat;

import chat.interfaces.ChatHash;

public class ChatNode {
	
	private String author;
	private long time;
	private ChatHash content, parent;
	
	public ChatNode(String author, long time, ChatHash content, ChatHash parent) {
		this.author = author;
		this.time = time;
		this.content = content;
		this.parent = parent;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public long getTime() {
		return time;
	}
	
	public ChatHash getContentHash() {
		return content;
	}
	
	public ChatHash getParentHash() {
		return parent;
	}
	
	public static ChatNode getInitialNode() {
		ChatNode node = new ChatNode(Properties.SYSTEM_NICK, ChatTimer.getTime(), null, null);
		return node;
	}

}
