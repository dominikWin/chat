package chat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import chat.data.ChatText;
import chat.data.ChatNode;
import chat.interfaces.ChatCompressor;
import chat.interfaces.ChatDatabase;
import chat.interfaces.ChatHash;
import chat.interfaces.ChatSerializer;

public class LocalDB implements ChatDatabase {

	private static final String PATH = "./";
	private static final String DATA_DIR = PATH + ".chatdata";
	private static final String OBJECTS_DIR = DATA_DIR + "/objects";
	private static final String REFS_DIR = DATA_DIR + "/refs";

	private static final String REQUIRED_DIRS[] = new String[] { DATA_DIR, OBJECTS_DIR, REFS_DIR };

	private ChatSerializer serializer;
	private ChatCompressor compressor;

	public LocalDB(ChatSerializer serializer, ChatCompressor compressor) {
		if (!verifyDir()) {
			System.out.println("Can't verify data directory");
			System.exit(1);
		}
		assert (serializer != null);
		this.serializer = serializer;
		this.compressor = compressor;
	}

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

	private void writeToFile(String path, byte[] data) {
		try {
			Files.write((new File(path).toPath()), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean fileExists(String path) {
		File f = new File(path);
		return f.exists() && f.isFile() && f.canRead();
	}

	private boolean dirExists(String path) {
		File f = new File(path);
		return f.exists() && f.isDirectory();
	}

	private boolean makeDir(String path) {
		File f = new File(path);
		return f.mkdir();
	}

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
		return fileExists(path) && serializer.deserialize(getFileContents(path)) instanceof ChatNode;
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

}
