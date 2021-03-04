package src;

/**
 *  To-do:
 *      - Add proper error checking for quantifier textfields (has to be int, not empty, strip, etc...)
 *      - Add all elements that can be added, validate them and make listView work
 *      - Included above: make controls for listView work
 *      - Add proper error dialog boxes to editor window
 *
 *  Extras:
 *      - When you add an expression make it go below the selected one (pass index to controller)
 *      - Fix text being selected at beginning
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainWindowController.class.getResource("/src/mainWindow.fxml"));
        primaryStage.setTitle("Regex Builder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
