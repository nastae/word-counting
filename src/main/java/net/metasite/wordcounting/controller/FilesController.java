package net.metasite.wordcounting.controller;

import net.metasite.wordcounting.counting.WordCounting;
import net.metasite.wordcounting.word.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "file")
public class FilesController {

	@Autowired
	private WordCounting counting;

	private final HashMap<String, Set<Word>> wordsGroup = new HashMap<>();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewHome() {
		wordsGroup.clear();
		return "index";
	}
	@RequestMapping(value = "/convert", method = RequestMethod.POST)
	public String convertWords(@ModelAttribute("files") Files upload) {
		wordsGroup.clear();
		wordsGroup.putAll(counting.countWordsFromFiles(upload.files));
		return "index";
	}

	@ModelAttribute(name = "words")
	public HashMap<String, Set<Word>> words() {
		return wordsGroup;
	}

	@ModelAttribute(name = "files")
	public Files files() {
		return new Files();
	}

	class Files {
		private List<MultipartFile> files = new ArrayList<>();

		public List<MultipartFile> getFiles() {
			return files;
		}

		public void setFiles(List<MultipartFile> files) {
			this.files = files;
		}
	}
}
