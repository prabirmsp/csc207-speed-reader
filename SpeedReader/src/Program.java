import java.awt.*;

public class Program {

	public static void main(String[] args) throws Exception {

		System.out.println(
				"Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");

		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int fontSize = Integer.parseInt(args[3]);
		int millsPerWord = 1000 * 60 / Integer.parseInt(args[4]);

		WordGenerator generator = new WordGenerator(args[0]);

		DrawingPanel panel = new DrawingPanel(width, height);
		Graphics g = panel.getGraphics();
		Font f = new Font("Courier", Font.BOLD, fontSize);
		g.setFont(f);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		int letterWidth = fontMetrics.charWidth(' ');
		int focusLetterStart = width / 3;

		while (generator.hasNext()) {
			String word = generator.next();
			int focus = getFocusLetter(word);
			panel.clear();
			g.drawString(word.substring(0, focus), focusLetterStart - focus * letterWidth, height / 2);
			g.setColor(Color.RED);
			g.drawString(word.substring(focus, focus+1), focusLetterStart, height / 2);
			g.setColor(Color.BLACK);
			g.drawString(word.substring(focus+1), focusLetterStart + letterWidth, height / 2);
			Thread.sleep(millsPerWord);
		}

		System.out.println("You read " + generator.getWordCount() + " words,");
		System.out.println("in " + generator.getSentenceCount() + " sentences!");


	}

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
