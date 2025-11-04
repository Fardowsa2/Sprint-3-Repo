package jonin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleSOSGameTest {

    @Test
    public void testSimpleGameWin() {
        SimpleSOSGame game = new SimpleSOSGame(3);

        assertTrue(game.placeLetter(0, 0, 'S')); // Blue
        assertTrue(game.placeLetter(1, 0, 'O')); // Red
        assertTrue(game.placeLetter(0, 1, 'O')); // Blue
        assertTrue(game.placeLetter(2, 2, 'S')); // Red
        assertTrue(game.placeLetter(0, 2, 'S')); // Blue — forms SOS

        assertTrue(game.isGameOver());
        assertEquals(AbstractSOSGame.Player.BLUE, game.getWinner());
        String status = game.getGameStatus();
        assertTrue(status.contains("BLUE") && status.contains("wins"));
    }

    @Test
    public void testSimpleGameDraw() {
        SimpleSOSGame game = new SimpleSOSGame(3);

        // Fill board with no SOS formation
        game.placeLetter(0, 0, 'O'); // Blue
        game.placeLetter(0, 1, 'O'); // Red
        game.placeLetter(0, 2, 'S'); // Blue
        game.placeLetter(1, 0, 'S'); // Red
        game.placeLetter(1, 1, 'S'); // Blue
        game.placeLetter(1, 2, 'O'); // Red
        game.placeLetter(2, 0, 'O'); // Blue
        game.placeLetter(2, 1, 'S'); // Red
        game.placeLetter(2, 2, 'O'); // Blue

        assertTrue(game.isGameOver());
        assertNull(game.getWinner());
        String status = game.getGameStatus();
        assertTrue(status.toLowerCase().contains("draw"));
    }
}
