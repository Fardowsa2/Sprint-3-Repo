package jonin;

import javax.swing.*;
import java.awt.*;

public class SOSGui extends JFrame {
    private static final long serialVersionUID = 1L;
	private AbstractSOSGame game;
    private JButton[][] cells;

    // Controls
    private JRadioButton simpleModeRadio;
    private JRadioButton generalModeRadio;
    private JComboBox<Integer> boardSizeCombo;
    private JRadioButton blueS, blueO, redS, redO;
    private JLabel statusLabel;
    private JButton newGameButton;

    public SOSGui() {
        super("SOS Game - Human vs Human");
        initializeGUI();
        startNewGame();
    }

    private void initializeGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        createTopPanel();
        createPlayerPanels();
        createCenterPanel();
        createBottomPanel();
        
        setVisible(true);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("SOS GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel);

        // Game mode selection
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        modePanel.setBorder(BorderFactory.createTitledBorder("Game Mode"));
        simpleModeRadio = new JRadioButton("Simple Game", true);
        generalModeRadio = new JRadioButton("General Game");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(simpleModeRadio);
        modeGroup.add(generalModeRadio);
        modePanel.add(simpleModeRadio);
        modePanel.add(generalModeRadio);
        topPanel.add(modePanel);

        // Board size selection
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sizePanel.setBorder(BorderFactory.createTitledBorder("Board Size"));
        boardSizeCombo = new JComboBox<>(new Integer[]{3, 4, 5, 6, 7, 8});
        boardSizeCombo.setSelectedItem(8);
        sizePanel.add(new JLabel("Size:"));
        sizePanel.add(boardSizeCombo);
        topPanel.add(sizePanel);

        // New game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        topPanel.add(newGameButton);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createPlayerPanels() {
        // Blue player panel (left)
        JPanel bluePanel = new JPanel();
        bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));
        bluePanel.setBorder(BorderFactory.createTitledBorder("Blue Player"));
        bluePanel.add(Box.createVerticalStrut(10));
        
        JLabel blueLabel = new JLabel("Choose Letter:");
        blueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bluePanel.add(blueLabel);
        
        blueS = new JRadioButton("S", true);
        blueO = new JRadioButton("O");
        blueS.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueO.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);
        
        bluePanel.add(blueS);
        bluePanel.add(blueO);
        bluePanel.add(Box.createVerticalStrut(20));
        
        add(bluePanel, BorderLayout.WEST);

        // Red player panel (right)
        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.Y_AXIS));
        redPanel.setBorder(BorderFactory.createTitledBorder("Red Player"));
        redPanel.add(Box.createVerticalStrut(10));
        
        JLabel redLabel = new JLabel("Choose Letter:");
        redLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        redPanel.add(redLabel);
        
        redS = new JRadioButton("S", true);
        redO = new JRadioButton("O");
        redS.setAlignmentX(Component.CENTER_ALIGNMENT);
        redO.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);
        
        redPanel.add(redS);
        redPanel.add(redO);
        redPanel.add(Box.createVerticalStrut(20));
        
        add(redPanel, BorderLayout.EAST);
    }

    private void createCenterPanel() {
        // Initial center panel with 8x8 grid
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(centerPanel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        statusLabel = new JLabel("Ready to start new game");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(statusLabel);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void startNewGame() {
        int size = (Integer) boardSizeCombo.getSelectedItem();
        
        // Create appropriate game instance
        if (simpleModeRadio.isSelected()) {
            game = new SimpleSOSGame(size);
        } else {
            game = new GeneralSOSGame(size);
        }
        
        rebuildBoard(size);
        updateStatus();
    }

    private void rebuildBoard(int size) {
        // Remove existing center component
        Container content = getContentPane();
        Component oldCenter = ((BorderLayout) content.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (oldCenter != null) {
            content.remove(oldCenter);
        }

        // Create new board grid
        JPanel boardPanel = new JPanel(new GridLayout(size, size, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cells = new JButton[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                cells[r][c] = new JButton("");
                cells[r][c].setFont(new Font("Arial", Font.BOLD, 20));
                cells[r][c].setFocusPainted(false);
                
                // Color coding for better visibility
                if ((r + c) % 2 == 0) {
                    cells[r][c].setBackground(new Color(240, 240, 240));
                } else {
                    cells[r][c].setBackground(new Color(220, 220, 220));
                }

                final int row = r, col = c;
                cells[r][c].addActionListener(e -> handleCellClick(row, col));
                boardPanel.add(cells[r][c]);
            }
        }

        content.add(boardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void handleCellClick(int row, int col) {
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game is over! Start a new game.", 
                                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Determine which letter to place based on current player's selection
        char letter;
        if (game.getCurrentPlayer() == AbstractSOSGame.Player.BLUE) {
            letter = blueS.isSelected() ? 'S' : 'O';
        } else {
            letter = redS.isSelected() ? 'S' : 'O';
        }

        boolean moveSuccess = game.placeLetter(row, col, letter);
        
        if (moveSuccess) {
            // Update cell display
            cells[row][col].setText(String.valueOf(letter));
            
            // Color the cell based on player
            if (game.getCurrentPlayer() == AbstractSOSGame.Player.BLUE) {
                cells[row][col].setBackground(new Color(200, 220, 255)); // Light blue
            } else {
                cells[row][col].setBackground(new Color(255, 200, 200)); // Light red
            }
            
            updateStatus();
            
            // Check if game is over and show final message
            if (game.isGameOver()) {
                String message = game.getGameStatus();
                JOptionPane.showMessageDialog(this, message, "Game Over", 
                                            JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Cell may be occupied or game is over.", 
                                        "Invalid Move", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateStatus() {
        statusLabel.setText(game.getGameStatus());
        
        // Update title to show current player
        String playerText = (game.getCurrentPlayer() == AbstractSOSGame.Player.BLUE) ? 
                           "Blue's Turn" : "Red's Turn";
        setTitle("SOS Game - " + playerText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SOSGui();
        });
    }
}
