package cs103arcade;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class start extends Application {

    
    /**
     * start class gets the starting fxml scene which  has to be displayed on the screen 
     * first
     * <p>
    ** {@inheritDoc }
    */
    
    @Override
    public void start(Stage stage) throws IOException {
        //root is the parent node and it will load resource named as start.fxml file this fxml fill contains the GUI
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));

        //Making a scene of width 290px and height 490 px respectively with containing the root variabale that contains the GUI
        Scene scene = new Scene(root, 290, 490);

        //setting the stage variable that automatically passed as parameter by super class thread non resizeable
        stage.setResizable(false);

        //initializing the style to Undecorated that means it will no contain the title bar and exit buttons
        stage.initStyle(StageStyle.UNDECORATED);

        //now setting the scene to the stage by using scene variable
        stage.setScene(scene);

        //showing the stage to get displayed
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
