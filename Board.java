import java.awt.event.*;
import javax.swing.*;

/*
 * The board for the Memory Card Game
 */
public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    /*
     * Constructor for Board.
     */
    public Board(int size, ActionListener AL)
    {
        // Allocate and configure the game board: an array of cards
        cards = new FlippableCard[size];

        // Fill the Cards array
        int imageIdx = 1;
        for (int i = 0; i < size; i += 2) {

            // Load the front image from the resources folder
            String imgPath = "res/hub" + imageIdx + ".jpg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));
            imageIdx++;  // get ready for the next pair of cards

            // Setup two cards at a time
            FlippableCard c1 = new FlippableCard(img);
            c1.setID(i);
            c1.setCustomName(imgPath);

            FlippableCard c2 = new FlippableCard(img);
            c2.setID(i+1);
            c2.setCustomName(imgPath);

            // Add Action Listeners to buttons
            c1.addActionListener(AL);
            c2.addActionListener(AL);

            // Add them to the array
            cards[i] = c1;
            cards[i + 1] = c2;
        }

        // Randomize the card positions
        randomizeCards();
    }

    /*
     * Fills the board view with cards.
     *
     * @param view      The view to fill with cards.
     */
    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {
            view.add(c);
        }
    }

    /*
     * Resets the board by randomizing flipping over cards.
     */
    public void resetBoard()
    {
        // Randomize the Card Positions
        randomizeCards();

        // Reset the flipped cards
        for (FlippableCard c : cards) {
            c.setEnabled(true);
            c.hideFront();
        }
    }

    /*
     * Randomize the card order.
     */
    private void randomizeCards() {

        // Shuffle the cards
        for (int i = 0; i < cards.length; i++) {
            int randIndex = (int)(Math.random() * cards.length);
            FlippableCard tempCard = cards[i];
            cards[i] = cards[randIndex];
            cards[randIndex] = tempCard;
        }
    }
}
