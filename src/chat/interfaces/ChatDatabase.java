package chat.interfaces;

import chat.data.ChatText;
import chat.data.ChatNode;

public interface ChatDatabase {
	public boolean existsNode(ChatHash hash);
	public boolean existsText(ChatHash hash);
	
	public ChatHash add(ChatNode node);
	public ChatHash add(ChatText content);
}
