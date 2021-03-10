package src;

public class Element {
    private String desc;
    private String symbol;
    private Quantifier quantifier;

    // Empty constructor so objects can be instantiated in the controller classes without knowing the properties
    public Element() {
        this.desc = "";
        this.symbol = "";
        this.quantifier = null;
    }

    // Constructor with parameters so objects can be instantiated when the properties are already known
    public Element(String desc, String symbol, Quantifier quantifier) {
        this.desc = desc;
        this.symbol = symbol;
        this.quantifier = quantifier;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }
}
