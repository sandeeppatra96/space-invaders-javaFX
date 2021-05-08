
package cs103arcade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class StartController implements Initializable {
    
    
    @FXML
    private ImageView player;
    @FXML
    private ImageView bg;

    private final String SYSTEM_ROOT = System.getProperty("user.dir");

    @FXML
    private void handle_start_game(Event event) {
        try {
            Cs103Arcade cs = new Cs103Arcade();

            Stage stage = (Stage) (((Node) event.getTarget()).getScene().getWindow());
            stage.close();

            cs.start(new Stage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void hanlde_exit_game(Event event) {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Space Invaders");
        a.setHeaderText("Confirm");
        a.setContentText("Do you want to Exit Space Invaders ? ");
        a.initOwner(((Node) event.getTarget()).getScene().getWindow());
        a.initStyle(StageStyle.TRANSPARENT);
        a.initModality(Modality.APPLICATION_MODAL);
        a.setWidth(200);
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    private void FadeOut(Node n, int sec, int delay) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(sec));
        ft.setDelay(Duration.seconds(delay));
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setNode(n);
        ft.play();

    }

    private void FadeIn(Node n, int sec, int delay) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(sec));
        ft.setDelay(Duration.seconds(delay));
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setNode(n);
        ft.play();
    }

    private void FadeInOut(Node n, double sec, int delay) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(sec));
        ft.setDelay(Duration.seconds(delay));
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setNode(n);
        ft.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            InputStream stream = new FileInputStream(SYSTEM_ROOT + "\\resources\\fg.png");
            Image image = new Image(stream);
            bg.setImage(image);
            bg.setX(0);
            bg.setY(0);
            player.setOpacity(0);
            stream = new FileInputStream(SYSTEM_ROOT + "\\resources\\player.png");
            image = new Image(stream);
            player.setImage(image);
            FadeInOut(player, 0.5, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
