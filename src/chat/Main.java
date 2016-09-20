package chat;

import java.util.Scanner;

import chat.data.ChatContent;

public class Main {
	public static void main(String[] args) {
		ChatContent c = new ChatContent(new Scanner(System.in).nextLine());
		
		HashSHA1 h = new  HashSHA1(c);
		System.out.println(h);
	}
}
