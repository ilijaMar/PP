// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementOptionFirst extends Assigning {

    private DesignatorZaDodelu DesignatorZaDodelu;
    private Assignop Assignop;
    private Expr Expr;

    public DesignatorStatementOptionFirst (DesignatorZaDodelu DesignatorZaDodelu, Assignop Assignop, Expr Expr) {
        this.DesignatorZaDodelu=DesignatorZaDodelu;
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorZaDodelu getDesignatorZaDodelu() {
        return DesignatorZaDodelu;
    }

    public void setDesignatorZaDodelu(DesignatorZaDodelu DesignatorZaDodelu) {
        this.DesignatorZaDodelu=DesignatorZaDodelu;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementOptionFirst(\n");

        if(DesignatorZaDodelu!=null)
            buffer.append(DesignatorZaDodelu.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementOptionFirst]");
        return buffer.toString();
    }
}
