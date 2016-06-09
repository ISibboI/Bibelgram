package de.isibboi.bibelgram;

import java.util.Collections;
import java.util.Random;

public class EmptyWordSelector extends WordSelector {
	/**
	 * First EmtpyWordSelector version.
	 */
	private static final long serialVersionUID = 1L;

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