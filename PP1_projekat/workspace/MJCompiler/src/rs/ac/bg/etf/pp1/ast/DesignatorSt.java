// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorSt extends Statement {

    private DesignatorStatementExpr DesignatorStatementExpr;

    public DesignatorSt (DesignatorStatementExpr DesignatorStatementExpr) {
        this.DesignatorStatementExpr=DesignatorStatementExpr;
        if(DesignatorStatementExpr!=null) DesignatorStatementExpr.setParent(this);
    }

    public DesignatorStatementExpr getDesignatorStatementExpr() {
        return DesignatorStatementExpr;
    }

    public void setDesignatorStatementExpr(DesignatorStatementExpr DesignatorStatementExpr) {
        this.DesignatorStatementExpr=DesignatorStatementExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementExpr!=null) DesignatorStatementExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementExpr!=null) DesignatorStatementExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementExpr!=null) DesignatorStatementExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorSt(\n");

        if(DesignatorStatementExpr!=null)
            buffer.append(DesignatorStatementExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorSt]");
        return buffer.toString();
    }
}
