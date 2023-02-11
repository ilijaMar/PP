// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class SwapperStatement extends Statement {

    private DesignatorIdentArr DesignatorIdentArr;
    private Expr Expr;
    private Expr Expr1;

    public SwapperStatement (DesignatorIdentArr DesignatorIdentArr, Expr Expr, Expr Expr1) {
        this.DesignatorIdentArr=DesignatorIdentArr;
        if(DesignatorIdentArr!=null) DesignatorIdentArr.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
    }

    public DesignatorIdentArr getDesignatorIdentArr() {
        return DesignatorIdentArr;
    }

    public void setDesignatorIdentArr(DesignatorIdentArr DesignatorIdentArr) {
        this.DesignatorIdentArr=DesignatorIdentArr;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Expr getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr Expr1) {
        this.Expr1=Expr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorIdentArr!=null) DesignatorIdentArr.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(Expr1!=null) Expr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwapperStatement(\n");

        if(DesignatorIdentArr!=null)
            buffer.append(DesignatorIdentArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwapperStatement]");
        return buffer.toString();
    }
}
