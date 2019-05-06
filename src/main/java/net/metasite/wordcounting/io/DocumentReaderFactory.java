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

	private DocumentReader epubDocumentReader = new DocumentReader(new EpubParser());
	private DocumentReader xmlDocumentReader = new DocumentReader(new XMLParser());
	private DocumentReader htmlDocumentReader = new DocumentReader(new HtmlParser());
	private DocumentReader textDocumentReader = new DocumentReader(new TXTParser());
	private DocumentReader msOfficeDocumentReader = new DocumentReader(new OOXMLParser());
	private DocumentReader odfDocumentReader = new DocumentReader(new OpenDocumentParser());
	private DocumentReader pdfDocumentReader = new DocumentReader(new PDFParser());

	public Optional<DocumentReader> getDocumentReader (String fileName) {
		if (fileName.endsWith(".pdf"))
			return Optional.ofNullable(pdfDocumentReader);
		else if (fileName.endsWith(".odt") || fileName.endsWith(".ods") || fileName.endsWith(".odp"))
			return Optional.ofNullable(odfDocumentReader);
		else if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".xls"))
			return Optional.ofNullable(msOfficeDocumentReader);
		else if (fileName.endsWith(".txt"))
			return Optional.ofNullable(textDocumentReader);
		else if (fileName.endsWith(".html"))
			return Optional.ofNullable(htmlDocumentReader);
		else if (fileName.endsWith(".xml"))
			return Optional.ofNullable(xmlDocumentReader);
		else if (fileName.endsWith(".epub"))
			return Optional.ofNullable(epubDocumentReader);
		
		return Optional.empty();
	}
}
