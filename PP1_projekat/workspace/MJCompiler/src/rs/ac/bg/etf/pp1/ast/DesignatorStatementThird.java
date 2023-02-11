// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementThird extends DesignatorStatementExpr {

    private DesignatorArr DesignatorArr;
    private DesignatorStatementOptions DesignatorStatementOptions;

    public DesignatorStatementThird (DesignatorArr DesignatorArr, DesignatorStatementOptions DesignatorStatementOptions) {
        this.DesignatorArr=DesignatorArr;
        if(DesignatorArr!=null) DesignatorArr.setParent(this);
        this.DesignatorStatementOptions=DesignatorStatementOptions;
        if(DesignatorStatementOptions!=null) DesignatorStatementOptions.setParent(this);
    }

    public DesignatorArr getDesignatorArr() {
        return DesignatorArr;
    }

    public void setDesignatorArr(DesignatorArr DesignatorArr) {
        this.DesignatorArr=DesignatorArr;
    }

    public DesignatorStatementOptions getDesignatorStatementOptions() {
        return DesignatorStatementOptions;
    }

    public void setDesignatorStatementOptions(DesignatorStatementOptions DesignatorStatementOptions) {
        this.DesignatorStatementOptions=DesignatorStatementOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArr!=null) DesignatorArr.accept(visitor);
        if(DesignatorStatementOptions!=null) DesignatorStatementOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArr!=null) DesignatorArr.traverseTopDown(visitor);
        if(DesignatorStatementOptions!=null) DesignatorStatementOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArr!=null) DesignatorArr.traverseBottomUp(visitor);
        if(DesignatorStatementOptions!=null) DesignatorStatementOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementThird(\n");

        if(DesignatorArr!=null)
            buffer.append(DesignatorArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementOptions!=null)
            buffer.append(DesignatorStatementOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementThird]");
        return buffer.toString();
    }
}
