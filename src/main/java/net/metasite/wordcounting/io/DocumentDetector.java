package net.metasite.wordcounting.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class DocumentDetector {
	protected BodyContentHandler handler = null;
	protected Metadata metadata = null;
	protected InputStream inputstream = null;
	protected ParseContext pcontext = null;
	
	public void detect (InputStream fileInputStream) throws FileNotFoundException {
		//detecting the file type
	    handler = new BodyContentHandler(-1);
	    metadata = new Metadata();
	    inputstream = fileInputStream;
	    pcontext = new ParseContext();
	}
}
