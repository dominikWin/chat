package chat.data;

/**
 * Class that represents text object. Stores content of message.
 */
public class ChatText {
	
	private String content;
	
	/**
	 * Creates ChatText with content.
	 * @param content
	 */
	public ChatText(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

}
