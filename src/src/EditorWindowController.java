package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public TextField matchesExactlyField;
    public TextField elementRangeMinField;
    public TextField elementRangeMaxField;
    public TextField elementsCharOfField;
    public TextField elementsCharNotOfField;
    public ListView elementListView;
    public RadioButton optNoCapGroup;
    public RadioButton optCapGroup;
    public RadioButton optNonCapGroup;
    public RadioButton optPosLookahead;
    public RadioButton optNegLookahead;
    public RadioButton optOne;
    public RadioButton opt0Or1;
    public RadioButton opt0OrMore;
    public RadioButton opt1OrMore;
    public RadioButton optExactly;
    public RadioButton optRange;
    public RadioButton optMin;
    
    // Non-FXML variables
    private ArrayList<Element> elements;
    private Group group;
    private Quantifier quantifier;
    private int selectedIndex;

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
        selectedIndex = 0;
    
        // Cell Factory to allow the ListView to store Element objects while displaying only a string of the expression
        elementListView.setCellFactory(new Callback<ListView<Element>, ListCell<Element>>() {
            @Override
            public ListCell<Element> call(ListView<Element> listView) {
                ListCell<Element> cell = new ListCell<Element>(){
                    @Override
                    protected void updateItem(Element e, boolean empty){
                        super.updateItem(e, empty);
                        if (e != null) {
                            setText(e.getSymbol() + e.getQuantifier().getSymbol());
                        }
                        else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        
        String selected = ((RadioButton) groupToggle.getSelectedToggle()).getText();
    
        quantifierPane.setDisable(selected.equals("None"));
    }
    
    public ListView getElementListView() {
        return elementListView;
    }
    
    public void setElementListView(ListView elementListView) {
        this.elementListView = elementListView;
    }
    
    public ArrayList<Element> getElements() {
        return elements;
    }
    
    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public Quantifier getQuantifier() {
        return quantifier;
    }
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    
    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }
    
    // Called every time a change is made to the expressions list. Updates the string in the textField
    public String refreshExpression() {
        expressionField.setText("");
        String fullExp = "";
        
        fullExp += group.getStartSymbol();
        
        for (Element e: elements) {
            fullExp += e.getSymbol();
            fullExp += e.getQuantifier().getSymbol();
        }
        
        fullExp += group.getEndSymbol();
        
        expressionField.setText(fullExp);
        
        return fullExp;
    }
    
    // Removes selected element when remove button is pressed
    public void pressedRemoveElement(ActionEvent actionEvent) {
        int index = elementListView.getSelectionModel().getSelectedIndex();
        int size = elementListView.getItems().size();
        elements.remove(elementListView.getSelectionModel().getSelectedItem());
        elementListView.getItems().remove(elementListView.getSelectionModel().getSelectedItem());
    
        // Automatically selects the expression that takes its place or the last expression
        if (index < size-1) {
            elementListView.getSelectionModel().select(index);
        }
        else {
            elementListView.getSelectionModel().select(index-1);
        }
        refreshExpression();
    }

    // Moves element up in the list
    public void pressedMoveUp(ActionEvent actionEvent) {
        Element item = (Element) elementListView.getSelectionModel().getSelectedItem();
        int index = elementListView.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            elementListView.getItems().remove(index);
            elementListView.getItems().add(index-1, item);
            elementListView.getSelectionModel().select(index-1);
            elements.remove(index);
            elements.add(index-1, item);
        }
        refreshExpression();
    }

    // Moves element down in the list
    public void pressedMoveDown(ActionEvent actionEvent) {
        Element item = (Element) elementListView.getSelectionModel().getSelectedItem();
        int index = elementListView.getSelectionModel().getSelectedIndex();
        if (index < elementListView.getItems().size() - 1) {
            elementListView.getItems().remove(index);
            elementListView.getItems().add(index+1, item);
            elementListView.getSelectionModel().select(index+1);
            elements.remove(index);
            elements.add(index+1, item);
        }
        refreshExpression();
    }

    // Allows user to edit the element's quantifier
    public void pressedEditElement(ActionEvent actionEvent) {
        // Stores the selected index so the created element is added in the correct place
        if (elementListView.getSelectionModel().getSelectedIndex() == -1) {
            selectedIndex = 0;
        }
        else {
            selectedIndex = elementListView.getSelectionModel().getSelectedIndex();
        }
        
        Element item = (Element) elementListView.getSelectionModel().getSelectedItem();
        elements.remove(elementListView.getSelectionModel().getSelectedItem());
        elementListView.getItems().remove(elementListView.getSelectionModel().getSelectedItem());
        
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/quantifierWindow.fxml"));
            Stage quantifierStage = new Stage();
            quantifierStage.setTitle("Regex builder - Select Quantifier");
            quantifierStage.setScene(new Scene(root));
            quantifierStage.initModality(Modality.APPLICATION_MODAL);
            quantifierStage.setResizable(false);
            quantifierStage.show();
        
            QuantifierWindowController quantifierController = StageConfig.getQuantifierWindowController();
            quantifierController.setElement(item);
            
            // Takes the quantifier from the expression
            Quantifier q = item.getQuantifier();
            quantifierController.setQuantifier(q);
    
            // Creates patterns to match and find the numbers, if they exist
            Pattern exactDigitPattern = Pattern.compile("\\{(\\d+)\\}");
            Pattern rangeDigitPattern = Pattern.compile("\\{(\\d+),(\\d+)\\}");
            Pattern minDigitPattern = Pattern.compile("\\{(\\d+),\\}");
            Matcher mExactDigit = exactDigitPattern.matcher(q.getSymbol());
            Matcher mRangeDigit = rangeDigitPattern.matcher(q.getSymbol());
            Matcher mMinDigit = minDigitPattern.matcher(q.getSymbol());
    
            // Loads the appropriate data into the quantifier radio buttons and text fields
            if (q.getSymbol().equals("?")) {
                quantifierController.opt0Or1.setSelected(true);
            }
            else if (q.getSymbol().equals("*")) {
                quantifierController.opt0OrMore.setSelected(true);
            }
            else if (q.getSymbol().equals("+")) {
                quantifierController.opt1OrMore.setSelected(true);
            }
            else if (mExactDigit.matches()) {
                quantifierController.optExactly.setSelected(true);
                quantifierController.quantifierExactlyField.setText(mExactDigit.group(1));
            }
            else if (mRangeDigit.matches()) {
                quantifierController.optRange.setSelected(true);
                quantifierController.quantifierRangeFirstField.setText(mRangeDigit.group(1));
                quantifierController.quantifierRangeLastField.setText(mRangeDigit.group(2));
            }
            else if (mMinDigit.matches()) {
                quantifierController.optMin.setSelected(true);
                quantifierController.quantifierMinField.setText(mMinDigit.group(1));
            }
            else {
                quantifierController.optOne.setSelected(true);
            }
    
        }
        catch (IOException exception){
            System.out.println("Failed to create a new window");
        }
    }
    
    // Duplicates selected element and adds it below
    public void pressedDuplicateElement(ActionEvent actionEvent) {
        int index = elementListView.getSelectionModel().getSelectedIndex();
        elementListView.getItems().add(index+1, elementListView.getSelectionModel().getSelectedItem());
        elements.add((Element) elementListView.getSelectionModel().getSelectedItem());
        
        elementListView.getSelectionModel().select(index+1);
        refreshExpression();
    }

    // Called when the user selects a group - if the group is 'None' the quantifier section will be disabled
    public void selectedGroup(ActionEvent actionEvent) {
        String selected = ((RadioButton) groupToggle.getSelectedToggle()).getText();

        quantifierPane.setDisable(selected.equals("None"));
        refreshExpression();
    }

    // Called when any element is added - opens quantifier window
    public void pressedAddElement(ActionEvent actionEvent) {
        String option = ((Button)actionEvent.getSource()).getUserData().toString();
        Element e = new Element();
        
        // Stores the selected index so the created element is added in the correct place
        if (elementListView.getSelectionModel().getSelectedIndex() == -1) {
            selectedIndex = 0;
        }
        else {
            selectedIndex = elementListView.getSelectionModel().getSelectedIndex() + 1;
        }

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
            Stage quantifierStage = new Stage();
            quantifierStage.setTitle("Regex builder - Select Quantifier");
            quantifierStage.setScene(new Scene(root));
            quantifierStage.initModality(Modality.APPLICATION_MODAL);
            quantifierStage.setResizable(false);
            quantifierStage.show();
            
            QuantifierWindowController quantifierController = StageConfig.getQuantifierWindowController();
            quantifierController.setElement(e);
        }
        catch (IOException exception){
            System.out.println("Failed to create a new window");
        }
    }

    // Called when "Save" button is pressed
    // Collects all the selected data and creates an expression that is then added to the main window's list
    public void pressedSaveExpression(ActionEvent actionEvent) {
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
                group.setDesc("");
                group.setStartSymbol("");
                group.setEndSymbol("");
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
        if (!selectedGroup.equals("none")) {
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
        }
        else {
            quantifier.setSymbol("");
            quantifier.setDesc("");
        }

        if (!errors) {
            int index = mainController.getSelectedIndex();
            // Adds created expression to main controller's ListView and ArrayList
            Expression ex = new Expression(elements, quantifier, group);
            mainController.expressionListView.getItems().add(index, ex);
            mainController.getExpressions().add(index, ex);
            
            // Selects the added expression in the ListView
            mainController.expressionListView.getSelectionModel().select(index);
            
            mainController.refreshExpression();

            // Closes the window and returns to main window
            Stage stage = (Stage) saveExpressionBtn.getScene().getWindow();
            stage.close();
        }
    }
}
