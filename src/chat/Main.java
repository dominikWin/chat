package chat;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ChatContent c = new ChatContent(new Scanner(System.in).nextLine());
		
		ChatSHA1 h = new  ChatSHA1(c);
		System.out.println(h);
	}
}
