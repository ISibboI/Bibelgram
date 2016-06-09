package de.isibboi.bibelgram;

import java.util.Arrays;


public class NGram {
	String[] _words;

	NGram(final String[] source, final int offset, final int n) {
		_words = new String[n];
		int i = offset;
		
		for (; i < 0 && i < offset + n; i++) {
			_words[i - offset] = "";
		}
		
		for (; i < n && i < offset + n; i++) {
			_words[i - offset] = source[i];
		}
		
		for (; i < offset + n; i++) {
			_words[i - offset] = "";
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

	public String getLast() {
		return _words[_words.length - 1];
	}
}