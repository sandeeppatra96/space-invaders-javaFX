
package cs103arcade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ali Zeeshan
 */
public class WonGameController implements Initializable {

    @FXML
    private ImageView bg;
    @FXML
    private ImageView icon;

    @FXML
    private AnchorPane root;

    private String mess;
    private final String SYSTEM_ROOT = System.getProperty("user.dir");

    @FXML
    private void handle_start_game(Event event) {
        try {
            Cs103Arcade cs = new Cs103Arcade();
            root.getScene().getWindow();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
            cs.start(new Stage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void hanlde_exit_game(Event event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            InputStream stream = new FileInputStream(SYSTEM_ROOT + "\\resources\\fg.png");
            Image image = new Image(stream);
            bg.setImage(image);
            bg.setX(0);
            bg.setY(0);

            stream = new FileInputStream(SYSTEM_ROOT + "\\resources\\enemy.png");
            image = new Image(stream);
            icon.setImage(image);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
