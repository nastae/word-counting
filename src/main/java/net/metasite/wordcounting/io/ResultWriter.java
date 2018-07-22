package net.metasite.wordcounting.io;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ResultWriter {
	private static Writer writer = null;
	
	public void writeFile (String fileName, String result) {
		
		try {
			writer = new FileWriter(fileName);
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
