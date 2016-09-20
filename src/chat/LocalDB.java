package chat;

import java.io.File;

import chat.data.ChatContent;
import chat.data.ChatNode;
import chat.interfaces.ChatDatabase;
import chat.interfaces.ChatHash;

public class LocalDB implements ChatDatabase {
	
	private static final String PATH = "./";
	private static final String DIR_NAME = ".chatdata";
	private static final String OBJECT_DIR_NAME = "objects";
	
	private boolean dirExists(String path) {
		File f = new File(path);
		return f.exists() && f.isDirectory();
	}
	
	private boolean dataDirExists() {
		return dirExists(PATH + DIR_NAME);
	}

	@Override
	public boolean existsNode(ChatHash hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsContent(ChatHash hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(ChatNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(ChatContent content) {
		// TODO Auto-generated method stub

	}

}
