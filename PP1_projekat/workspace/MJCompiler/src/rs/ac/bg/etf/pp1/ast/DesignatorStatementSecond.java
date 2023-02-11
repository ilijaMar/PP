// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementSecond extends DesignatorStatementExpr {

    private DesignatorStatementDesignatorList DesignatorStatementDesignatorList;
    private DesignatorIdentArr DesignatorIdentArr;

    public DesignatorStatementSecond (DesignatorStatementDesignatorList DesignatorStatementDesignatorList, DesignatorIdentArr DesignatorIdentArr) {
        this.DesignatorStatementDesignatorList=DesignatorStatementDesignatorList;
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.setParent(this);
        this.DesignatorIdentArr=DesignatorIdentArr;
        if(DesignatorIdentArr!=null) DesignatorIdentArr.setParent(this);
    }

    public DesignatorStatementDesignatorList getDesignatorStatementDesignatorList() {
        return DesignatorStatementDesignatorList;
    }

    public void setDesignatorStatementDesignatorList(DesignatorStatementDesignatorList DesignatorStatementDesignatorList) {
        this.DesignatorStatementDesignatorList=DesignatorStatementDesignatorList;
    }

    public DesignatorIdentArr getDesignatorIdentArr() {
        return DesignatorIdentArr;
    }

    public void setDesignatorIdentArr(DesignatorIdentArr DesignatorIdentArr) {
        this.DesignatorIdentArr=DesignatorIdentArr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.accept(visitor);
        if(DesignatorIdentArr!=null) DesignatorIdentArr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.traverseTopDown(visitor);
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementDesignatorList!=null) DesignatorStatementDesignatorList.traverseBottomUp(visitor);
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementSecond(\n");

        if(DesignatorStatementDesignatorList!=null)
            buffer.append(DesignatorStatementDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorIdentArr!=null)
            buffer.append(DesignatorIdentArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementSecond]");
        return buffer.toString();
    }
}
