package chat.interfaces;

import chat.data.ChatText;
import chat.data.ChatNode;

/**
 * Interface to serialize {@link ChatNode} and {@link ChatText} objects.
 */
public interface ChatSerializer {
	/**
	 * @param node
	 * @return data of {@code node}.
	 */
	public byte[] serialize(ChatNode node);

	/**
	 * @param text
	 * @return data of {@code text}.
	 */
	public byte[] serialize(ChatText text);

	/**
	 * Returns a object from the {@code data}.
	 * 
	 * @param data
	 * @return a {@link ChatNode} or {@link ChatText} from the {@code data}.
	 */
	public Object deserialize(byte[] data);
}
