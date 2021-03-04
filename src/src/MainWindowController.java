package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javafx.scene.image.Image ;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindowController {
    public ArrayList<Expression> expressions;

    public Label expressionLabel;
    public TextField fullRegexField;
    public ListView<Expression> expressionListView;
    public Expression ex;
    public Button addExpressionBtn;
    public Button removeExpressionBtn;
    public Button moveUpBtn;
    public Button moveDownBtn;
    public Button editExpressionBtn;
    public CheckBox firstElementCheckBox;
    public CheckBox lastElementCheckBox;

    @FXML
    public void initialize() {
        StageConfig.setMainWindowController(this);
        expressions = new ArrayList<Expression>();

        expressionListView.setCellFactory(new Callback<ListView<Expression>, ListCell<Expression>>() {
            @Override
            public ListCell<Expression> call(ListView<Expression> listView) {
                ListCell<Expression> cell = new ListCell<Expression>(){
                    @Override
                    protected void updateItem(Expression e, boolean empty){
                        super.updateItem(e, empty);
                        if (e != null) {
                            setText(e.compileExpression());
                        }
                        else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });

        refreshExpression();

    }

    public String refreshExpression() {
        fullRegexField.setText("");
        String fullExp = "";

        if (firstElementCheckBox.isSelected()) {
            fullExp+="^";
        }

        for (Expression e : expressions) {
            fullExp+=e.compileExpression();
        }

        if (lastElementCheckBox.isSelected()) {
            fullExp+="$";
        }
        fullRegexField.setText(fullExp);

        return fullExp;
    }

    public void pressedAddExpression(ActionEvent actionEvent) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/editorWindow.fxml"));
            Stage editorStage = new Stage();
            editorStage.setTitle("Regex builder - Expression editor");
            editorStage.setScene(new Scene(root));
            editorStage.initModality(Modality.APPLICATION_MODAL);
            editorStage.show();
        }
        catch (IOException e){
            System.out.println("Failed to create a new window");
        }
        refreshExpression();
    }

    public void pressedRemoveExpression(ActionEvent actionEvent) {
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        int size = expressionListView.getItems().size();
        expressions.remove(expressionListView.getSelectionModel().getSelectedItem());
        expressionListView.getItems().remove(expressionListView.getSelectionModel().getSelectedItem());
        if (index < size-1) {
            expressionListView.getSelectionModel().select(index);
        }
        else {
            expressionListView.getSelectionModel().select(index-1);
        }
        refreshExpression();
    }

    public void pressedMoveUp(ActionEvent actionEvent) {
        Expression item = expressionListView.getSelectionModel().getSelectedItem();
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            expressionListView.getItems().remove(index);
            expressionListView.getItems().add(index-1, item);
            expressionListView.getSelectionModel().select(index-1);
            expressions.remove(index);
            expressions.add(index-1, item);
        }
        refreshExpression();
    }

    public void pressedMoveDown(ActionEvent actionEvent) {
        Expression item = expressionListView.getSelectionModel().getSelectedItem();
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        if (index < expressionListView.getItems().size() - 1) {
            expressionListView.getItems().remove(index);
            expressionListView.getItems().add(index+1, item);
            expressionListView.getSelectionModel().select(index+1);
            expressions.remove(index);
            expressions.add(index+1, item);
        }
        refreshExpression();
    }

    public void pressedEditExpression(ActionEvent actionEvent) {
    }

    public void firstElementPressed(ActionEvent actionEvent) {
        refreshExpression();
    }

    public void lastElementPressed(ActionEvent actionEvent) {
        refreshExpression();
    }

    public void copyFullExpression(ActionEvent actionEvent) {
        StringSelection stringSelection = new StringSelection(fullRegexField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
