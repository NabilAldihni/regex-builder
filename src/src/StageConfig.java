package src;

public class StageConfig {
    private static MainWindowController mainWindowController = null;
    private static EditorWindowController editorWindowController = null;
    private static QuantifierWindowController quantifierWindowController = null;

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public static EditorWindowController getEditorWindowController() {
        return editorWindowController;
    }

    public static QuantifierWindowController getQuantifierWindowController() {
        return quantifierWindowController;
    }

    public static void setQuantifierWindowController(QuantifierWindowController quantifierWindowController) {
        StageConfig.quantifierWindowController = quantifierWindowController;
    }

    public static void setMainWindowController(MainWindowController mwc) {
        StageConfig.mainWindowController = mwc;
    }

    public static void setEditorWindowController(EditorWindowController ewc) {
        StageConfig.editorWindowController = ewc;
    }
}
