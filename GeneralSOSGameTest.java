package jonin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralSOSGameTest {

    @Test
    public void testGeneralGameExtraTurn() {
        GeneralSOSGame game = new GeneralSOSGame(3);

        // Set up a guaranteed SOS on top row by Blue
        assertTrue(game.placeLetter(0, 0, 'S')); // Blue
        assertTrue(game.placeLetter(1, 0, 'S')); // Red
        assertTrue(game.placeLetter(0, 1, 'O')); // Blue
        assertTrue(game.placeLetter(2, 0, 'O')); // Red
        assertTrue(game.placeLetter(0, 2, 'S')); // Blue forms SOS

        assertEquals(AbstractSOSGame.Player.BLUE, game.getCurrentPlayer()); // Should keep turn
        assertTrue(game.getScoreBlue() > 0);
    }

    @Test
    public void testGeneralGameOverAndWinner() {
        GeneralSOSGame game = new GeneralSOSGame(3);
    
        // Simulate a full game with just one SOS by Red
        assertTrue(game.placeLetter(0, 0, 'S')); // Blue
        assertTrue(game.placeLetter(0, 1, 'O')); // Red
        assertTrue(game.placeLetter(0, 2, 'O')); // Blue
        assertTrue(game.placeLetter(1, 0, 'S')); // Red
        assertTrue(game.placeLetter(1, 1, 'O')); // Blue
        assertTrue(game.placeLetter(1, 2, 'S')); // Red -> Forms "SOS" in row 1
        assertTrue(game.placeLetter(2, 0, 'O')); // Red gets extra turn
        assertTrue(game.placeLetter(2, 1, 'O')); // Red again
        assertTrue(game.placeLetter(2, 2, 'S')); // Red again
    
        // Now the board is fully filled
        assertTrue(game.isGameOver());
        String status = game.getGameStatus();
        System.out.println(status);
       // assertTrue(status.contains("Game over"));
        assertTrue(game.getScoreRed() > game.getScoreBlue());
    }
}  
