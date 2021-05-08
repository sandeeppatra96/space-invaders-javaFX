package cs1302.game;

import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * An abstract parent class for simple games. The central component of any concrete
 * child class should be its overridden {@link #update} method as it represents one
 * iteration of the main game loop -- here is some pseudo code that illustrates
 * the {@link #play} method:
 *
 * <pre>
 * init();
 * while(playing) {
 *    update();
 * } // while
 * </pre>
 */
public abstract class Game extends Region {

    protected final Logger logger = Logger.getLogger("cs1302.game.Game");

    private final Bounds bounds;                     // game bounds
    private final Duration fpsTarget;                // target duration for game loop
    private final Timeline loop = new Timeline();    // timeline for main game loop
    private final BitSet keysPressed = new BitSet(); // set of currently pressed keys

    private boolean initialized = false;             // play() has been called?

    /**
     * Construct a {@code Game} object.
     * @param width minimum game region width
     * @param height minimum game region height
     * @param fps target frames per second (FPS)
     */
    public Game(int width, int height, int fps) {
        super();
        setMinWidth(width);
        setMinHeight(height);
        this.bounds = new BoundingBox(0, 0, width, height);
        this.fpsTarget = Duration.millis(1000.0 / fps);
        addEventFilter(KeyEvent.KEY_PRESSED, event -> handleKeyPressed(event));
        addEventFilter(KeyEvent.KEY_RELEASED, event -> handleKeyReleased(event));
        initGameLoop();
    } // Game

    /**
     * Initialize the main game loop.
     */
    private void initGameLoop() {
        KeyFrame updateFrame = new KeyFrame(fpsTarget, event -> {
            requestFocus();
            update();
        });
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.getKeyFrames().add(updateFrame);
    } // initGameLoop

    /**
     * Initialize the game. A game may override this method to perform initialization
     * that needs to happen prior to the main game loop. The {@link #play} method
     * will attempt to call this method only once. The implementation of this method
     * provided by the {@code Game} class does nothing.
     */
    protected void init() {}

    /**
     * Perform one iteration of the main game loop.
     */
    protected abstract void update();

    /**
     * Add the key code for the pressed key to the set of pressed keys.
     * @param event associated key event
     */
    private void handleKeyPressed(KeyEvent event) {
        logger.info(event.toString());
        keysPressed.set(event.getCode().getCode());
    } // handleKeyPressed

    /**
     * Remove the key code for the released key from the set of pressed keys.
     * @param event associated key event
     */
    private void handleKeyReleased(KeyEvent event) {
        logger.info(event.toString());
        keysPressed.clear(event.getCode().getCode());
    } // handleKeyReleased

    /**
     * Return whether or not a key is currently pressed.
     * @param key the key code to check
     * @return {@code true} if the key is pressed; otherwise {@code false}
     */
    protected final boolean isKeyPressed(KeyCode key) {
        return keysPressed.get(key.getCode());
    } // isKeyPressed

    /**
     * Return whether or not a key is currently pressed. If the key is pressed, then
     * {@code handler.run()} is run on the calling thread before the method returns.
     * @param key the key code to check
     * @param handler the object whose {@code run} method is invoked
     * @return {@code true} if the key is pressed; otherwise {@code false}
     */
    protected final boolean isKeyPressed(KeyCode key, Runnable handler) {
        if (isKeyPressed(key)) {
            handler.run();
            return true;
        } else {
            return false;
        } // if
    } // isKeyPressed

    /**
     * Setup and start the main game loop.
     */
    public final void play() {
        if (!initialized) {
            init();
            initialized = true;
        } // if
        loop.play();
    } // start

    /**
     * Stop the main game loop.
     */
    public final void stop() {
        loop.stop();
    } // stop

    /**
     * Pause the main game loop.
     */
    public final void pause() {
        loop.pause();
    } // pause

    /**
     * Set the log level specifying which message levels will be logged by the game's logger.
     * @param level level to set
     */
    public final void setLogLevel(Level level) {
        logger.setLevel(level);
    } // setLogLevel

    /**
     * Get the bounds for this game that were specified when it was constructed.
     * @return bounds for this game
     */
    public final Bounds getGameBounds() {
        return bounds;
    } // getGameBounds

} // Game
