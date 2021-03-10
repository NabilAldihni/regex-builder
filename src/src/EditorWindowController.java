package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditorWindowController {
    // FXML variables
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
    public TextField elementsCharNotOfField;

    // Non-FXML variables
    private ArrayList<Element> elements;
    private Group group;
    private Quantifier quantifier;

    @FXML
    // Method called by FXML when the window is started
    public void initialize() {
        // Updated editorWindowController in StageConfig class so other controllers can access it
        StageConfig.setEditorWindowController(this);
        
        // Stores the main window controller to update the list later on
        mainController = StageConfig.getMainWindowController();

        elements = new ArrayList<Element>();
        group = new Group();
        quantifier = new Quantifier();
    }

    // Removes selected element when remove button is pressed
    public void pressedRemoveElement(ActionEvent actionEvent) {
    }

    // Moves element up in the list
    public void pressedMoveUp(ActionEvent actionEvent) {
    }

    // Moves element down in the list
    public void pressedMoveDown(ActionEvent actionEvent) {
    }

    // Allows user to edit the element's quantifier
    public void pressedEditElement(ActionEvent actionEvent) {
    }
    
    // Duplicates selected element and adds it below
    public void pressedDuplicateElement(ActionEvent actionEvent) {
    }

    // Called when the user selects a group - if the group is 'None' the quantifier section will be disabled
    public void selectedGroup(ActionEvent actionEvent) {
        String selected = ((RadioButton) groupToggle.getSelectedToggle()).getText();

        quantifierPane.setDisable(selected.equals("None"));
    }

    // Called when any element is added - opens quantifier window
    public void pressedAddElement(ActionEvent actionEvent) {
        String option = ((Button)actionEvent.getSource()).getUserData().toString();
        Element e = new Element();

        if (option.equals("exactly")) {
            e.setDesc("Matches '" + matchesExactlyField.getText() + "' exactly");
            e.setSymbol("(?:" + matchesExactlyField.getText() + ")");
        }
        else if (option.equals("digitRange")) {
            e.setDesc("Digit from " + elementRangeMinField.getText() + " to " + elementRangeMaxField.getText());
            e.setSymbol("[" + elementRangeMinField.getText() + "-" + elementRangeMaxField.getText() + "]");
        }
        else if (option.equals("anyCharOf")) {
            e.setDesc("Any char of '" + elementsCharOfField.getText() + "'");
            e.setSymbol("[" + elementsCharOfField.getText() + "]");
        }
        else if (option.equals("anyCharNotOf")) {
            e.setDesc("Any char not of '" + elementsCharNotOfField.getText() + "'");
            e.setSymbol("[^" + elementsCharNotOfField.getText() + "]");
        }
        else {
            e.setDesc("Other");
            e.setSymbol(option);
        }

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/quantifierWindow.fxml"));
            Stage editorStage = new Stage();
            editorStage.setTitle("Regex builder - Select Quantifier");
            editorStage.setScene(new Scene(root));
            editorStage.initModality(Modality.APPLICATION_MODAL);
            editorStage.show();
        }
        catch (IOException exception){
            System.out.println("Failed to create a new window");
        }
    }

    // Called when "Save" button is pressed
    // Collects all the selected data and creates an expression that is then added to the main window's list
    public void saveExpressionPressed(ActionEvent actionEvent) {
        boolean errors = false;

        // Find selected group
        String selectedGroup = "";
        try {
            selectedGroup = ((RadioButton) groupToggle.getSelectedToggle()).getUserData().toString();
        }
        catch (Exception e) {
            System.out.println("No group option was selected!");
            errors = true;
        }

        // Populates the properties of the group according to selection
        switch (selectedGroup) {
            case "none":
                break;
            case "capGroup":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(");
                group.setEndSymbol(")");
                break;
            case "nonCapGroup":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(?:");
                group.setEndSymbol(")");
                break;
            case "posLook":
                group.setDesc(selectedGroup);
                group.setStartSymbol("(?=");
                group.setEndSymbol(")");
                break;
            case "negLook":
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
                selectedQuantifier = ((RadioButton) quantifierToggle.getSelectedToggle()).getUserData().toString();
            } catch (Exception e) {
                System.out.println("There was no quantifier option selected!");
                errors = true;
            }

            // Populates the properties of the quantifier according to the selection
            switch (selectedQuantifier) {
                case "1":
                    break;
                case "01":
                    quantifier.setDesc("0 or 1");
                    quantifier.setSymbol("?");
                    break;
                case "0+":
                    quantifier.setDesc("0 or more");
                    quantifier.setSymbol("*");
                    break;
                case "1+":
                    quantifier.setDesc("1 or more");
                    quantifier.setSymbol("+");
                    break;
                case "exact":
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
                case "range":
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
                case "minAmount":
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
            Element e = new Element("testDesc", "[a-zA-Z0-9]", new Quantifier("testDesc2", ""));
            elements.add(e);

            int index = mainController.getSelectedIndex();
            // Adds created expression to main controller's ListView and ArrayList
            Expression ex = new Expression(elements, quantifier, group);
            mainController.expressionListView.getItems().add(index, ex);
            mainController.expressions.add(index, ex);
            
            // Selects the added expression in the ListView
            if (index == 0) {
            mainController.expressionListView.getSelectionModel().select(index);
            
            mainController.refreshExpression();

            // Closes the window and returns to main window
            Stage stage = (Stage) saveExpressionBtn.getScene().getWindow();
            stage.close();
        }
    }
}
