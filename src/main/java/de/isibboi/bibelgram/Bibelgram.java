package de.isibboi.bibelgram;

import java.util.Collection;

public class Bibelgram {
	public static void main(String[] args) {
		Collection<String[]> bibel = Loader.loadBibel("bibel.txt");
		NGramModel model = new NGramModel(4); // Create bigram model
		model.train(bibel);
		NGramIndex index = model.buildIndex(); // Create index

		for (int i = 0; i < 10; i++) {
			System.out.println("Random sentence: " + Generator.generateRandomSentence(index, 100));
		}

		// WordSelector firstWord = index.getWordSelector(new String[]{""});
		// System.out.println("First word probabilities: " + firstWord);
	}
}