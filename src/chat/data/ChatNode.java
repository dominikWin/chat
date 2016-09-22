package chat.data;

import chat.HashSHA1;
import chat.interfaces.ChatHash;

/**
 * Class that represents a node object. Stores author, time, parent (hash), and
 * text (hash).
 */
public class ChatNode {

	public static final String KEY_AUTHOR = "author";
	public static final String KEY_TIME = "time";
	public static final String KEY_TEXT = "text";
	public static final String KEY_PARENT = "parent";

	private String author;
	private long time;
	private ChatHash text, parent;

	/**
	 * Creates a node given parameters.
	 * 
	 * @param author
	 * @param time
	 * @param text
	 * @param parent
	 */
	public ChatNode(String author, long time, ChatHash text, ChatHash parent) {
		this.author = author;
		this.time = time;
		this.text = text;
		this.parent = parent;
	}

	/**
	 * Create a node after creating {@link ChatHash} from {@code text} and
	 * {@code parent}.
	 * 
	 * @param author
	 * @param time
	 * @param text
	 * @param parent
	 */
	public ChatNode(String author, long time, String text, String parent) {
		this.author = author;
		this.time = time;
		this.text = new HashSHA1(text);
		this.parent = new HashSHA1(parent);
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

}
