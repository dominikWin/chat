package chat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import chat.data.ChatText;
import chat.data.ChatNode;
import chat.data.ChatRef;
import chat.interfaces.ChatCompressor;
import chat.interfaces.ChatDatabase;
import chat.interfaces.ChatHash;
import chat.interfaces.ChatSerializer;

/**
 * Implementation of {@link ChatDatabase} where data is stored locally in a
 * similar way to git.
 */
public class LocalDB implements ChatDatabase {

	private static final String PATH = "./";
	private static final String DATA_DIR = PATH + ".chatdata";
	private static final String OBJECTS_DIR = DATA_DIR + "/objects";
	private static final String REFS_DIR = DATA_DIR + "/refs";

	private static final String REQUIRED_DIRS[] = new String[] { DATA_DIR, OBJECTS_DIR, REFS_DIR };

	private ChatSerializer serializer;
	private ChatCompressor compressor;

	/**
	 * Create a LocalDB with the serializer and compressor types.
	 * 
	 * @param serializer
	 * @param compressor
	 */
	public LocalDB(ChatSerializer serializer, ChatCompressor compressor) {
		if (!verifyDir()) {
			System.out.println("Can't verify data directory");
			System.exit(1);
		}
		assert (serializer != null);
		this.serializer = serializer;
		this.compressor = compressor;
	}

	/**
	 * @param path
	 * @return content of file at {@code path}.
	 */
	private byte[] getFileContents(String path) {
		try {
			return Files.readAllBytes((new File(path).toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("Trying to get contents of non existant file");
		return null;
	}

	/**
	 * Write {@code data} to file at {@code path}.
	 * 
	 * @param path
	 * @param data
	 */
	private void writeToFile(String path, byte[] data) {
		try {
			Files.write((new File(path).toPath()), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param path
	 * @return {@code true} if file at {@code path} exists.
	 */
	public boolean fileExists(String path) {
		File f = new File(path);
		return f.exists() && f.isFile() && f.canRead();
	}

	/**
	 * @param path
	 * @return {@code true} if directory at {@code path} exists.
	 */
	private boolean dirExists(String path) {
		File f = new File(path);
		return f.exists() && f.isDirectory();
	}

	/**
	 * Creates a directory at {@code path}.
	 * 
	 * @param path
	 * @return {@code true} if directory was created.
	 */
	private boolean makeDir(String path) {
		File f = new File(path);
		return f.mkdir();
	}

	/**
	 * Makes sure data directory is formated for usage, and creates it if
	 * needed.
	 * 
	 * @return {@code true} if the directory is usable.
	 */
	private boolean verifyDir() {
		for (String path : REQUIRED_DIRS) {
			if (!dirExists(path)) {
				System.out.println("Creating dir " + path);
				if (!makeDir(path)) {
					System.err.println("Can't make dir " + path);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean existsNode(ChatHash hash) {
		if (hash.isNull()) {
			System.err.println("Trying to check if null node exists");
			return false;
		}
		String path = OBJECTS_DIR + "/" + hash.toString();
		Object parsed = serializer.deserialize(compressor.inflate(getFileContents(path)));
		return fileExists(path) && parsed != null && parsed instanceof ChatNode;
	}

	@Override
	public boolean existsText(ChatHash hash) {
		if (hash.isNull()) {
			System.err.println("Trying to check if null text exists");
			return false;
		}
		String path = OBJECTS_DIR + "/" + hash.toString();
		return fileExists(path) && serializer.deserialize(getFileContents(path)) instanceof ChatText;
	}

	@Override
	public ChatHash add(ChatNode node) {
		byte[] data = serializer.serialize(node);
		HashSHA1 h = new HashSHA1(node);
		String hash = h.toString();
		data = compressor.deflate(data);
		writeToFile(OBJECTS_DIR + "/" + hash, data);
		return h;
	}

	@Override
	public ChatHash add(ChatText content) {
		byte[] data = serializer.serialize(content);
		HashSHA1 h = new HashSHA1(content);
		String hash = h.toString();
		data = compressor.deflate(data);
		writeToFile(OBJECTS_DIR + "/" + hash, data);
		return h;
	}

	@Override
	public ChatNode getNode(ChatHash hash) {
		assert (existsNode(hash));
		return (ChatNode) serializer
				.deserialize(compressor.inflate(getFileContents(OBJECTS_DIR + "/" + hash.toString())));
	}

	@Override
	public ChatText getText(ChatHash hash) {
		assert (existsText(hash));
		return (ChatText) serializer
				.deserialize(compressor.inflate(getFileContents(OBJECTS_DIR + "/" + hash.toString())));
	}

	@Override
	public boolean existsRef(String name) {
		return fileExists(REFS_DIR + "/" + name);
	}

	@Override
	public void setRef(String name, ChatHash hash) {
		writeToFile(REFS_DIR + "/" + name, (hash.toString() + "\n").getBytes());
	}

	@Override
	public ChatRef getRef(String name) {
		assert (existsRef(name));
		return new ChatRef(name, new HashSHA1(new String(getFileContents(REFS_DIR + "/" + name)).trim()));
	}

	@Override
	public List<String> listRefs() {
		File f = new File(REFS_DIR);
		List<String> out = new ArrayList<>();
		for (String s : f.list())
			out.add(s);
		return out;
	}

}
