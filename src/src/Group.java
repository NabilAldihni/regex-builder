package src;

public class Group {
    private String desc;
    private String startSymbol;
    private String endSymbol;
    
    /**
     * Empty constructor so objects can be instantiated in the controller classes without knowing the properties
     */
    public Group() {
        this.desc = "";
        this.startSymbol = "";
        this.endSymbol = "";
    }
    
    /**
     * Constructor with parameters so objects can be instantiated when the properties are already known
     * @param desc
     * @param startSymbol
     * @param endSymbol
     */
    public Group(String desc, String startSymbol, String endSymbol) {
        this.desc = desc;
        this.startSymbol = startSymbol;
        this.endSymbol = endSymbol;
    }
    
    /**
     * Constructor used for deep copies
     * @param g
     */
    public Group(Group g) {
        this(g.getDesc(), g.getStartSymbol(), g.getEndSymbol());
    }
    
    /**
     * Gets the description of the group
     * @return the description
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Sets the description of the group
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * Gets the starting symbol of the expression
     * @return the start symbol
     */
    public String getStartSymbol() {
        return startSymbol;
    }
    
    /**
     * Gets the starting symbol of the expression
     * @param startSymbol
     */
    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }
    
    /**
     * Gets the ending symbol of the expression
     * @return the end symbol
     */
    public String getEndSymbol() {
        return endSymbol;
    }
    
    /**
     * Gets the ending symbol of the expression
     * @param endSymbol
     */
    public void setEndSymbol(String endSymbol) {
        this.endSymbol = endSymbol;
    }
}
