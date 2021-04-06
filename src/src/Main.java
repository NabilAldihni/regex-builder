package src;

/**
 *  To-do:
 *      - Look into RichTextFX for highlighting the test data
 *      - Add proper validation to inputs in quantifier window
 *      - Fix bug: changing the group or quantifier in the editor window does not show the changes in the full expression
 *      - Make the listview properly display descriptions (with quantifiers) instead of symbols
 *
 * Extras:
 *      -
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    // Start method to show the main window when the program starts
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainWindowController.class.getResource("/src/mainWindow.fxml"));
        primaryStage.setTitle("Regex Builder");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Launch method is called automatically by JavaFX when the program starts
    public static void main(String[] args) {
        launch(args);
    }
}
