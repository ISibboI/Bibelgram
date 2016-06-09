package de.isibboi.bibelgram;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class NGramModel {
	private final Multiset<NGram> _model = HashMultiset.create();
	private final int _n;

	public NGramModel(final int n) {
		_n = n;
	}

	/**
	 * Trains the model with the given sentence.
	 * 
	 * @param sentence
	 *            An array of words.
	 */
	public void train(final String[] sentence) {
		for (int i = -_n + 1; i < sentence.length; i++) {
			NGram ngram = new NGram(sentence, i, _n);
			_model.add(ngram);
		}
	}
	
	/**
	 * Trains the model with the given sentences.
	 * @param sentences A collection of sentences.
	 */
	public void train(final Collection<String[]> sentences) {
		System.out.println("Training model...");
		
		for (String[] sentence : sentences) {
			train(sentence);
		}
		
		System.out.println("Trained model successfully");
	}
	
	public NGramIndex buildIndex() {
		return new NGramIndex(_model);
	}
}