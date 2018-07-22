package net.metasite.wordcounting.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import net.metasite.wordcounting.word.*;
import net.metasite.wordcounting.files.Files;
import net.metasite.wordcounting.io.DocumentReader;
import net.metasite.wordcounting.io.DocumentReaderFactory;
import net.metasite.wordcounting.io.ResultWriter;
import net.metasite.wordcounting.thread.WordCountingThread;

@Controller
public class FilesController {
	
	private WordEditor wordEditor = new WordEditor();
	
	private final static String[] RESULT_FILE_NAMES = {"results1.txt", "results2.txt", "results3.txt", "results4.txt"};
	private final static String RESULTS_PATH = "results\\";
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHome (Model model) {
		return "home";
	} 
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inputFiles (Model model) {
		model.addAttribute("files", new Files());
		return "index";
	} 
	
    @RequestMapping("/")
    public String uploadResources( HttpServletRequest servletRequest,
                                 @ModelAttribute Files files,
                                 Model model) {
    	Set<TypedWord> words = Collections.synchronizedSet(new HashSet<TypedWord>());
    	
        //Get the uploaded files and store them
        List<MultipartFile> uploadedFiles = files.getFiles();
        List<String> wrongFormatFiles = new ArrayList<String>();
        if (null != uploadedFiles && uploadedFiles.size() > 0)
        {
        	ExecutorService executor = Executors.newFixedThreadPool(files.getFiles().size());
            for (MultipartFile multipartFile : uploadedFiles) {

            	DocumentReaderFactory documentReaderFactory = new DocumentReaderFactory();
            	DocumentReader documentReader = documentReaderFactory.getDocumentReader(multipartFile.getOriginalFilename());
            	
            	if (documentReader != null) {
            		try {
    					executor.submit(new Thread(new WordCountingThread(documentReader, multipartFile.getInputStream(), words)));
    				} catch (IOException e) {
    					System.out.println(e.getMessage());
    				}
            	} else {
            		wrongFormatFiles.add(multipartFile.getOriginalFilename());
            	}
            	

            }
            executor.shutdown();
    		while (!executor.isTerminated());

        }

        String[] results = wordEditor.toStringArray(words.iterator());
        ResultWriter resultWriter = new ResultWriter();
        resultWriter.writeFile(servletRequest.getServletContext().getRealPath("/results/") + RESULT_FILE_NAMES[0], results[0]);
        resultWriter.writeFile(servletRequest.getServletContext().getRealPath("/results/") + RESULT_FILE_NAMES[1], results[1]);
        resultWriter.writeFile(servletRequest.getServletContext().getRealPath("/results/") + RESULT_FILE_NAMES[2], results[2]);
        resultWriter.writeFile(servletRequest.getServletContext().getRealPath("/results/") + RESULT_FILE_NAMES[3], results[3]);

        model.addAttribute("words", wordEditor.sortList(new ArrayList<TypedWord>(words)));
        return "index";
    }

}
