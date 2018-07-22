package net.metasite.wordcounting.word;

import java.util.Iterator;
import java.util.List;

public class WordEditor {
	
	public List<TypedWord> sortList (List<TypedWord> list) {
    	TypedWord wordTemp = null;

    	for (int i = 0; i < list.size() - 1; i++) {
    		for (int j = i + 1; j < list.size(); j++) {
    			if ((list.get(i).getCount() < list.get(j).getCount()) || 
    				(list.get(i).getCount() == list.get(j).getCount() && list.get(i).getWord().toLowerCase().compareTo(list.get(j).getWord().toLowerCase()) >= 0)) {
    				wordTemp = list.get(i);
    				list.set(i, list.get(j));
    				list.set(j, wordTemp);
    			}
    		}
    	}
    	
    	return list;
    }
    
    public static String[] toStringArray (Iterator<TypedWord> words) {
		String[] results = {"", "", "", ""};
		while (words.hasNext()) {
			TypedWord word = words.next();
			int index = 0;
			if (word.getType() == 0) 
				index = 0;
			else if (word.getType() == 1) 
				index = 1;
			else if (word.getType() == 2) 
				index = 2;
			else if (word.getType() == 3) 
				index = 3;
			results[index] += word.toString() + "\n";
		}
		return results;
	}
}
