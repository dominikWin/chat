package chat;

public class UNIXTime {
	
	public static long getUNIXTime() {
		return (long) ((long) System.currentTimeMillis() / (long) 1000);
	}

}
