package src;

import java.util.ArrayList;

public class Expression {
    private ArrayList<Element> elements;
    private Quantifier quantifier;
    private Group group;

    // Class constructor
    public Expression(ArrayList<Element> elements, Quantifier quantifier, Group group) {
        this.elements = elements;
        this.quantifier = quantifier;
        this.group = group;
    }
    
    public Expression(Expression e) {
        elements = new ArrayList<Element>();
        for (Element element: e.getElements()) {
            this.elements.add(new Element(element));
        }
        this.quantifier = new Quantifier(e.getQuantifier());
        this.group = new Group(e.getGroup());
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void addElement(Element element)
    {
        this.elements.add(element);
    }

    public void removeElement(Element element)
    {
        this.elements.remove(element);
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    // Join the different parts of the expression and returns a single string that represents the whole expression
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
