package chat.data;

import chat.ServerSettings;
import chat.UNIXTime;
import chat.interfaces.ChatHash;

public class ChatNode {
	
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_TIME = "time";
	public static final String KEY_TEXT = "text";
	public static final String KEY_PARENT = "parent";
	
	private String author;
	private long time;
	private ChatHash text, parent;
	
	public ChatNode(String author, long time, ChatHash content, ChatHash parent) {
		this.author = author;
		this.time = time;
		this.text = content;
		this.parent = parent;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public long getTime() {
		return time;
	}
	
	public ChatHash getTextHash() {
		return text;
	}
	
	public ChatHash getParentHash() {
		return parent;
	}
	
	public static ChatNode getInitialNode() {
		ChatNode node = new ChatNode(ServerSettings.SYSTEM_NICK, UNIXTime.getUNIXTime(), null, null);
		return node;
	}

}
