package chat.interfaces;

/**
 * Interface that allows for compression and decompression of data.
 * All data uses {@code byte[]}s.
 */
public interface ChatCompressor {
	
	/**
	 * @param data
	 * @return a compressed representation of {@code data}.
	 */
	public byte[] deflate(byte[] data);
	
	/**
	 * @param data
	 * @return a decompressed representation of {@code data}.
	 */
	public byte[] inflate(byte[] data);
}
