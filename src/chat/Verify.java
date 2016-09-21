package chat;

public class Verify {
	public static boolean goodSHA1(String s) {
		if (s.length() != 40)
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')))
				return false;
		}

		return true;
	}
}
