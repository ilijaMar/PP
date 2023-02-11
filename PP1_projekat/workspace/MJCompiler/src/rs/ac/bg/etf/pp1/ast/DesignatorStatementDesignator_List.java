// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementDesignator_List extends DesignatorStatementDesignatorList {

    private DesignatorStatementDesignatorList DesignatorStatementDesignatorList;
    private OptionalDesignator OptionalDesignator;

    public DesignatorStatementDesignator_List (DesignatorStatementDesignatorList DesignatorStatementDesignatorList, OptionalDesignator OptionalDesignator) {
        this.DesignatorStatementDesignatorList=DesignatorStatementDesignatorList;
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.setParent(this);
        this.OptionalDesignator=OptionalDesignator;
        if(OptionalDesignator!=null) OptionalDesignator.setParent(this);
    }

    public DesignatorStatementDesignatorList getDesignatorStatementDesignatorList() {
        return DesignatorStatementDesignatorList;
    }

    public void setDesignatorStatementDesignatorList(DesignatorStatementDesignatorList DesignatorStatementDesignatorList) {
        this.DesignatorStatementDesignatorList=DesignatorStatementDesignatorList;
    }

    public OptionalDesignator getOptionalDesignator() {
        return OptionalDesignator;
    }

    public void setOptionalDesignator(OptionalDesignator OptionalDesignator) {
        this.OptionalDesignator=OptionalDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.accept(visitor);
        if(OptionalDesignator!=null) OptionalDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.traverseTopDown(visitor);
        if(OptionalDesignator!=null) OptionalDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.traverseBottomUp(visitor);
        if(OptionalDesignator!=null) OptionalDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementDesignator_List(\n");

        if(DesignatorStatementDesignatorList!=null)
            buffer.append(DesignatorStatementDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalDesignator!=null)
            buffer.append(OptionalDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementDesignator_List]");
        return buffer.toString();
    }
}
