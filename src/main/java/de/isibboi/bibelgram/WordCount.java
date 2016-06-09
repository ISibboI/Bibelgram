package de.isibboi.bibelgram;

public class WordCount implements Comparable<WordCount> {
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
}
