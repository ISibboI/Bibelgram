package de.isibboi.bibelgram;

import java.util.Collection;

public class Bibelgram {
	public static void main(String[] args) {
		Collection<String[]> bibel = Loader.loadBibel("bibel.txt");
		NGramModel model = new NGramModel(2); // Create bigram model
		model.train(bibel);
		NGramIndex index = model.buildIndex(); // Create index
		
		System.out.println("Random sentence: " + Generator.generateRandomSentence(index));
	}
}