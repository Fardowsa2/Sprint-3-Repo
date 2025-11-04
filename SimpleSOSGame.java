package jonin;

public class SimpleSOSGame extends AbstractSOSGame {
    private Player winner;

    public SimpleSOSGame(int boardSize) {
        super(boardSize, GameType.SIMPLE);
    }

    @Override
    protected void resetGameState() {
        winner = null;
    }

    @Override
    protected void processMove(int row, int col) {
        int sosCount = checkForSOS(row, col);
        if (sosCount > 0) {
            gameOver = true;
            winner = currentPlayer;
        } else {
            if (isBoardFull()) {
                gameOver = true;
                winner = null;
            } else {
                switchTurn();
            }
        }
    }

    @Override
    public String getGameStatus() {
        if (!gameOver) {
            return "Simple Game - Current turn: " + currentPlayer;
        }
        if (winner != null) {
            return "Simple Game Over - " + winner + " wins!";
        }
        return "Simple Game Over - It's a draw!";
    }
    
    public Player getWinner() {
        return winner;
    }
}
