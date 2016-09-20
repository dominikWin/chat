package chat.interfaces;

import chat.data.ChatContent;
import chat.data.ChatNode;

public interface ChatCompressor {
	public byte[] deflate(byte[] data);
	public byte[] inflate(byte[] data);
}
