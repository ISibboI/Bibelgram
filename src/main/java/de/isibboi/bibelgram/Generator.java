package de.isibboi.bibelgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generator {
	public static String generateRandomSentence(NGramIndex index, int limit, double absoluteJitter,
			double relativeJitter, String[] prefix) {
		List<String> words = new ArrayList<>();

		for (int i = 0; i < index.getKeySize(); i++) {
			words.add("");
		}
		
		words.addAll(Arrays.asList(prefix));

		String[] type = new String[0];
		String word;
		int i = prefix.length;

		while (!(word = index.selectRandom(words.toArray(type), absoluteJitter, relativeJitter)).equals("")
				&& i < limit) {
			if (Bibelgram.verbose) {
				WordSelector currentSelector = index.getWordSelector(words.toArray(type));
				System.out.println(
						"Current probabilities for:" + Arrays.toString(words.toArray(type)) + " : " + currentSelector);
			}

			words.add(word);
			i++;
		}

		words = words.subList(index.getKeySize(), words.size());

		String result = Loader.merge(words.toArray(type), " ");
		result = result.replace(" ;", ";");
		result = result.replace(" :", ":");
		result = result.replace(" ,", ",");
		result = result + ".";
	
		return result;
	}
}