package chat.interfaces;

import chat.data.ChatText;
import chat.data.ChatNode;
import chat.data.ChatRef;

public interface ChatDatabase {
	public boolean existsNode(ChatHash hash);
	public boolean existsText(ChatHash hash);
	
	public ChatHash add(ChatNode node);
	public ChatHash add(ChatText content);
	
	public ChatNode getNode(ChatHash hash);
	public ChatText getText(ChatHash hash);
	
	public boolean existsRef(String name);
	public void setRef(String name, ChatHash hash);
	public ChatRef getRef(String name);
}
