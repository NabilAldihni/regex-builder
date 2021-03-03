package src;

public class Quantifier {
    private String desc;
    private String symbol;

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
