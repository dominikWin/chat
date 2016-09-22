package chat.interfaces;

/**
 * Interface for a generic hash type.
 */
public interface ChatHash {

	/**
	 * @return a lowercase base 16 representation of the hash.
	 */
	public String toString();

	/**
	 * @return {@code true} if the hash is null.
	 */
	public boolean isNull();

	/**
	 * @param other
	 * @return {@code true} if this and {@code other} have the same hash value.
	 */
	public boolean equals(ChatHash other);

	/**
	 * @return {@code byte[]} representation of hash.
	 */
	public byte[] getHash();

}
