package de.isibboi.bibelgram;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Multiset;

public class NGramIndex {
	private final Hashtable<NGram, WordSelector> _index = new Hashtable<>();
	private final int _keySize;
	private final Random _random = new Random();
	
	public NGramIndex(Multiset<NGram> model) {
		int keySize = 0;
		
		for (Multiset.Entry<NGram> entry : model.entrySet()) {
			NGram key = entry.getElement().removeLast();
			keySize = key._words.length;
			WordSelector value = _index.get(key);
			WordCount word = new WordCount(entry.getElement().getLast(), entry.getCount());
			
			if (value == null) {
				value = new WordSelector(word);
				_index.put(key, value);
			} else {
				value.add(word);
			}
		}
		
		_keySize = keySize;
		
		for (Entry<NGram, WordSelector> word : _index.entrySet()) {
			word.getValue().finishBuilding(word.getKey());
		}
		
		System.out.println("Generated index successfully");
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
	
	public String selectRandom(String[] words) {
		return getSelector(words).selectRandom(_random);
	}
	
	public WordSelector getWordSelector(String[] words) {
		return getSelector(words);
	}
	
	public int getKeySize() {
		return _keySize;
	}
}