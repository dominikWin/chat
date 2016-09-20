package chat.interfaces;

import chat.data.ChatContent;
import chat.data.ChatNode;

public interface ChatSerializer {
	public byte[] serialize(ChatNode node);
	public byte[] serialize(ChatContent content);

	public Object deserialize(byte[] data);
}
