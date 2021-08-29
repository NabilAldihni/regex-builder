package src;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpWindowController {
    public Button closeBtn;
    public Label contentLabel;
    
    public void initialize() {
        contentLabel.setText(
                "In the main window that you just opened this menu from you can manage the expression " +
                    "that you want to create.\n\n" +
                "The full expression that you build will appear in the white box on the top left, " +
                    "and you can use the options on the right of the table to configure and add " +
                    "parts to your expression (the \"copy\" button at the bottom left copies the expression " +
                    "to your clipboard).\n\n" +
                "When you click on the add button you will be taken to a new window, where you " +
                    "have many options to configure your individual elements.\n\n" +
                "The table and text field at the top of the editor window work in the same way as the " +
                    "main window.\n\n" +
                "You can edit any individual element whenever you need to with the edit button " +
                "(button with pencil icon)."
        );
    }

    public void pressedCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
