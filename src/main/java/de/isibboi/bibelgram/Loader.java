package de.isibboi.bibelgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Loader {
	public static String merge(String[] parts, String inbetween) {
		if (parts.length == 0) {
			return "";
		}

		StringBuilder str = new StringBuilder(parts[0]);

		for (int i = 1; i < parts.length; i++) {
			str.append(inbetween).append(parts[i]);
		}

		return str.toString();
	}

	public static Collection<String[]> loadBible(final String path) {
		List<String> lines = new ArrayList<>();

		try (InputStream f = Loader.class.getResourceAsStream(path);) {
			BufferedReader in = new BufferedReader(new InputStreamReader(f, "iso-8859-1"));
			String line;

			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		if (Bibelgram.verbose) {
			System.out.println("Read " + lines.size() + " lines");
		}

		StringBuilder allLines = new StringBuilder();

		lines.stream().map((String s) -> {
			int index = s.indexOf(' ', 0);
			index = s.indexOf(' ', index + 1);
			return s.substring(index + 1);
		}).forEach((String s) -> {
			allLines.append(s).append(' ');
		});

		if (Bibelgram.verbose) {
			System.out.println("Filtered lines size : " + allLines.toString().length());
		}

		String[] sentences = allLines.toString().split("[\\.!?]");
		List<String[]> result = new ArrayList<>(sentences.length);

		if (Bibelgram.verbose) {
			System.out.println("Created " + sentences.length + " sentences");
		}

		for (String sentence : sentences) {
			String source = sentence;
			sentence = merge(sentence.split(";"), " ;");
			sentence = merge(sentence.split(","), " ,");
			sentence = merge(sentence.split(":"), " :");
			sentence = merge(sentence.split("\\)"), " ");
			sentence = merge(sentence.split("\\("), " ");
			sentence = merge(sentence.split("\u0084"), " ");
			sentence = merge(sentence.split("\u0094"), " ");
			sentence = sentence.trim();
			String[] words = sentence.split("\\s+");

			if (words.length == 0 || words[0].equals("")) {
				continue;
			}

			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].charAt(0) + words[i].substring(1).toLowerCase();
			}

			result.add(words);

			if (words[0].equals("")) {
				System.out.println("Created sentence: " + Arrays.toString(words) + " from: " + source);
			}
		}

		if (Bibelgram.verbose) {
			System.out.println("Loader loaded " + result.size() + " bible sentences");
		}

		return result;
	}

	public static Collection<String[]> loadWhatsApp(final String path) {
		StringBuilder source = new StringBuilder();

		try (InputStream f = Loader.class.getResourceAsStream(path);) {
			BufferedReader in = new BufferedReader(new InputStreamReader(f, "utf-8"));
			String line;

			while ((line = in.readLine()) != null) {
				source.append("\n").append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String sourceString = source.toString();
		sourceString = sourceString.replace('?', ' ');
		sourceString = sourceString.replace('!', ' ');
		sourceString = sourceString.replace('.', ' ');
		sourceString = sourceString.replace(',', ' ');
		sourceString = sourceString.replaceAll("(?<name>[\\sa-zA-Z0-9])\\)", "\\k<name>");
		sourceString = sourceString.replaceAll("(?<name>\\([\\sa-zA-Z0-9])", "\\k<name>");
		
		String[] messages = sourceString.split("\"\n\"");
		
		if (Bibelgram.verbose) {
			System.out.println("Loaded " + messages.length + " WhatsApp messages");
		}
		
		messages[0] = messages[0].substring(2);
		messages[messages.length - 1] = messages[messages.length - 1].substring(0, messages[messages.length - 1].length() - 1);
		
		List<String[]> result = new ArrayList<>(messages.length);
		
		for (String message : messages) {
			if (message.startsWith("BEGIN:VCARD")) {
				continue;
			}
			
			String[] words = message.split("\\s+");
			
			if (words.length == 0) {
				continue;
			}
			
			result.add(words);
		}
		
		return result;
	}
}