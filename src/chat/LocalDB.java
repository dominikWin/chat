package chat;

import java.io.File;

import chat.data.ChatContent;
import chat.data.ChatNode;
import chat.interfaces.ChatDatabase;

public class LocalDB implements ChatDatabase {
	
	private static final String PATH = "./";
	private static final String DIR_NAME = ".chatdata";
	private static final String OBJECT_DIR_NAME = "objects";
	
	private boolean dataDirExists() {
		File f = new File(PATH + DIR_NAME);
		return f.exists() && f.isDirectory();
	}

	@Override
	public boolean existsNode(String hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsContent(String hash) {
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
