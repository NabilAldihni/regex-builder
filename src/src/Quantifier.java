package src;

public class Quantifier {
    private String desc;
    private String symbol;
    
    // Empty constructor so objects can be instantiated in the controller classes without knowing the properties
    public Quantifier() {
        this.desc = "";
        this.symbol = "";
    }
    
    // Constructor with parameters so objects can be instantiated when the properties are already known
    public Quantifier(String desc, String symbol) {
        this.desc = desc;
        this.symbol = symbol;
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
}
