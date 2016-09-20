package chat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import chat.data.ChatText;
import chat.data.ChatNode;
import chat.interfaces.ChatHash;

public class HashSHA1 implements ChatHash {

	byte[] hash;

	public HashSHA1(ChatText content) {
		hash = hash(content.getContent());
	}

	public HashSHA1(ChatNode node) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(node.getAuthor());
		sb.append(" ");
		sb.append(node.getTime());
		sb.append(" ");
		sb.append(node.getTextHash());
		sb.append(" ");
		sb.append(node.getParentHash());
		
		hash = hash(sb.toString());
	}

	private static byte[] hash(String content) {
		byte out[];
		MessageDigest hashFunction = null;
		try {
			hashFunction = MessageDigest.getInstance("SHA-1");
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
		String out = new BigInteger(1, hash).toString(16);
		// BigInteger will not include an 0 as the first number
		while(out.length() < 40)
			out = "0" + out;
		return out;
	}

}
