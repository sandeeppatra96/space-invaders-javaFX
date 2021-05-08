package cs1302.game;

import java.util.Random;
import java.util.logging.Level;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private Random rng;       // random number generator
    private Rectangle player; // some rectangle to represent the player
    private IdleCat cat;      // the not so idle cat (see IdleCat.java)

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */
    public DemoGame(int width, int height) {
        super(width, height, 60);            // call parent constructor
        setLogLevel(Level.INFO);             // enable logging
        this.rng = new Random();             // random number generator
        this.player = new Rectangle(20, 20); // some rectangle to represent the player
        this.cat  = new IdleCat(this);       // the not so idle cat (see IdleCat.java)
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        getChildren().addAll(player, cat);         // add to main container
        // setup player
        player.setX(50);                           // 50px in the x direction (right)
        player.setY(50);                           // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
        // setup the cat
        cat.setX(0);
        cat.setY(0);
    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {

        // (x, y)         In computer graphics, coordinates along an x-axis and
        // (0, 0) -x--->  y-axis are used. When compared to the standard
        // |              Cartesian plane that most students are familiar with,
        // y              the x-axis behaves the same, but the y-axis increases
        // |              in the downward direction! Keep this in mind when
        // v              adjusting the x and y positions of child nodes.

        // update player position
        isKeyPressed( KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
        isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));

        // <--------------------------------------------------------------------
        // try adding the code to make the player move up and down!
        // <--------------------------------------------------------------------

        // update idle cat
        cat.update();

    } // update

    /**
     * Move the player rectangle to a random position.
     * @param event associated mouse event
     */
    private void handleClickPlayer(MouseEvent event) {
        logger.info(event.toString());
        player.setX(rng.nextDouble() * (getWidth() - player.getWidth()));
        player.setY(rng.nextDouble() * (getHeight() - player.getHeight()));
    } // handleClickPlayer

} // DemoGame
