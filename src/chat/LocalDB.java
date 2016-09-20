package chat;

import java.io.File;

import chat.data.ChatContent;
import chat.data.ChatNode;
import chat.interfaces.ChatDatabase;
import chat.interfaces.ChatHash;
import chat.interfaces.ChatSerializer;

public class LocalDB implements ChatDatabase {

	private static final String PATH = "./";
	private static final String DATA_DIR = PATH + ".chatdata";
	private static final String OBJECTS_DIR = DATA_DIR + "/objects";
	
	private static final String REQUIRED_DIRS[] = new String[] {DATA_DIR, OBJECTS_DIR};
	
	private ChatSerializer serializer;
	
	public LocalDB(ChatSerializer serializer) {
		if(!verifyDir()) {
			System.out.println("Can't verify data directory");
			System.exit(1);
		}
		assert(serializer != null);
		this.serializer = serializer;
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
		for(String path : REQUIRED_DIRS) {
			if(!dirExists(path)) {
				System.out.println("Creating dir " + path);
				if(!makeDir(path)) {
					System.err.println("Can't make dir " + path);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean existsNode(ChatHash hash) {
		
	}

	@Override
	public boolean existsContent(ChatHash hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(ChatNode node) {
		
	}

	@Override
	public void add(ChatContent content) {
		
	}

}
