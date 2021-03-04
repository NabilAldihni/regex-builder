package src;

public class Group {
    private String desc;
    private String startSymbol;
    private String endSymbol;

    public Group() {
        this.desc = "";
        this.startSymbol = "";
        this.endSymbol = "";
    }

    public Group(String desc, String startSymbol, String endSymbol) {
        this.desc = desc;
        this.startSymbol = startSymbol;
        this.endSymbol = endSymbol;
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
