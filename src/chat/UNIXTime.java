package chat;

/**
 * Class with static method to return UNIX time as {@code long}.
 */
public class UNIXTime {

	/**
	 * @return UNIX time.
	 */
	public static long getUNIXTime() {
		return (long) ((long) System.currentTimeMillis() / (long) 1000);
	}

}
