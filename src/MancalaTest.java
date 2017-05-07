
import javax.swing.*;
import java.awt.*;

/**
 * Class with the main method to start the program.
 * @author Andrew Jong
 */
public class MancalaTest {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Controller.setDefaultGameModel();
			GameModel model = Controller.getGameModel();

			JFrame frame = new JFrame("Mancala");
			frame.setSize(new Dimension(1000, 600));
			GameView view = new GameView(model);
			frame.add(view);

			frame.pack();

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});

	}
}
