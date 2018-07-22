package net.metasite.wordcounting.io;

import java.io.InputStream;

public interface DocumentReader {
	public String read(InputStream fileInputStream);
}
