package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditorWindowController {
    public TextField expressionField;
    public Button saveExpressionBtn;
    public MainWindowController mainController;
    public TextField quantifierRangeFirstField;
    public TextField quantifierRangeLastField;
    public TextField quantifierMinField;
    public ToggleGroup groupToggle;
    public Pane quantifierPane;
    public ToggleGroup quantifierToggle;
    public TextField quantifierExactlyField;
    public Pane p1;
    public TextField matchesExactlyField;
    public TextField elementRangeMinField;
    public TextField elementRangeMaxField;
    public TextField elementsCharOfField;

    private ArrayList<Element> elements;
    private Group group;
    private Quantifier quantifier;

    @FXML
    public void initialize() {
        StageConfig.setEditorWindowController(this);
        mainController = StageConfig.getMainWindowController();

        elements = new ArrayList<Element>();
        group = new Group();
        quantifier = new Quantifier();
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
        boolean errors = false;

        // Find selected group
        String selectedGroup = "";
        try {
            selectedGroup = ((RadioButton) groupToggle.getSelectedToggle()).getText();
        }
        catch (Exception e) {
            System.out.println("No group option was selected!");
            errors = true;
        }

        quantifierPane.setDisable(selectedGroup.equals("None"));

        switch (selectedGroup) {
            case "None":
                break;
            case "Capture group":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(");
                group.setEndSymbol(")");
                break;
            case "Non-capture group":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(?:");
                group.setEndSymbol(")");
                break;
            case "Positive lookahead":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(?=");
                group.setEndSymbol(")");
                break;
            case "Negative lookahead":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(?!");
                group.setEndSymbol(")");
                break;
            default:
                System.out.println("There was an error with the selected group");
                errors = true;
        }

        // Find selected quantifier
        if (!selectedGroup.equals("None")) {
            String selectedQuantifier = "";
            try {
                selectedQuantifier = ((RadioButton) quantifierToggle.getSelectedToggle()).getText();
            } catch (Exception e) {
                System.out.println("There was no quantifier option selected!");
                errors = true;
            }

            switch (selectedQuantifier) {
                case "1":
                    break;
                case "0 or 1":
                    quantifier.setDesc(selectedQuantifier);
                    quantifier.setSymbol("?");
                    break;
                case "0 or more":
                    quantifier.setDesc(selectedQuantifier);
                    quantifier.setSymbol("*");
                    break;
                case "1 or more":
                    quantifier.setDesc(selectedQuantifier);
                    quantifier.setSymbol("+");
                    break;
                case "Exactly":
                    try {
                        int exactAmount = Integer.parseInt(quantifierExactlyField.getText());
                        quantifier.setDesc("Exactly " + exactAmount);
                        quantifier.setSymbol("{" + exactAmount + "}");
                    }
                    catch (Exception e) {
                        System.out.println("Please fill in the appropriate quantifier field with a valid integer");
                        errors = true;
                    }
                break;
                case "From               to               (inclusive)":
                    try {
                        int from = Integer.parseInt(quantifierRangeFirstField.getText());
                        int to = Integer.parseInt(quantifierRangeLastField.getText());
                        quantifier.setDesc("From " + from + " to " + to + " (inclusive)");
                        quantifier.setSymbol("{" + from + "," + to + "}");
                    }
                    catch (Exception e) {
                        System.out.println("Please fill in the appropriate quantifier fields with a valid integer");
                        errors = true;
                    }
                    break;
                case "            or more":
                    try {
                        int minAmount = Integer.parseInt(quantifierMinField.getText());
                        quantifier.setDesc(minAmount + " or more");
                        quantifier.setSymbol("{" + minAmount + ",}");
                    }
                    catch (Exception e) {
                        System.out.println("Please fill in the appropriate quantifier field with a valid integer");
                        errors = true;
                    }
                    break;
                default:
                    System.out.println("Error while finding selected quantifier");
                    errors = true;
            }
        }

        if (!errors) {
            Element e = new Element("wassup", "[a-zA-Z0-9]", new Quantifier("hey", ""));
            elements.add(e);

            Expression ex = new Expression(elements, quantifier, group);
            mainController.expressionListView.getItems().add(mainController.getSelectedIndex(), ex);
            mainController.expressions.add(mainController.getSelectedIndex(), ex);
            mainController.refreshExpression();

            Stage stage = (Stage) saveExpressionBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void selectedGroup(ActionEvent actionEvent) {
        String selected = ((RadioButton) groupToggle.getSelectedToggle()).getText();

        quantifierPane.setDisable(selected.equals("None"));
    }

}
