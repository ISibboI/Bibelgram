package de.isibboi.bibelgram;

import java.io.Serializable;

public class WordCount implements Comparable<WordCount>, Serializable {
	/**
	 * First WordCount version.
	 */
	private static final long serialVersionUID = 1L;
	
	final String _word;
	final int _count;

	WordCount(final String word, final int count) {
		_word = word;
		_count = count;
	}

	@Override
	public int compareTo(final WordCount o) {
		return o._count - _count;
	}

	@Override
	public String toString() {
		return _word + " (" + _count + ")";
	}
}
