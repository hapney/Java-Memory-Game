import javax.swing.*;

/*
 * The specific Flippable Card for the Memory Card Game
 */
public class FlippableCard extends JButton
{
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("res/Back.jpg"));

    // ID + Name
    private int id;
    private String customName;

    /*
     * Default constructor
     */
    public FlippableCard() {
        super();
    }

    /*
     * Constructor with card front initialization
     */
    public FlippableCard(ImageIcon frontImage)
    {
        super();
        front = frontImage;
        super.setIcon(back);
    }

    /*
     * Set the image used as the front of the card
     *
     * @param frontImage    The imageIcon to save as the front
     */
    public void setFrontImage(ImageIcon frontImage) {
        front = frontImage;
    }

    /*
     * Flips the card to the front
     */
    public void showFront() {
        this.setIcon(front);
    }

    /*
     * Flips the card to the back
     */
    public void hideFront() {
        this.setIcon(back);
    }

    /*
     * Retrieves the ID for the card.
     *
     * @returns the id
     */
    public int id() {
        return id;
    }

    /*
     * Sets the ID for the card
     *
     * @param i     The id to set
     */
    public void setID(int i) {
        id = i;
    }

    /*
     * Retrieves the custom name for the card
     *
     * @returns the custom name
     */
    public String customName() {
        return customName;
    }

    /*
     * Sets the custom name for the card
     *
     * @param s     The custom name
     */
    public void setCustomName(String s) {
        customName = s;
    }
}
