package de.isibboi.bibelgram;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Multiset;

public class NGramIndex implements Serializable {
	/**
	 * First version of ngram index
	 */
	private static final long serialVersionUID = 1L;

	private final Hashtable<NGram, WordSelector> _index = new Hashtable<>();
	private final List<String> _words = new ArrayList<>();
	private final int _keySize;
	private transient Random _random = new Random();

	public NGramIndex(Multiset<NGram> model) {
		int keySize = 0;
		Set<String> words = new HashSet<>();

		for (Multiset.Entry<NGram> entry : model.entrySet()) {
			NGram key = entry.getElement().removeLast();
			keySize = key._words.length;
			WordSelector value = _index.get(key);
			WordCount word = new WordCount(entry.getElement().getLast(), entry.getCount());
			words.add(entry.getElement().getLast());

			if (value == null) {
				value = new WordSelector(word);
				_index.put(key, value);
			} else {
				value.add(word);
			}
		}

		_keySize = keySize;
		_words.addAll(words);

		for (Entry<NGram, WordSelector> word : _index.entrySet()) {
			word.getValue().finishBuilding(word.getKey());
		}

		if (Bibelgram.verbose) {
			System.out.println("Generated index successfully");
		}
	}

	private NGram createKey(String[] words) {
		return new NGram(words, words.length - _keySize, _keySize);
	}

	private WordSelector getSelector(String[] words) {
		NGram key = createKey(words);
		WordSelector selector = _index.get(key);

		if (selector == null) {
			System.out.println("Unknown word sequence: " + key);
			selector = new EmptyWordSelector();
		}

		return selector;
	}

	public String selectMax(String[] words) {
		return getSelector(words).selectMax();
	}

	public String selectRandom(final String[] words, final double absoluteJitter, final double relativeJitter) {
		String word = getSelector(words).selectRandom(_random, absoluteJitter, relativeJitter);

		if (word == null) {
			word = _words.get(_random.nextInt(_words.size()));
		}

		return word;
	}

	public WordSelector getWordSelector(String[] words) {
		return getSelector(words);
	}

	public int getKeySize() {
		return _keySize;
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		_random = new Random();
	}
}