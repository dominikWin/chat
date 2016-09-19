package chat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import chat.interfaces.ChatHash;

public class ChatSHA1 implements ChatHash {

	byte[] hash;

	public ChatSHA1(ChatContent content) {
		hash = hash(content.getContent());
	}

	public ChatSHA1(ChatNode node) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(node.getAuthor());
		sb.append(" ");
		sb.append(node.getTime());
		sb.append(" ");
		sb.append(node.getContentHash());
		sb.append(" ");
		sb.append(node.getParentHash());
		
		hash = hash(sb.toString());
	}

	private static byte[] hash(String content) {
		byte out[];
		MessageDigest hashFunction = null;
		try {
			hashFunction = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out = new byte[hashFunction.getDigestLength()];

		hashFunction.reset();
		try {
			hashFunction.update(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out = hashFunction.digest();

		return out;
	}

	public String toString() {
		return new BigInteger(1, hash).toString(16);
	}

}
