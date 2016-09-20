package chat;

import chat.interfaces.ChatCompressor;
import java.util.zip.*;

public class CompressZLIB implements ChatCompressor
{

	@Override
	public byte[] deflate(byte[] data)
	{
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		int writen;
		byte buffer[] = new byte[2048];
		writen = deflater.deflate(buffer);
		deflater.end();
		if (writen >= 2048)
			System.err.println("To large compressed message");
		byte out[] = new byte[writen];
		for(int i = 0; i < writen; i++)
			out[i] = buffer[i];
		return out;
	}

	@Override
	public byte[] inflate(byte[] data)
	{
		Inflater inflater = new Inflater();
		inflater.setInput(data);

		int writen = 0;
		byte buffer[] = new byte[2048];
		try
		{
			writen = inflater.inflate(buffer);
		} catch (DataFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inflater.end();
		if (writen >= 2048)
			System.err.println("To large decompressed message");
		byte out[] = new byte[writen];
		for(int i = 0; i < writen; i++)
			out[i] = buffer[i];
		return out;
	}

}
