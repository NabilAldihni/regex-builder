package src;

public class Group {
    private String desc;
    private String startSymbol;
    private String endSymbol;
    
    // Empty constructor so objects can be instantiated in the controller classes without knowing the properties
    public Group() {
        this.desc = "";
        this.startSymbol = "";
        this.endSymbol = "";
    }
    
    // Constructor with parameters so objects can be instantiated when the properties are already known
    public Group(String desc, String startSymbol, String endSymbol) {
        this.desc = desc;
        this.startSymbol = startSymbol;
        this.endSymbol = endSymbol;
    }
    
    // Class constructor used for deep copies
    public Group(Group g) {
        this(g.getDesc(), g.getStartSymbol(), g.getEndSymbol());
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    public String getEndSymbol() {
        return endSymbol;
    }

    public void setEndSymbol(String endSymbol) {
        this.endSymbol = endSymbol;
    }
}
