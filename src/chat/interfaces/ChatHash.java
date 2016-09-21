package chat.interfaces;

public interface ChatHash {
	
	public String toString();
	
	public boolean isNull();
	
	public boolean equals(ChatHash other);
	
	public byte[] getHash();
	
}
