package chat.interfaces;

import chat.data.ChatText;
import chat.data.ChatNode;

public interface ChatSerializer {
	public byte[] serialize(ChatNode node);
	public byte[] serialize(ChatText content);

	public Object deserialize(byte[] data);
}
