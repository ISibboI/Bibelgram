package de.isibboi.bibelgram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordSelector {
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
	
	public void finishBuilding() {
		Collections.sort(_wordCounts);
	}
	
	public String selectMax() {
		return _wordCounts.get(0)._word;
	}
	
	public String selectRandom(Random r) {
		int index = r.nextInt(_sum);
		
		for (WordCount word : _wordCounts) {
			index -= word._count;
			
			if (index < 0) {
				return word._word;
			}
		}
		
		throw new RuntimeException("This cannot happen");
	}
}