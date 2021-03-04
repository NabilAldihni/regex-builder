package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditorWindowController {
    public TextField expressionField;
    public Button saveExpressionBtn;
    public MainWindowController mainController;

    @FXML
    public void initialize() {
        StageConfig.setEditorWindowController(this);
        mainController = StageConfig.getMainWindowController();
    }

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
        String text = expressionField.getText();

        Quantifier q1 = new Quantifier("From 2 to 9", "");
        Group g = new Group("Capture group", text, text);
        ArrayList<Element> es = new ArrayList<Element>();
        Element e1 = new Element("Any capital letter", "", q1);
        es.add(e1);

        Expression ex = new Expression(es, new Quantifier("1 or more", "CODE"), g);
        mainController.expressionListView.getItems().add(ex);
        mainController.expressions.add(ex);
        mainController.refreshExpression();
        Stage stage = (Stage) saveExpressionBtn.getScene().getWindow();
        stage.close();
    }
}
