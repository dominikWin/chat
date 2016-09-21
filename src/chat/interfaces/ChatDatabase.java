package chat.interfaces;

import chat.data.ChatText;

import java.util.List;

import chat.data.ChatNode;
import chat.data.ChatRef;

/**
 * Interface for abstract database for data storage.
 */
public interface ChatDatabase {
	/**
	 * @param hash
	 * @return {@code true} if a {@link ChatNode} with key {@code hash} exists
	 * in database.
	 */
	public boolean existsNode(ChatHash hash);

	/**
	 * @param hash
	 * @return {@code true} if a {@link ChatText} with key {@code hash} exists
	 * in database.
	 */
	public boolean existsText(ChatHash hash);

	/**
	 * @param name
	 * @return {@code true} if a {@link ChatRef} with key {@code hash} exists
	 * in database.
	 */
	public boolean existsRef(String name);

	/**
	 * Adds a {@link ChatNode} to the database.
	 * @param node
	 * @return {@link ChatHash} of added node.
	 */
	public ChatHash add(ChatNode node);

	/**
	 * Add {@link ChatText} to database.
	 * @param content
	 * @return {@link ChatHash} of added text.
	 */
	public ChatHash add(ChatText content);

	/**
	 * Sets the value of a ref on disk.
	 * If the ref does not exist in the database it is created.
	 * @param name
	 * @param hash
	 */
	public void setRef(String name, ChatHash hash);

	/**
	 * Fetches a {@link ChatNode} from the key {@code hash}.
	 * @param hash
	 * @return node from key.
	 */
	public ChatNode getNode(ChatHash hash);

	/**
	 * Fetches a {@link ChatText} from the key {@code hash}.
	 * @param hash
	 * @return text from key.
	 */
	public ChatText getText(ChatHash hash);

	/**
	 * Fetches a {@link ChatRef} from the key {@code name}.
	 * @param name
	 * @return {@code ChatRef} representation of fetched data.
	 */
	public ChatRef getRef(String name);

	/**
	 * @return {@link List} where each item is a {@link String} representing the
	 *         name of each ref in the database.
	 */
	public List<String> listRefs();
}
