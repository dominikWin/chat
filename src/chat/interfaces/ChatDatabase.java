package chat.interfaces;

import chat.data.ChatContent;
import chat.data.ChatNode;

public interface ChatDatabase {
	public boolean existsNode(String hash);
	public boolean existsContent(String hash);
	
	public void add(ChatNode node);
	public void add(ChatContent content);
}
