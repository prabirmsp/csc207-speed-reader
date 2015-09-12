import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class WordGenerator {

	private String filename;
	private Scanner fileScanner;
	private int wordCount, sentenceCount;

	public WordGenerator (String filename) throws IOException {
		this.filename = filename;
		this.fileScanner = new Scanner(new File(filename));
		
	}

	public boolean hasNext(){
		return fileScanner.hasNext();
	}
	
	public String next(){
		String word = fileScanner.next();
		if (word.contains(".") || word.contains("!") || word.contains("?"))
			sentenceCount++;
		wordCount++;
		return word;
	}
	
	public int getWordCount(){
		return wordCount;
	}
	
	public int getSentenceCount(){
		return sentenceCount;
	}
}

