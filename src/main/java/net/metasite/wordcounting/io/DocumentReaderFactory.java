package net.metasite.wordcounting.io;

public class DocumentReaderFactory {

	public DocumentReader getDocumentReader (String fileName) {
		if (fileName.endsWith(".pdf"))
			return new PDFDocumentReader();
		else if (fileName.endsWith(".odt") || fileName.endsWith(".ods") || fileName.endsWith(".odp"))
			return new ODFDocumentReader();
		else if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".xls"))
			return new MSOfficeDocumentReader();
		else if (fileName.endsWith(".txt"))
			return new TextDocumentReader();
		else if (fileName.endsWith(".html"))
			return new HTMLDocumentReader();
		else if (fileName.endsWith(".xml"))
			return new XMLDocumentReader();
		else if (fileName.endsWith(".epub"))
			return new XMLDocumentReader();
		
		return null;
	}
}
