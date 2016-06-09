package de.isibboi.bibelgram;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class NGramModel {
	private static class NGram {
		String[] _words;

		NGram(final String[] source, final int offset, final int n) {
			_words = new String[n];

			for (int i = offset; i < offset + n; i++) {
				_words[i - offset] = source[i];
			}
		}

		@Override
		public boolean equals(final Object o) {
			if (o instanceof NGram) {
				NGram n = (NGram) o;
				return Arrays.equals(_words, n._words);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(_words);
		}
	}

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
		for (int i = 0; i < sentence.length - _n; i++) {
			NGram ngram = new NGram(sentence, i, _n);
			_model.add(ngram);
		}
	}
	
	/**
	 * Trains the model with the given sentences.
	 * @param sentences A collection of sentences.
	 */
	public void train(final Collection<String[]> sentences) {
		for (String[] sentence : sentences) {
			train(sentence);
		}
	}
}