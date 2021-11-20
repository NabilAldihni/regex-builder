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
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

public class MainWindowController {
    // Non-FXML variables
    private ArrayList<Expression> expressions;
    private int selectedIndex;

    // FXML Elements
    public Label expressionLabel;
    public TextField fullRegexField;
    public ListView<Expression> expressionListView;
    public Expression ex;
    public Button addExpressionBtn;
    public Button removeExpressionBtn;
    public Button moveUpBtn;
    public Button moveDownBtn;
    public Button editExpressionBtn;
    public Button helpBtn;
    public CheckBox firstElementCheckBox;
    public CheckBox lastElementCheckBox;
    
    @FXML
    /**
     * Method called by FXML when the window is started
     */
    public void initialize() {
        // Updated mainWindowController in the StageConfig class so the editor window controller can later access it
        StageConfig.setMainWindowController(this);
        expressions = new ArrayList<Expression>();
        selectedIndex = 0;

        // Cell Factory to allow the ListView to store whole Expression objects while displaying a full description of the expression
        expressionListView.setCellFactory(new Callback<ListView<Expression>, ListCell<Expression>>() {
            @Override
            public ListCell<Expression> call(ListView<Expression> listView) {
                ListCell<Expression> cell = new ListCell<Expression>(){
                    @Override
                    protected void updateItem(Expression e, boolean empty){
                        super.updateItem(e, empty);
                        String fullDesc = "";
                        if (e != null) {
                            if (e.getGroup().getDesc().equals("")){
                                fullDesc += "Not a capture group\n";
                            }
                            else {
                                fullDesc += e.getGroup().getDesc() + " (" + e.getQuantifier().getDesc().toLowerCase() + ") containing:\n";
                            }
                            
                            for (Element element : e.getElements()) {
                                fullDesc += "\t" + element.getDesc() + "\n";
                                fullDesc += "\t\t" + element.getQuantifier().getDesc() + "\n";
                            }
                            
                            setText(fullDesc);
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
    
    /**
     * Getter used to access the list of expressions
     * @return arraylist of expressions
     */
    public ArrayList<Expression> getExpressions() {
        return expressions;
    }
    
    /**
     * Used to change the current expressions in the list of expressions
     * @param expressions
     */
    public void setExpressions(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }
    
    /**
     * Getter used to access the selected item index
     * @return the index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    /**
     * Used to change the current selectedIndex in the list of expressions
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    
    /**
     * Called every time a change is made to the expressions list. Updates the string in the textField
     * @return A string with the full expression
     */
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
    
    /**
     * Called when the add expression button is pressed (FXML event listener)
     * @param actionEvent
     */
    public void pressedAddExpression(ActionEvent actionEvent) {
        // Stores the selected index so the created expression is added in the correct place
        if (expressionListView.getSelectionModel().getSelectedIndex() == -1) {
            selectedIndex = 0;
        }
        else {
            selectedIndex = expressionListView.getSelectionModel().getSelectedIndex() + 1;
        }

        // Opens the Editor Window
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/editorWindow.fxml"));
            Stage editorStage = new Stage();
            editorStage.setTitle("Regex builder - Expression editor");
            editorStage.setScene(new Scene(root));
            // APPLICATION_MODAL disables this window while the other one is open
            editorStage.initModality(Modality.APPLICATION_MODAL);
            editorStage.setResizable(false);
            editorStage.show();
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("We could not open the window");
            alert.setContentText("There was an error when opening the window, sorry about the inconvenience");
            alert.showAndWait();
        }
    }

    /**
     * Removes selected expression from ListView and ArrayList
     * @param actionEvent
     */
    public void pressedRemoveExpression(ActionEvent actionEvent) {
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        int size = expressionListView.getItems().size();
        expressions.remove(expressionListView.getSelectionModel().getSelectedItem());
        expressionListView.getItems().remove(expressionListView.getSelectionModel().getSelectedItem());
        
        // Automatically selects the expression that takes its place or the last expression
        if (index < size-1) {
            expressionListView.getSelectionModel().select(index);
        }
        else {
            expressionListView.getSelectionModel().select(index-1);
        }
        refreshExpression();
    }

    /**
     * Moves the selected expression up in the list
     * @param actionEvent
     */
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

    /**
     * Moves the selected expression down in the list
     * @param actionEvent
     */
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
    
    /**
     * Duplicates the selected expression
     * @param actionEvent
     */
    public void pressedDuplicateExpression(ActionEvent actionEvent) {
        int index = expressionListView.getSelectionModel().getSelectedIndex();
        
        // Creates a deep copy of the selected object (by value)
        Expression e = new Expression((Expression) expressionListView.getSelectionModel().getSelectedItem());
        expressionListView.getItems().add(index+1, e);
        expressions.add(index+1, e);
    
        expressionListView.getSelectionModel().select(index+1);
        refreshExpression();
    }

    /**
     * Opens the editor window and loads the selected expression's data
     * @param actionEvent
     */
    public void pressedEditExpression(ActionEvent actionEvent) {
        // Stores the selected index so the created expression is added in the correct place
        if (expressionListView.getSelectionModel().getSelectedIndex() == -1) {
            selectedIndex = 0;
        }
        else {
            selectedIndex = expressionListView.getSelectionModel().getSelectedIndex();
        }
        
        Expression item = expressionListView.getSelectionModel().getSelectedItem();
        expressions.remove(expressionListView.getSelectionModel().getSelectedItem());
        expressionListView.getItems().remove(expressionListView.getSelectionModel().getSelectedItem());
        
        // Opens the Editor Window
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/editorWindow.fxml"));
            Stage editorStage = new Stage();
            editorStage.setTitle("Regex builder - Expression editor");
            editorStage.setScene(new Scene(root));
            // APPLICATION_MODAL disables this window while the other one is open
            editorStage.initModality(Modality.APPLICATION_MODAL);
            editorStage.setResizable(false);
            editorStage.show();
            
            // Loads all the appropriate data into the editor window
            EditorWindowController editorController = StageConfig.getEditorWindowController();
            for (Element e: item.getElements()) {
                editorController.getElements().add(e);
                editorController.getElementListView().getItems().add(e);
            }
            
            // Takes the quantifier from the expression
            Quantifier q = item.getQuantifier();
            editorController.setQuantifier(q);
            
            // Creates patterns to match and find the numbers, if they exist
            Pattern exactDigitPattern = Pattern.compile("\\{(\\d+)\\}");
            Pattern rangeDigitPattern = Pattern.compile("\\{(\\d+),(\\d+)\\}");
            Pattern minDigitPattern = Pattern.compile("\\{(\\d+),\\}");
            Matcher mExactDigit = exactDigitPattern.matcher(q.getSymbol());
            Matcher mRangeDigit = rangeDigitPattern.matcher(q.getSymbol());
            Matcher mMinDigit = minDigitPattern.matcher(q.getSymbol());
            
            // Loads the appropriate data into the quantifier radio buttons and text fields
            if (q.getSymbol().equals("?")) {
                editorController.opt0Or1.setSelected(true);
            }
            else if (q.getSymbol().equals("*")) {
                editorController.opt0OrMore.setSelected(true);
            }
            else if (q.getSymbol().equals("+")) {
                editorController.opt1OrMore.setSelected(true);
            }
            else if (mExactDigit.matches()) {
                editorController.optExactly.setSelected(true);
                editorController.quantifierExactlyField.setText(mExactDigit.group(1));
            }
            else if (mRangeDigit.matches()) {
                editorController.optRange.setSelected(true);
                editorController.quantifierRangeFirstField.setText(mRangeDigit.group(1));
                editorController.quantifierRangeLastField.setText(mRangeDigit.group(2));
            }
            else if (mMinDigit.matches()) {
                editorController.optMin.setSelected(true);
                editorController.quantifierMinField.setText(mMinDigit.group(1));
            }
            else {
                editorController.optOne.setSelected(true);
            }
            
            // Loads the appropriate data into the capture group radio buttons
            Group g = item.getGroup();
            editorController.setGroup(g);
            if (g.getStartSymbol().equals("(")) {
                editorController.optCapGroup.setSelected(true);
                editorController.quantifierPane.setDisable(false);
            }
            else if (g.getStartSymbol().equals("(?:")) {
                editorController.optNonCapGroup.setSelected(true);
                editorController.quantifierPane.setDisable(false);
            }
            else if (g.getStartSymbol().equals("(?=")) {
                editorController.optPosLookahead.setSelected(true);
                editorController.quantifierPane.setDisable(false);
            }
            else if (g.getStartSymbol().equals("(?!")) {
                editorController.optNegLookahead.setSelected(true);
                editorController.quantifierPane.setDisable(false);
            }
            editorController.refreshExpression();
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("We could not open the window");
            alert.setContentText("There was an error when opening the window, sorry about the inconvenience");
            alert.showAndWait();
        }
    }

    /**
     * Updates the expression when the checkbox for first element is pressed
     * @param actionEvent
     */
    public void firstElementPressed(ActionEvent actionEvent) {
        refreshExpression();
    }
    
    /**
     * Updates the expression when the checkbox for last element is pressed
     * @param actionEvent
     */
    public void lastElementPressed(ActionEvent actionEvent) {
        refreshExpression();
    }

    /**
     * Copies full expression to clipboard
     * @param actionEvent
     */
    public void copyFullExpression(ActionEvent actionEvent) {
        StringSelection stringSelection = new StringSelection(fullRegexField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    /**
     * Called when the help button is pressed
     * @param actionEvent
     */
    public void pressedHelpBtn(ActionEvent actionEvent) {
        // Opens the help window
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("src/helpWindow.fxml"));
            Stage helpStage = new Stage();
            helpStage.setTitle("Regex builder - Help menu");
            helpStage.setScene(new Scene(root));
            // APPLICATION_MODAL disables this window while the other one is open
            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.setResizable(false);
            helpStage.show();
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("We could not open the window");
            alert.setContentText("There was an error when opening the window, sorry about the inconvenience");
            alert.showAndWait();
        }
    }
}
