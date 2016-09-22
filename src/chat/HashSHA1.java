package chat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import chat.data.ChatText;
import chat.data.ChatNode;
import chat.interfaces.ChatHash;

/**
 * SHA1 implementation of {@link ChatHash}.
 */
public class HashSHA1 implements ChatHash {

	private byte[] hash;
	
	/**
	 * @param c
	 * @return value of hexadecimal character {@code c}.
	 */
	private static int hex(char c) {
		if(c >= 0x30 && c <= 0x39)
			return c - 0x30;
		if(c >= 0x61 && c <= 0x66)
			return c - 0x57;
		System.err.println("Unknown hex char " + c);
		return -1;
	}
	
	/**
	 * Create a new null HashSHA1.
	 */
	public HashSHA1() {
		hash = null;
	}
	
	/**
	 * Create a HashSHA1 from the passed string.
	 * @param hashStr {@link String} representation of a SHA1.
	 */
	public HashSHA1(String hashStr) {
		if(hashStr == null || hashStr.equals("null")) {
			hash = null;
			return;
		}
		assert(hashStr.length() == 40);

		// Fairly efficient way to convert string to byte[] for SHA1.
		hash = new byte[20];
		for(int i = 0; i < 40; i++) {
			hash[i/2] = (byte) ((hash[i/2] << ((i%2) * 4)) + hex(hashStr.charAt(i)));
		}
	}

	/**
	 * Create hash from {@link ChatText} object.
	 * @param content
	 */
	public HashSHA1(ChatText content) {
		hash = hash(content.getContent());
	}

	/**
	 * Create hash from {@link ChatNode} object.
	 * @param node
	 */
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

	/**
	 * @param content
	 * @return SHA1 value of {@link String} {@code content}.
	 */
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
		if(hash == null)
			return "null";
		String out = new BigInteger(1, hash).toString(16);
		// BigInteger will not include an 0 as the first number
		while(out.length() < 40)
			out = "0" + out;
		return out;
	}
	
	public boolean isNull() {
		return hash == null;
	}

	@Override
	public boolean equals(ChatHash other) {
		if(hash == null || other.getHash() == null)
			return hash == other.getHash();
		return Arrays.equals(other.getHash(), hash);
	}

	@Override
	public byte[] getHash() {
		return hash;
	}

}
