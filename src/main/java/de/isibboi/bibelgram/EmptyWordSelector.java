package de.isibboi.bibelgram;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class EmptyWordSelector extends WordSelector {
	public EmptyWordSelector() {
		super(Collections.EMPTY_LIST);
	}

	@Override
	public String selectMax() {
		return "";
	}
	
	@Override
	public String selectRandom(Random r) {
		return "";
	}
}