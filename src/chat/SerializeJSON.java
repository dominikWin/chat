package chat;

import chat.data.ChatText;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import chat.data.ChatNode;
import chat.interfaces.ChatSerializer;

public class SerializeJSON implements ChatSerializer {
	private static final String NODE_PREFIX = "node ";
	private static final String TEXT_PREFIX = "text ";

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

		String content = NODE_PREFIX + json.toJSONString();
		return toByteArray(content);
	}

	@Override
	public byte[] serialize(ChatText text) {
		return toByteArray(TEXT_PREFIX + text.getContent());
	}

	private ChatNode reconstructNode(String text) {
		JSONParser jsonParser = new JSONParser();
		JSONObject object = null;
		try {
			object = (JSONObject) jsonParser.parse(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChatNode out = new ChatNode((String) object.get(ChatNode.KEY_AUTHOR),
				((Long) object.get(ChatNode.KEY_TIME)).longValue(), (String) object.get(ChatNode.KEY_TEXT),
				(String) object.get(ChatNode.KEY_PARENT));

		return out;
	}

	@Override
	public Object deserialize(byte[] data) {
		String text = null;
		try {
			text = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (text != null);

		if (text.startsWith(NODE_PREFIX))
			return reconstructNode(text.substring(NODE_PREFIX.length()));
		else if (text.startsWith(TEXT_PREFIX))
			return reconstructNode(text.substring(TEXT_PREFIX.length()));
		else
			return null;
	}

}
