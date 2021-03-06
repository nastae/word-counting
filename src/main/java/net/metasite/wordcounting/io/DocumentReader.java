package net.metasite.wordcounting.io;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class DocumentReader {

	protected AbstractParser parser;

	protected BodyContentHandler handler;
	protected Metadata metadata = new Metadata();
	protected ParseContext pcontext = new ParseContext();

	public DocumentReader(AbstractParser parser) {
		this.parser = parser;
	}

	public synchronized Optional<String> read(InputStream inputstream) {
		handler = new BodyContentHandler(-1);
		String result = "";
		try {
            parser.parse(inputstream, handler, metadata, pcontext);
            result = handler.toString();
		} catch (IOException | SAXException | TikaException e) {
			return Optional.empty();
		}
		return Optional.ofNullable(result);
	}
}