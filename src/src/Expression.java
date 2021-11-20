package src;

import java.util.ArrayList;

public class Expression {
    private ArrayList<Element> elements;
    private Quantifier quantifier;
    private Group group;

    /**
     * Class constructor
     * @param elements
     * @param quantifier
     * @param group
     */
    public Expression(ArrayList<Element> elements, Quantifier quantifier, Group group) {
        this.elements = elements;
        this.quantifier = quantifier;
        this.group = group;
    }
    
    /**
     * Class constructor used for deep copies
     * @param e
     */
    public Expression(Expression e) {
        elements = new ArrayList<Element>();
        for (Element element: e.getElements()) {
            this.elements.add(new Element(element));
        }
        this.quantifier = new Quantifier(e.getQuantifier());
        this.group = new Group(e.getGroup());
    }
    
    /**
     * Gets the list of elements that make up the expression
     * @return list of elements
     */
    public ArrayList<Element> getElements() {
        return elements;
    }
    
    /**
     * Gets the quantifier for the expression
     * @return the quantifier
     */
    public Quantifier getQuantifier() {
        return quantifier;
    }
    
    /**
     * Sets the quantifier for the expression
     * @param quantifier
     */
    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }
    
    /**
     * Gets the group of the expression
     * @return the group
     */
    public Group getGroup() {
        return group;
    }
    
    /**
     * Sets the group for the expression
     * @param group
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
    /**
     * Joins the different parts of the expression and returns a single string that represents the whole expression
     * @return
     */
    public String compileExpression()
    {
        String e = "";
        e += this.group.getStartSymbol();
        for (Element element : this.elements)
        {
            e += element.getSymbol();
            e += element.getQuantifier().getSymbol();
        }
        e += this.group.getEndSymbol();
        e += this.quantifier.getSymbol();
 
        return e;
    }
}
