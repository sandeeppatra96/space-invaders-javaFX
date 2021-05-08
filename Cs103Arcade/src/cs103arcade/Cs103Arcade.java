
package cs103arcade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ali Zeeshan
 */
public class Cs103Arcade extends Application {

    private Pane root = new Pane();

    private Sprite player = new Sprite(140, 450, 50, 50, "player");
    private int Dead_Enemies = 0;
    private double t = 0;
    private AnimationTimer timer;
    private final String SYSTEM_ROOT = System.getProperty("user.dir");
    private ImageView _bg = new ImageView();

    private Parent createContent() {
        root.setPrefSize(300, 500);

        try {
            Image _i = new Image(new FileInputStream(SYSTEM_ROOT + "\\resources\\player.png"));
            player.setSrc(_i);
            Image b = new Image(new FileInputStream(SYSTEM_ROOT + "\\resources\\fg.png"));
            root.setBackground(new Background(new BackgroundImage(b, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        root.getChildren().add(player);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }

        };

        timer.start();
        nextLevel();
        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(40 + i * 50, 100, 30, 30, "enemy");
            try {
                Image E = new Image(new FileInputStream(SYSTEM_ROOT + "\\resources\\enemy.png"));
                s.setSrc(E);
            } catch (Exception e) {
            }
            root.getChildren().add(s);
        }
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }

    ;
    private void update() {
        t += 0.016;
        sprites().forEach(s -> {
            switch (s.type) {
                case "enemybullet":
                    s.moveUp();
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;
                case "playerbullet":
                    s.moveDown();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            Dead_Enemies++;

                        }
                    });
                    break;

                case "enemy":

                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }
                    break;
            }
        });
        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;

            return s.dead;
        });
        if (t > 2) {
            t = 0;
        }
        if (Dead_Enemies == 5) {
            timer.stop();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();

            try {
                super.stop();
                Parent r = FXMLLoader.load(getClass().getResource("wonGame.fxml"));
                Stage s = new Stage();
                Scene _scene = new Scene(r, 290, 490);
                s.setResizable(false);
                s.initStyle(StageStyle.UNDECORATED);
                s.setTitle("Invaders Arcade");
                s.setScene(_scene);
                s.show();
                System.out.println("You WON");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (player.dead) {
            timer.stop();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();

            try {
                super.stop();
                Parent r = FXMLLoader.load(getClass().getResource("endGame.fxml"));
                Stage s = new Stage();
                Scene _scene = new Scene(r, 290, 490);
                s.setResizable(false);
                s.initStyle(StageStyle.UNDECORATED);
                s.setTitle("Invaders Arcade");
                s.setScene(_scene);
                s.show();
                System.out.println("GAME OVER");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    private void shoot(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 15, (int) who.getTranslateY(), 20, 30, who.type + "bullet");
        try {
            Image _S = new Image(new FileInputStream(SYSTEM_ROOT + "\\resources\\bullet.png"));
            s.setSrc(_S);
        } catch (Exception e) {
        }
        root.getChildren().add(s);
    }

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        stage.setTitle("Space Invaders");
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();

        try {
            stage.getIcons().add(new Image(new FileInputStream(SYSTEM_ROOT + "\\resources\\bullet.png")));

            Scene scene = new Scene(createContent());
            scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case A:
                        if (player.getTranslateX() >= 5) {
                            player.moveLeft();
                        }
                        break;
                    case D:

                        if (player.getTranslateX() <= 265) {
                            player.moveRight();
                        }
                        break;
                    case SPACE:
                        shoot(player);
                        break;
                }
            });
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
    }

    private static class Sprite extends ImageView {

        boolean dead = false;
        String type;

        Sprite(int x, int y, int w, int h, String type) {
            super.setFitWidth(w);
            super.setFitHeight(h);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void setSrc(Image image) {
            super.setImage(image);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 5);
        }

        void moveUp() {
            setTranslateY(getTranslateY() + 5);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 5);
        }

        void moveDown() {
            setTranslateY(getTranslateY() - 5);
        }

    }

    public static void main(String[] args) {

        launch(args);
    }

    public Cs103Arcade() {

    }

}
