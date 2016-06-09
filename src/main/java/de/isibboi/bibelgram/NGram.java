package de.isibboi.bibelgram;

import java.util.Arrays;

public class NGram {
	String[] _words;

	NGram(final String[] source, final int offset, final int n) {
		_words = new String[n];
		int i = 0;
		boolean empty = true;

		for (; i + offset < 0; i++) {
			_words[i] = "";
		}

		for (; i < n && i + offset < source.length; i++) {
			_words[i] = source[i + offset];
			empty = false;
		}

		for (; i < n; i++) {
			_words[i] = "";
		}

		if (empty) {
			System.out.println(
					"Created empty ngram with parameters: " + Arrays.toString(source) + "; " + offset + "; " + n);
		}
	}

	public NGram removeLast() {
		return new NGram(_words, 0, _words.length - 1);
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

	@Override
	public String toString() {
		return Arrays.toString(_words);
	}

	public String getLast() {
		return _words[_words.length - 1];
	}
}