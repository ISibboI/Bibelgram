package de.isibboi.bibelgram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Bibelgram {
	public static boolean verbose = false;

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", "create", false, "Create the model");
		options.addOption("g", "generate", false, "Generate the model");
		options.addOption("n", true, "Create an n-gram model (default 4)");
		options.addOption("b", true, "Path to book (default bibel.txt)");
		options.addOption("i", true, "Path to index (default bibel.ind)");
		options.addOption("a", true, "Absolute jitter (default 0)");
		options.addOption("r", true, "Relative jitter (default 0)");
		options.addOption("l", true, "Maximum sentence length (default 100)");
		options.addOption("s", true, "Generation count (default 10)");
		options.addOption("t", true, "Maximum tries per sentence (default 10)");
		options.addOption("u", false, "Don't output cutoff sentences");
		options.addOption("v", false, "Verbose (default false)");
		options.addOption("h", "help", false, "Prints this help message");
		options.addOption("p", true, "The prefix");

		CommandLineParser parser = new DefaultParser();
		CommandLine line;
		
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			printHelp(options);
			e.printStackTrace();
			return;
		}

		boolean createIndex = false;
		boolean generateSentences = false;
		int n = 4;
		String pathToBook = "bibel.txt";
		String pathToIndex = "bibel.ind";
		double absoluteJitter = 0;
		double relativeJitter = 0;
		int maxLength = 100;
		int generationCount = 10;
		int maxTries = 10;
		boolean outputCutoffSentences = true;
		String prefix = "";

		Iterator<Option> iter = line.iterator();
		Option current;
		while (iter.hasNext()) {
			current = iter.next();

			switch (current.getOpt()) {
			case "c":
				createIndex = true;
				break;
			case "g":
				generateSentences = true;
				break;
			case "n":
				n = Integer.parseInt(current.getValue());
				break;
			case "b":
				pathToBook = current.getValue();
				break;
			case "i":
				pathToIndex = current.getValue();
				break;
			case "a":
				absoluteJitter = Double.parseDouble(current.getValue());
				break;
			case "r":
				relativeJitter = Double.parseDouble(current.getValue());
				break;
			case "l":
				maxLength = Integer.parseInt(current.getValue());
				break;
			case "s":
				generationCount = Integer.parseInt(current.getValue());
				break;
			case "t":
				maxTries = Integer.parseInt(current.getValue());
				break;
			case "u":
				outputCutoffSentences = false;
				break;
			case "v":
				verbose = true;
				break;
			case "h":
				printHelp(options);
				break;
			case "p":
				prefix = current.getValue();
				break;
			default:
				throw new RuntimeException("Unrecognized option: " + current.getOpt());
			}
		}

		if (!createIndex && !generateSentences) {
			System.out.println("No action given");
			return;
		}

		NGramIndex index;

		if (createIndex) {
			Collection<String[]> book = Loader.loadBible(pathToBook);
			NGramModel model = new NGramModel(n); // Create bigram model
			model.train(book);
			index = model.buildIndex(); // Create index

			File f = new File(pathToIndex);
			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f))) {
				out.writeObject(index);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else {
			if (verbose) {
				System.out.println("Loading index...");
			}

			try (ObjectInputStream in = new ObjectInputStream(Bibelgram.class.getResourceAsStream(pathToIndex))) {
				index = (NGramIndex) in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		if (generateSentences) {
			if (verbose) {
				System.out.println("Generating sentences...");
			}

			for (int i = 0; i < generationCount; i++) {
				String sentence = null;
				boolean success = false;

				for (int t = 0; t < maxTries; t++) {
					sentence = Generator.generateRandomSentence(index, maxLength, absoluteJitter, relativeJitter, prefix.split("\\s+"));
					if (sentence.split(" |\\.[;:,]").length == maxLength) {
						continue;
					} else {
						System.out.println(sentence);
						success = true;
						break;
					}
				}

				if (!success && outputCutoffSentences && sentence != null) {
					System.out.println(sentence);
				}
			}
		}

		// WordSelector firstWord = index.getWordSelector(new String[]{""});
		// System.out.println("First word probabilities: " + firstWord);
	}
	
	private static void printHelp(Options o) {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("java -jar JARNAME <-c|-g> [args...]", o);
	}
}