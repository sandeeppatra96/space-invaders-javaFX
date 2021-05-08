package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * A simple "sprite" of an idle cat.
 */
public class IdleCat extends ImageView {

    private Game game; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update

    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */
    public IdleCat(Game game) {
        super("file:resources/sprites/cat_idle.gif"); // call parent constructor
        this.setPreserveRatio(true);
        this.setFitWidth(getImage().getWidth());
        this.game = game;
        this.dx = 2; // each update, add 2 to x (to start)
        this.dy = 0; // each update, add 0 to y (to start)
    } // IdleCat

    /**
     * Update the position of the cat.
     */
    public void update() {
        Bounds catBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        if (catBounds.getMaxX() > gameBounds.getMaxX()) {
            dx *= -1.0;      // change x direction
            setScaleX(-1.0); // flip this image view horizontally
        } else if (catBounds.getMinX() < gameBounds.getMinX()) {
            dx *= -1.0;      // change x direction
            setScaleX(1.0);  // flip this image view back
        } // if
        setX(getX() + dx);   // move this cat!
    } // update

} // IdleCat
