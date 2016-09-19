package chat;

public class Main {
	public static void main(String[] args) {
		ChatContent c = new ChatContent("text");
		
		ChatSHA1 h = new  ChatSHA1(c);
		System.out.println(h);
	}
}
