package jonin;

public class GeneralSOSGame extends AbstractSOSGame {
    private int scoreBlue;
    private int scoreRed;

    public GeneralSOSGame(int boardSize) {
        super(boardSize, GameType.GENERAL);
    }

    @Override
    protected void resetGameState() {
        scoreBlue = 0;
        scoreRed = 0;
    }

    @Override
    protected void processMove(int row, int col) {
        int sosCount = checkForSOS(row, col);
        
        // Update scores
        if (currentPlayer == Player.BLUE) {
            scoreBlue += sosCount;
        } else {
            scoreRed += sosCount;
        }
        
        // Check game end condition
        if (isBoardFull()) {
            gameOver = true;
        } else {
            // In general game, player gets another turn only if they formed an SOS
            if (sosCount == 0) {
                switchTurn();
            }
        }
    }

    @Override
    public String getGameStatus() {
        if (!gameOver) {
            return String.format("General Game - %s's turn - Blue: %d, Red: %d", 
                               currentPlayer, scoreBlue, scoreRed);
        }
        
        // Determine winner
        if (scoreBlue > scoreRed) {
            return String.format("General Game Over - Blue wins! %d-%d", scoreBlue, scoreRed);
        } else if (scoreRed > scoreBlue) {
            return String.format("General Game Over - Red wins! %d-%d", scoreRed, scoreBlue);
        } else {
            return String.format("General Game Over - Draw! %d-%d", scoreBlue, scoreRed);
        }
    }
    
    public int getScoreBlue() {
        return scoreBlue;
    }
    
    public int getScoreRed() {
        return scoreRed;
    }
}
