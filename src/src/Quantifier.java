package src;

public class Quantifier {
    private String desc;
    private String symbol;
    
    /**
     * Empty constructor so objects can be instantiated in the controller classes without knowing the properties
     */
    public Quantifier() {
        this.desc = "";
        this.symbol = "";
    }
    
    /**
     * Constructor with parameters so objects can be instantiated when the properties are already known
     * @param desc
     * @param symbol
     */
    public Quantifier(String desc, String symbol) {
        this.desc = desc;
        this.symbol = symbol;
    }
    
    /**
     * Constructor used for deep copies
     * @param q
     */
    public Quantifier(Quantifier q) {
        this(q.getDesc(), q.getSymbol());
   }
    
    /**
     * Gets the description of the quantifier
     * @return the description
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Sets the description of the quantifier
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * Gets the symbol of the quantifier
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Sets the symbol of the quantifier
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
