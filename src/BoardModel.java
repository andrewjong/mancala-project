import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The BoardModel represents the internal game state of the board and allows manipulation of the board via a player
 * move.
 * @author Andrew Jong
 * @since 14 April 2017
 */
class BoardModel {

	/* === ATTRIBUTES === */

	/* Pits for each player */
	private int[] player1Pits;
	private int[] player2Pits;

	/* Player mancalas, also directly tied to score, initial value is 0 */
	private int player1Mancala = 0;
	private int player2Mancala = 0;

	/* Keep track of whose turn it is to determine which pits/mancala to place stones in */
	private boolean player1Turn = true;

	/* Determine if the game is finished */
	private boolean gameFinished = false;

	/* === METHODS === */

	/**
	 * Construct a BoardModel object.
	 * @param pitsPerSide          the number of pits (not including mancalas) per side of the board
	 * @param startingStonesPerPit the number of starting stones in each pit
	 */
	public BoardModel(int pitsPerSide, int startingStonesPerPit) {
		player1Pits = new int[pitsPerSide];
		player2Pits = new int[pitsPerSide];
		Arrays.fill(player1Pits, startingStonesPerPit);
		Arrays.fill(player2Pits, startingStonesPerPit);
	}

	// Because clone() method in Java is bad practice.

	/**
	 * Copy constructor, Creates a copy of a BoardModel.
	 * @param copy the BoardModel to copy
	 */
	public BoardModel(BoardModel copy) {
		player1Pits = copy.player1Pits.clone();
		player2Pits = copy.player2Pits.clone();
		player1Mancala = copy.player1Mancala;
		player2Mancala = copy.player2Mancala;
		player1Turn = copy.player1Turn;
	}

	/**
	 * Process a move for the current player. The player is determined via the board's internal state.
	 * @param index the index of the pit, from 0 to the number of pits per side (obtainable via the method
	 *              getPitsPerSide()).
	 */
	public void playerMove(int index) {
		if (gameFinished) return; // don't do anything if game finished

		/* Argument checking. */

		// index must be within board bounds.
		if (index < 0 || index >= player1Pits.length) {
			throw new IllegalArgumentException("Requested index out of board bounds, must be between 0 and " +
					(player1Pits.length - 1) + ". Received " + index);
		}

		/* Determine the correct location of the move and pick up the stones */

		boolean onSide1 = player1Turn; // boolean for remembering which side the stones will go
		int numStones;
		if (onSide1) {
			// pick up the stones from side 1
			numStones = player1Pits[index];
			player1Pits[index] = 0;
		} else {
			// pick up the stones from side 2
			numStones = player2Pits[index];
			player2Pits[index] = 0;
		}
		if (numStones == 0) {
			System.out.println("No stones!");
			return;
		}

		/* Distribute the stones around the board */

		int nextPit = index + 1; // the pit to place stones in, starts at the pit after the index.
		for (; numStones > 0; numStones--) {
			// if no more pits on this side, put in mancala and toggle the state of onSide1.
			if (nextPit >= player1Pits.length) {
				// Add stone to the mancala
				if (onSide1) player1Mancala++;
				else player2Mancala++;

				// change side, and set next pit to zero
				nextPit = 0;
				onSide1 = !onSide1;
			}
			// otherwise add stones normally
			else {
				if (onSide1) player1Pits[nextPit] += 1;
				else player2Pits[nextPit] += 1;
				// move to the next pit
				nextPit++;
			}
		}

		/* Capturing */

		// the pit landed in
		int landedPit = nextPit - 1;
		// can capture if the pit landed is within range, on the player's side, and pit landed in is empty
		boolean withinRange = landedPit >= 0 && landedPit < player1Pits.length;
		if (withinRange) {
			boolean onPlayerSide = onSide1 == player1Turn;
			int[] myPits = (player1Turn) ? player1Pits : player2Pits;
			int[] opponentPits = (player1Turn) ? player2Pits : player1Pits;
			// landed pit was empty if the only stone was the last stone placed (1)
			boolean landedPitWasEmpty = myPits[landedPit] == 1;
			if (onPlayerSide && landedPitWasEmpty) {
				// the index of the opposite pit
				int capturePitIndex = opponentPits.length - 1 - landedPit;
				// see if stones to capture
				if (opponentPits[capturePitIndex] > 0) {
					myPits[landedPit] = 0;
					opponentPits[capturePitIndex] = 0;
					// 'capture' from the appropriate pit by adding the player's landed stone and the captured opponent's
					// stones to the player's mancala, and setting both the captured pit and the landed pit to 0
					if (onSide1) {
						player1Mancala += 1 + opponentPits[capturePitIndex];
					} else {
						player2Mancala += 1 + opponentPits[capturePitIndex];
					}
				}

			}
		}

		/* Take stones on own side if one side empty, and declare game finished */
		if (isOneSideEmpty()) {
			player1Mancala += IntStream.of(player1Pits).parallel().sum();
			Arrays.fill(player1Pits, 0);
			player2Mancala += IntStream.of(player2Pits).parallel().sum();
			Arrays.fill(player2Pits, 0);
			gameFinished = true;
		}

		/* Determine which player's turn is next */

		// if landed in mancala, the next pit to place would be index 0
		boolean notInEitherMancala = nextPit != 0;
		// if landed opponent's mancala, the next side to place on will be the current player's
		boolean landOpponentMancala = onSide1 == player1Turn;
		// toggle player turn turn if did not land in player's mancala
		if (notInEitherMancala || landOpponentMancala)
			player1Turn = !player1Turn;
		// otherwise the player gets to go again
	}

	/**
	 * Determine whether all the pits on one side (excluding mancalas) are empty of stones.
	 * @return true if stones empty, false if has stones
	 */
	private boolean isOneSideEmpty() {
		boolean side1Empty = true;
		for (int i : player1Pits) {
			if (i != 0)
				side1Empty = false;
		}
		if (!side1Empty) {
			for (int i : player2Pits)
				if (i != 0)
					return false;

		}
		return true;
	}

	/* === GETTERS AND SETTERS === */

	/**
	 * Get the array representing the stones on player 1's side of the board
	 * @return array of pits
	 */
	public int[] getPlayer1Pits() {
		return player1Pits;
	}

	/**
	 * Get the array representing the stones on player 2's side of the board
	 * @return array of pits
	 */
	public int[] getPlayer2Pits() {
		return player2Pits;
	}

	/**
	 * Get the number of stones in player 1's mancala. Also represents player 1's score.
	 * @return number stones in mancala
	 */
	public int getPlayer1Mancala() {
		return player1Mancala;
	}

	/**
	 * Get the number of stones in player 2's mancala. Also represents player 2's score.
	 * @return number stones in mancala
	 */
	public int getPlayer2Mancala() {
		return player2Mancala;
	}

	/**
	 * Get if the current turn is player 1's turn.
	 * @return true if player 1 turn, false if player 2 turn
	 */
	public boolean isPlayer1Turn() {
		return player1Turn;
	}

	/**
	 * See whether the game is finished or not.
	 * @return the state of the game true if finished, false if not.
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}
}
