package de.isibboi.bibelgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generator {
	public static String generateRandomSentence(NGramIndex index, int limit) {
		List<String> words = new ArrayList<>();

		for (int i = 0; i < index.getKeySize(); i++) {
			words.add("");
		}

		String[] type = new String[0];
		String word;
		int i = 0;

		while (!(word = index.selectRandom(words.toArray(type))).equals("") && i < limit) {
			WordSelector currentSelector = index.getWordSelector(words.toArray(type));
//			System.out.println("Current probabilities for: "+Arrays.toString(words.toArray(type))+" : " + currentSelector);
			
			words.add(word);
			i++;
		}

		words = words.subList(index.getKeySize(), words.size());

		return Loader.merge(words.toArray(type), " ");
	}
}