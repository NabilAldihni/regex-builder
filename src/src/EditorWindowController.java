package src;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditorWindowController {
    public TextField expressionField;
    public Button saveExpressionBtn;

    public void pressedAddExpression(ActionEvent actionEvent) {
    }

    public void pressedRemoveExpression(ActionEvent actionEvent) {
    }

    public void pressedMoveUp(ActionEvent actionEvent) {
    }

    public void pressedMoveDown(ActionEvent actionEvent) {
    }

    public void pressedEditExpression(ActionEvent actionEvent) {
    }

    public void saveExpressionPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) saveExpressionBtn.getScene().getWindow();
        stage.close();
    }
}
