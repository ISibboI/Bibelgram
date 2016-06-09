package de.isibboi.bibelgram;

import java.util.ArrayList;
import java.util.List;

public class Generator {
	public static String generateRandomSentence(NGramIndex index) {
		List<String> words = new ArrayList<>();

		for (int i = 0; i < index.getKeySize(); i++) {
			words.add("");
		}

		String[] type = new String[0];
		String word;

		while (!(word = index.selectMax(words.toArray(type))).equals("")) {
			words.add(word);
		}

		words = words.subList(index.getKeySize(), words.size());

		return Loader.merge(words.toArray(type), " ");
	}
}