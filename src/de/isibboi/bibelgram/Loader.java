package de.isibboi.bibelgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
	public static String merge(String[] parts, String inbetween) {
		StringBuilder str = new StringBuilder(parts[0]);
		
		for (int i = 1; i < parts.length; i++) {
			str.append(inbetween).append(parts[i]);
		}
		
		return str.toString();
	}
	
	public static Collection<String[]> loadBibel(final String path) {
		File f = new File(path);
		List<String> lines = new ArrayList<>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
			String line;
			
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		StringBuilder allLines = new StringBuilder();
		
		lines.stream().map((String s) -> {
			int index = s.indexOf(' ', 0);
			index = s.indexOf(' ', index + 1);
			return s.substring(index + 1);
		}).forEach((String s) -> {
			allLines.append(s).append(' ');
		});
		
		String[] sentences = allLines.toString().split(".");
		Collection<String[]> result = new ArrayList<>(sentences.length);
		
		for (String sentence :sentences) {
			sentence = merge(sentence.split(";"), " ;");
			sentence = merge(sentence.split(","), " ,");
			result.add(sentence.split(" "));
		}
		
		return result;
	}
}