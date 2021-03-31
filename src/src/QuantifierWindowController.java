package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class QuantifierWindowController {
    // FXML variables
    public ToggleGroup quantifierGroup;
    public TextField quantifierExactlyField;
    public TextField quantifierRangeFirstField;
    public TextField quantifierRangeLastField;
    public TextField quantifierMinField;
    public Button applyQuantifierBtn;
    public RadioButton optOne;
    public RadioButton opt0OrMore;
    public RadioButton opt1OrMore;
    public RadioButton optExactly;
    public RadioButton optRange;
    public RadioButton optMin;
    public RadioButton opt0Or1;
    
    // Non-FXML variables
    private Quantifier quantifier;
    private Element element;
    private EditorWindowController editorController;
    
    @FXML
    // Method called by FXML when the window is started
    public void initialize() {
        StageConfig.setQuantifierWindowController(this);
        editorController = StageConfig.getEditorWindowController();
        quantifier = new Quantifier();
    }
    
    public Quantifier getQuantifier() {
        return quantifier;
    }
    
    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }
    
    public Element getElement() {
        return element;
    }
    
    public void setElement(Element element) {
        this.element = element;
    }
    
    // Called when apply button is pressed - saves quantifier
    public void pressedApplyBtn(ActionEvent actionEvent) {
        boolean errors = false;
        String selectedQuantifier = "";
        
        try {
            selectedQuantifier = ((RadioButton) quantifierGroup.getSelectedToggle()).getUserData().toString();
        } catch (Exception e) {
            System.out.println("There was no quantifier option selected!");
            errors = true;
        }
    
        // Populates the properties of the quantifier according to the selection
        switch (selectedQuantifier) {
            case "1":
                quantifier.setDesc("");
                quantifier.setSymbol("");
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
    
        if (!errors) {
            int index = editorController.getSelectedIndex();
            // Adds created element to its appropriate place in the editor window
            element.setQuantifier(quantifier);
            editorController.elementListView.getItems().add(index, element);
            editorController.getElements().add(index, element);
            editorController.refreshExpression();
            
            // Selects the created element in the listview
            editorController.elementListView.getSelectionModel().select(index);
            
            // Closes window
            Stage stage = (Stage) applyQuantifierBtn.getScene().getWindow();
            stage.close();
        }
    }
}
