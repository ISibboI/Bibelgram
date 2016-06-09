package de.isibboi.bibelgram;

import java.util.Collections;
import java.util.Random;

public class EmptyWordSelector extends WordSelector {
	public EmptyWordSelector() {
		super(Collections.emptyList());
	}

	@Override
	public String selectMax() {
		return null;
	}
	
	@Override
	public String selectRandom(Random r, double absoluteJitter, double relativeJitter) {
		return null;
	}
}