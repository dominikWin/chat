package chat.interfaces;

import chat.data.ChatText;
import chat.data.ChatNode;

public interface ChatDatabase {
	public boolean existsNode(ChatHash hash);
	public boolean existsContent(ChatHash hash);
	
	public void add(ChatNode node);
	public void add(ChatText content);
}
