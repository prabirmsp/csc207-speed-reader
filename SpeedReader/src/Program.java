import java.awt.*;
import java.awt.geom.Line2D;

public class Program {

	/**
	 * Main calls the WordGenerator class to read a .txt file 
	 * and after displaying each word of the text file, reports the number of words 
	 * and number of sentences it processed.
	 * @param args
	 * @throws Exception
	 * citation: We leanrt how to make lines from stackoverflow:
	 * <http://stackoverflow.com/questions/10767265/drawing-a-line-on-a-jframe>
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(
				"Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");

		// checks given arguments
		if (args.length < 4){
			System.out.println("Not enough arguements!!");
			return ;
		}
		// parse the given arguments
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int fontSize = Integer.parseInt(args[3]);
		int wpm = Integer.parseInt(args[4]);
		int millsPerWord = 1000 * 60 / wpm;

		WordGenerator generator = new WordGenerator(args[0]);

		// set up the DrawingPanel
		DrawingPanel panel = new DrawingPanel(width, height);
		Graphics g = panel.getGraphics();
		Font f = new Font("Courier", Font.BOLD, fontSize);
		Font infoFont = new Font("Arial", Font.ITALIC, fontSize / 3);
		Font logoFont = new Font("Arial", Font.BOLD, fontSize );
		g.setFont(f);

		// measurement specs for drawing on the panel
		FontMetrics fontMetrics = g.getFontMetrics();
		int letterWidth = fontMetrics.charWidth(' ');
		int focusLetterStart = 3 * width / 8;
		int topLineHeight = height / 2 - 2 * letterWidth;
		int bottomLineHeight = height / 2 + letterWidth;
		int focusLineStart = (int) (focusLetterStart + 0.5 * letterWidth);
		Line2D topLine = new Line2D.Float(
				width / 12, 
				topLineHeight, 
				11 * width / 12, 
				topLineHeight);
		Line2D bottomLine = new Line2D.Float(
				width / 12 , 
				bottomLineHeight, 
				11 * width / 12, 
				bottomLineHeight);
		Line2D topFocusLine = new Line2D.Float(
				focusLineStart, 
				topLineHeight, 
				focusLineStart, 
				(int) (topLineHeight + 0.5 * letterWidth));
		Line2D bottomFocusLine = new Line2D.Float(
				focusLineStart, 
				bottomLineHeight, 
				focusLineStart, 
				(int) (bottomLineHeight - 0.5 * letterWidth));

		// loop until all words from file have been displayed
		while (generator.hasNext()) {
			String word = generator.next();
			int focus = getFocusLetter(word);
			panel.clear();

			// Draw line graphics
			((Graphics2D) g).draw(topLine);
			((Graphics2D) g).draw(bottomLine);
			((Graphics2D) g).draw(topFocusLine);
			((Graphics2D) g).draw(bottomFocusLine);

			// Draw info words
			g.setFont(infoFont);
			g.drawString("Powered by", width / 12, 10 * height / 12);
			g.drawString(wpm + " wpm", 11 * width / 12 - 3 * letterWidth, 11 * height / 12);
			g.setFont(logoFont);
			g.setColor(Color.BLUE);
			g.drawString("nvp", width / 10, 10 * height / 12 + fontMetrics.charWidth(' '));

			// Draw focus word
			g.setFont(f);
			g.setColor(Color.BLACK);
			g.drawString(
					word.substring(0, focus), focusLetterStart - focus * letterWidth, height / 2);
			g.setColor(Color.RED);
			g.drawString(word.substring(focus, focus+1), focusLetterStart, height / 2);
			g.setColor(Color.BLACK);
			g.drawString(word.substring(focus+1), focusLetterStart + letterWidth, height / 2);

			// Pause after each word
			if (word.contains(",") 
					|| word.contains(".") 
					|| word.contains("!") 
					|| word.contains("?"))
				Thread.sleep(millsPerWord * 2);
			else Thread.sleep(millsPerWord);
		}

		// Print word statistics to System.out at the end
		System.out.println("You read " + generator.getWordCount() + " words,");
		System.out.println("in " + generator.getSentenceCount() + " sentences!");

		return;
	}

	/**
	 * getFocusLetter: given a word, returns the index of the focus letter
	 * @param String word
	 * @return index of focus letter
	 */
	public static int getFocusLetter (String word) {
		int length = word.length();
		if (length < 2)
			return 0;
		else if (length < 6)
			return 1;
		else if (length < 10)
			return 2;
		else if (length < 14)
			return 3;
		else return 4;
	}

}
