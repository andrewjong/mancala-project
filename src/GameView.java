import javax.swing.*;
import java.awt.*;

/**
 * The main view for the Mancala Game. Note many of these methods and classes don't exist yet, as they will be
 * implemented later.
 * @author Andrew Jong
 */
public class GameView {
	// Possibly add parameter of style
	GameView(GameModel gameModel) {
		JFrame frame = new JFrame("Mancala");
		frame.setLayout(new BorderLayout());

		// Player turn
		PlayerTurnPanel playerTurnPanel = new PlayerTurnPanel();
		frame.add(playerTurnPanel, BorderLayout.NORTH);

		// Board Panel
		BoardPanel boardPanel = new BoardPanel();

		GridPanel gridPanel = new GridPanel();
		MancalaPanel mancalaPanelP1 = new MancalaPanel();
		MancalaPanel mancalaPanelP2 = new MancalaPanel();
		boardPanel.left = mancalaPanelP2;
		boardPanel.center = gridPanel;
		boardPanel.right = mancalaPanelP1;


		frame.add(boardPanel, BorderLayout.CENTER);

		// Undo/redo controls
		UndoRedoPanel undoRedoPanel = new UndoRedoPanel();
		frame.add(undoRedoPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// the change listener
		gameModel.addChangeListener(changeEvent -> {
			BoardData boardData = gameModel.getCurrentBoardData();
			playerTurnPanel.setPlayerTurn(boardData.PLAYER_1_TURN);
			mancalaPanelP1.setMancala(boardData.PLAYER_1_MANCALA);
			gridPanel.

			undoRedoPanel.update(boardData);
		});
	}
}
