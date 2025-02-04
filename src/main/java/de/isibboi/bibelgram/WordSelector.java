package de.isibboi.bibelgram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordSelector implements Serializable {
	/**
	 * First WordSelector version.
	 */
	private static final long serialVersionUID = 1L;
	
	private int _sum;
	private final List<WordCount> _wordCounts = new ArrayList<>();

	public WordSelector(Collection<WordCount> words) {
		_sum = 0;

		for (WordCount word : words) {
			_sum += word._count;
			_wordCounts.add(word);
		}
	}

	public WordSelector(WordCount word) {
		_sum = word._count;
		_wordCounts.add(word);
	}

	public void add(WordCount word) {
		_sum += word._count;
		_wordCounts.add(word);
	}

	public void finishBuilding(NGram source) {
		Collections.sort(_wordCounts);

		// int n = Math.min(3, _wordCounts.size());
		// System.out.println("The " + n + " most common words after " + source
		// + " are: "
		// + Arrays.toString(_wordCounts.subList(0, n).toArray()));
	}

	public String selectMax() {
		return _wordCounts.get(0)._word;
	}

	public String selectRandom(Random r, final double absoluteJitter, final double relativeJitter) {
		double rn = r.nextDouble() * (_sum + absoluteJitter) * (1 + relativeJitter);

		if (rn >= _sum) {
			return _wordCounts.get(r.nextInt(_wordCounts.size()))._word;
		}

		int index = (int) rn;
		boolean once = true;

		// System.out.println("Using index: " + index + " mod " + _sum);

		for (WordCount word : _wordCounts) {
			index -= word._count;

			if (index < 0) {
				return word._word;
			}

			if (once) {
				// System.out.println("Didn't return first word!");
				once = false;
			}
		}

		throw new RuntimeException("This cannot happen");
	}

	@Override
	public String toString() {
		return Arrays.toString(_wordCounts.toArray());
	}
}