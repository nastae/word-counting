package net.metasite.wordcounting.io;

import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.parser.xml.XMLParser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DocumentReaderFactory {

	public Optional<DocumentReader> getDocumentReader (String fileName) {
		if (fileName.endsWith(".pdf"))
			return Optional.of(new DocumentReader(new PDFParser()));
		else if (fileName.endsWith(".odt") || fileName.endsWith(".ods") || fileName.endsWith(".odp"))
			return Optional.of(new DocumentReader(new OpenDocumentParser()));
		else if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".xls"))
			return Optional.of(new DocumentReader(new OOXMLParser()));
		else if (fileName.endsWith(".txt"))
			return Optional.of(new DocumentReader(new TXTParser()));
		else if (fileName.endsWith(".html"))
			return Optional.of(new DocumentReader(new HtmlParser()));
		else if (fileName.endsWith(".xml"))
			return Optional.of(new DocumentReader(new XMLParser()));
		else if (fileName.endsWith(".epub"))
			return Optional.of(new DocumentReader(new EpubParser()));
		return Optional.empty();
	}
}
