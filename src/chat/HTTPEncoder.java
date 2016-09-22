package chat;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class HTTPEncoder
{
	public static String httpSafe(String in) {
		return in.replace('+', '-').replace('/', '_').replaceAll("=", "");
	}

	public static String base64Reformat(String in) {
		String out = in.replace('-', '+').replace('_', '/');
		while (out.length() % 4 != 0)
			out += "=";
		return out;
	}

	public static String encode(String in) {
		return httpSafe(new String(Base64.encode(in.getBytes())));
	}

	public static String decode(String in) {
		return new String(Base64.decode(base64Reformat(in)));
	}
}
