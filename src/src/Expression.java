package src;

import java.util.ArrayList;

public class Expression {
    private ArrayList<Element> elements;
    private Quantifier quantifier;
    private Group group;

    public Expression(ArrayList<Element> elements, Quantifier quantifier, Group group) {
        this.elements = elements;
        this.quantifier = quantifier;
        this.group = group;
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
