/*
 * Author: Sydney Norman
 *      Base Code from Brent Seales
 *
 * Date: September 19, 2017
 * Class: CS335-001
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Delivers the classic Memory Card Game
 */
public class MemoryGame extends JFrame implements ActionListener
{
    // Total size of the board
    private static final int SIZE = 24;

    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1, prevCard2;

    // Labels to display game info
    private JLabel errorLabel, timerLabel, matchesLabel;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int clickCount = 0, gameTime = 0, errorCount = 0;
    private int pairsFound = 0;

    // Game timer: will be configured to trigger an event every second
    private Timer gameTimer;

    /*
     * Constructor for Memory Game
     */
    public MemoryGame()
    {
        // Call the base class constructor
        super("Hubble Memory Game");

        // Allocate the interface elements
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");
        timerLabel = new JLabel("Timer: 0");
        errorLabel = new JLabel("Errors: 0");
        matchesLabel = new JLabel("Matches: 0");

        // Set Up the Timer
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTime++;
                timerLabel.setText("Timer: " + gameTime);
            }
        });
        gameTimer.start();

        // Add event handler for restart game button
        RestartGameHandler restartHandler = new RestartGameHandler();
        restart.addActionListener(restartHandler);

        // Add event handler for quit game button
        QuitGameHandler quitHandler = new QuitGameHandler();
        quit.addActionListener(quitHandler);

        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(SIZE, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(4, 6, 2, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 4, 2, 2));
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(timerLabel);
        labelView.add(errorLabel);
        labelView.add(matchesLabel);

        // Both panels should now be individually layed out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(745, 470);
        setVisible(true);
    }

    /*
     * Handle anything that gets clicked and that uses MemoryGame as an ActionListener
     *
     * @param e The action event which called this function
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get the currently clicked card from a click event
        FlippableCard currCard = (FlippableCard)e.getSource();

        processCardClick(currCard);

        // Check If Game Over
        if (pairsFound == SIZE / 2) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(MemoryGame.this, "Congrats! You Won In " + gameTime + " Seconds With Only " + errorCount + " Errors!");

            int reply = JOptionPane.showConfirmDialog(MemoryGame.this, "Play Again?", "Play Again?", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
            restartGame();
        }

    }

    /*
     * Processes the user's card click
     *
     * @param currCard The card clicked on by the user
     */
    private void processCardClick(FlippableCard currCard) {

        // Show Current Card
        currCard.showFront();

        // Process the card selection
        if (clickCount == 0) {

            // Hide Previous Cards
            if (prevCard1 != null) {
                prevCard1.hideFront();
            }
            if (prevCard2 != null) {
                prevCard2.hideFront();
            }

            // Show Current Card
            currCard.showFront();
            prevCard1 = currCard;

            clickCount++;
        }
        else if (clickCount == 1 && currCard.id() != prevCard1.id()) {

            // Show Current Card
            currCard.showFront();
            prevCard2 = currCard;

            // Check if there is a match
            if (prevCard1.customName().equals(prevCard2.customName()) && prevCard1.id() != prevCard2.id()) {

                // Add a Point
                pairsFound++;
                matchesLabel.setText("Matches: " + pairsFound);

                // Make Cards Unclickable
                prevCard1.setEnabled(false);
                prevCard2.setEnabled(false);

                // Reset Previous Cards
                prevCard1 = null;
                prevCard2 = null;
            }
            else {

                // Increase Error Count
                errorCount++;
                errorLabel.setText("Errors: " + errorCount);
            }
            clickCount--;
        }
    }

    /*
     * Restarts the game.
     */
    private void restartGame()
    {
        pairsFound = 0;
        gameTime = 0;
        clickCount = 0;
        errorCount = 0;
        timerLabel.setText("Timer: 0");
        errorLabel.setText("Errors: 0");
        matchesLabel.setText("Matches: 0");
        gameTimer.start();

        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        gameBoard.resetBoard();
        gameBoard.fillBoardView(boardView);
        boardView.revalidate();
        boardView.repaint();
    }

    // Inner Class for Restart Game Handler
    private class RestartGameHandler implements ActionListener {
        // handle button event
        public void actionPerformed(ActionEvent event) {

            // Pause the Timer
            gameTimer.stop();

            // Ask if user would like to restart
            int reply = JOptionPane.showConfirmDialog(MemoryGame.this, "Would you like to restart?", "Restart Game", JOptionPane.YES_NO_OPTION);

            // Restart Game If User Selected Yes
            if (reply == JOptionPane.YES_OPTION) {
                restartGame();
            }

            // Return to Game Play
            gameTimer.start();

        }
    }

    // Inner Class for Quit Game Handler
    private class QuitGameHandler implements ActionListener {

        // Handle the Button Event
        public void actionPerformed(ActionEvent event) {

            // Pause the Timer
            gameTimer.stop();

            // Ask if user would like to quit
            int reply = JOptionPane.showConfirmDialog(MemoryGame.this, "Are you sure you would like to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);

            // Quit Game If User Selected Yes
            if (reply == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

            // Return to Game Play
            gameTimer.start();
        }
    }

    /*
     * Creates the Memory Game.
     */
    public static void main(String args[]) {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
