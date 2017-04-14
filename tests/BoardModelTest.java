import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by andrew on 4/13/17.
 */
class BoardModelTest {
	@Test
	void testPlayerMoveSideException(){
		BoardModel model = new BoardModel();
		try {
			model.playerMove(2, 0);
		} catch (IllegalArgumentException e) {
			String expectedErrorMessage = "Please pass in BoardModel.SIDE1 (0) or BoardModel.SIDE2 (1). " +
			"Received" + 2;
			assertTrue(expectedErrorMessage.equals(e.getMessage()));
		}
	}

	@Test
	void testPlayerTurnToggle() {
		BoardModel model = new BoardModel();
		assertEquals(true, model.isPlayer1Turn());
		model.playerMove(BoardModel.SIDE1, 0);
		assertEquals(false, model.isPlayer1Turn());
	}

	@Test
	void testPlayerMoveP1SideOnlyWithMancala() {
		BoardModel model = new BoardModel(4);
		model.playerMove(BoardModel.SIDE1, 2);
		
		int[] expectedP1Pits = {4, 4, 0, 5, 5, 5};
		assertTrue(Arrays.equals(expectedP1Pits, model.getPlayer1Pits()));

		int[] expectedP2Pits = {4, 4, 4, 4, 4, 4};
		assertTrue(Arrays.equals(expectedP2Pits, model.getPlayer2Pits()));

		int amountInMancala = model.getPlayer1Mancala();
		assertEquals(1, amountInMancala);
	}

	@Test
	void testPlayerMoveTransferP1ToP2Side() {
		BoardModel model = new BoardModel(4);
		model.playerMove(BoardModel.SIDE1, 3);

		int[] expectedP1Pits = {4, 4, 4, 0, 5, 5};
		assertTrue(Arrays.equals(expectedP1Pits, model.getPlayer1Pits()));

		int[] expectedP2Pits = {5, 4, 4, 4, 4, 4};
		assertTrue(Arrays.equals(expectedP2Pits, model.getPlayer2Pits()));

		int amountInMancala = model.getPlayer1Mancala();
		assertEquals(1, amountInMancala);
	}
}