// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class SamoExprList extends ExprListMod {

    private ExprListMod ExprListMod;
    private Expr Expr;

    public SamoExprList (ExprListMod ExprListMod, Expr Expr) {
        this.ExprListMod=ExprListMod;
        if(ExprListMod!=null) ExprListMod.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ExprListMod getExprListMod() {
        return ExprListMod;
    }

    public void setExprListMod(ExprListMod ExprListMod) {
        this.ExprListMod=ExprListMod;
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
        if(ExprListMod!=null) ExprListMod.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprListMod!=null) ExprListMod.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprListMod!=null) ExprListMod.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SamoExprList(\n");

        if(ExprListMod!=null)
            buffer.append(ExprListMod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SamoExprList]");
        return buffer.toString();
    }
}
