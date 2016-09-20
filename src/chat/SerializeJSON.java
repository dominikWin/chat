package chat;

import chat.data.ChatText;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONObject;

import chat.data.ChatNode;
import chat.interfaces.ChatSerializer;
import jdk.nashorn.api.scripting.JSObject;

public class SerializeJSON implements ChatSerializer {
	private static final String NODE_PREFIX = "node";
	private static final String TEXT_PREFIX = "text";

	private byte[] toByteArray(String text) {
		try {
			return text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] serialize(ChatNode node) {
		JSONObject json = new JSONObject();

		json.put(ChatNode.KEY_AUTHOR, node.getAuthor());
		json.put(ChatNode.KEY_TIME, new Long(node.getTime()));
		json.put(ChatNode.KEY_TEXT, node.getTextHash() == null ? null : node.getTextHash().toString());
		json.put(ChatNode.KEY_PARENT, node.getParentHash() == null ? null : node.getParentHash().toString());

		String content = NODE_PREFIX + " " + json.toJSONString();
		return toByteArray(content);
	}

	@Override
	public byte[] serialize(ChatText text) {
		return toByteArray(TEXT_PREFIX + " " + text.getContent());
	}

	@Override
	public Object deserialize(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
