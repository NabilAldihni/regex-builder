package src;

public class Element {
    private String desc;
    private String symbol;
    private Quantifier quantifier;

    /**
     * Empty constructor so objects can be instantiated in the controller classes without knowing the properties
     */
    public Element() {
        this.desc = "";
        this.symbol = "";
        this.quantifier = null;
    }

    /**
     * Constructor with parameters so objects can be instantiated when the properties are already known
     * @param desc
     * @param symbol
     * @param quantifier
     */
    public Element(String desc, String symbol, Quantifier quantifier) {
        this.desc = desc;
        this.symbol = symbol;
        this.quantifier = quantifier;
    }
    
    /**
     * Class constructor used for deep copies
     * @param e
     */
    public Element(Element e) {
        this(e.getDesc(), e.getSymbol(), new Quantifier(e.getQuantifier()));
    }
    
    /**
     * Gets the description of the element
     * @return the description
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Sets the description of the element
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * Gets the symbol for the element
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Sets the symbol for the element
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Gets the quantifier for the element
     * @return the quantifier
     */
    public Quantifier getQuantifier() {
        return quantifier;
    }
    
    /**
     * Sets the quantifier for the element
     * @param quantifier
     */
    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }
}
