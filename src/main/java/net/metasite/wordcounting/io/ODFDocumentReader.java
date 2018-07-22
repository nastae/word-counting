package net.metasite.wordcounting.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.xml.sax.SAXException;

public class ODFDocumentReader extends DocumentDetector implements DocumentReader {

	@Override
	public String read(InputStream fileInputStream) {
		String result = "";

		try {
			super.detect(fileInputStream);
			
			OpenDocumentParser openofficeparser = new OpenDocumentParser (); 
		    openofficeparser.parse(inputstream, handler, metadata,pcontext);
		    
		    result = handler.toString();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (TikaException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

}
