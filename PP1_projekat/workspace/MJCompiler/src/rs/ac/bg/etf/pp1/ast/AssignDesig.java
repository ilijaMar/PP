// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class AssignDesig extends Statement {

    private Assigning Assigning;

    public AssignDesig (Assigning Assigning) {
        this.Assigning=Assigning;
        if(Assigning!=null) Assigning.setParent(this);
    }

    public Assigning getAssigning() {
        return Assigning;
    }

    public void setAssigning(Assigning Assigning) {
        this.Assigning=Assigning;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assigning!=null) Assigning.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assigning!=null) Assigning.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assigning!=null) Assigning.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignDesig(\n");

        if(Assigning!=null)
            buffer.append(Assigning.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignDesig]");
        return buffer.toString();
    }
}
