package src;

/**
 *  To-do:
 *      - Make quantifier window communicate with previous window
 *      - Add all elements that can be added, validate them and make listView work
 *          - Validation for elements with textfields
 *      - Included above: make controls for listView work
 *      - Add proper error dialog boxes to editor window
 *      - Make the listview properly display descriptions (with quantifiers) instead of symbols
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
