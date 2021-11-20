package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    /**
     * Start method to show the main window when the program starts
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainWindowController.class.getResource("/src/mainWindow.fxml"));
        primaryStage.setTitle("Regex Builder");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Launch method, which is called automatically by JavaFX when the program starts
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
