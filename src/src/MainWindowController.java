package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.awt.*;
import javafx.scene.image.Image ;
import java.util.ArrayList;

public class MainWindowController {
    public Label expressionLabel;
    public TextField fullRegexField;
    public ListView<Expression> expressionListView;
    public Expression ex;
    public Button addExpressionBtn;
    public Button removeExpressionBtn;
    public Button moveUpBtn;
    public Button moveDownBtn;
    public Button editExpressionBtn;

    @FXML
    public void initialize() {
        Quantifier q1 = new Quantifier("From 2 to 9", "{2,9}");
        Quantifier q2 = new Quantifier("Optional", "?");
        Group g = new Group("Capture group", "(", ")");
        ArrayList<Element> es = new ArrayList<Element>();
        Element e1 = new Element("Any capital letter", "[A-Z]", q1);
        Element e2 = new Element("Any digit from 0-9", "[0-9]", q2);
        es.add(e1);
        es.add(e2);

        ex = new Expression(es, new Quantifier("1 or more", "+"), g);

        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test1"), g);
        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test2"), g);
        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test3"), g);
        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test4"), g);
        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test5"), g);
        expressionListView.getItems().add(ex);
        ex = new Expression(es, new Quantifier("1 or more", "test6"), g);

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

    }

    public void pressedAddExpression(ActionEvent actionEvent) {
        expressionListView.getItems().add(ex);
    }

    public void pressedRemoveExpression(ActionEvent actionEvent) {
        expressionListView.getItems().remove(expressionListView.getSelectionModel().getSelectedItem());
    }

    public void pressedMoveUp(ActionEvent actionEvent) {
        Expression item = expressionListView.getSelectionModel().getSelectedItem();
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            expressionListView.getItems().remove(index);
            expressionListView.getItems().add(index-1, item);
            expressionListView.getSelectionModel().select(index-1);
        }
    }

    public void pressedMoveDown(ActionEvent actionEvent) {
        Expression item = expressionListView.getSelectionModel().getSelectedItem();
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        if (index < expressionListView.getItems().size() - 1) {
            expressionListView.getItems().remove(index);
            expressionListView.getItems().add(index+1, item);
            expressionListView.getSelectionModel().select(index+1);
        }
    }

    public void pressedEditExpression(ActionEvent actionEvent) {
    }
}
