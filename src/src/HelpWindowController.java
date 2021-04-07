package src;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpWindowController {
    public Button closeBtn;

    public void pressedCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
