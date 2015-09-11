import static org.junit.Assert.*;

import org.junit.Test;


public class WordGeneratorTest {

	@Test
	public void getWordCountTest() throws Exception {
		WordGenerator suess = new WordGenerator("dr-suess.txt");
		while (suess.hasNext())
			suess.next();
		assertEquals(14, suess.getWordCount());
	}

	@Test
	public void getSentenceCountTest() throws Exception {
		WordGenerator suess = new WordGenerator("dr-suess.txt");
		while (suess.hasNext())
			suess.next();
		assertEquals(4, suess.getSentenceCount());
	}

}
