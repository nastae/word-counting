package net.metasite.wordcounting.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.xml.sax.SAXException;

public class MSOfficeDocumentReader extends DocumentDetector implements DocumentReader {

	@Override
	public String read(InputStream fileInputStream) {
		String result = "";
		
		try {
			super.detect(fileInputStream);
			
			OOXMLParser  msofficeparser = new OOXMLParser(); 
		    msofficeparser.parse(inputstream, handler, metadata,pcontext);
		    
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