package src;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.awt.*;
import java.util.ArrayList;

public class MainWindowController {
    public Label expressionLabel;
    public TextField fullRegexField;
    public ListView<Expression> expressionListView;
    public Expression ex;

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

    }

    public void pressedMoveUp(ActionEvent actionEvent) {
        ObservableList selectedIndices = expressionListView.getSelectionModel().getSelectedIndices();

        for(Object o : selectedIndices){
            System.out.println("o = " + o + " (" + o.getClass() + ")");
        }
    }

    public void pressedMoveDown(ActionEvent actionEvent) {
    }

    public void pressedEditExpression(ActionEvent actionEvent) {
    }
}
