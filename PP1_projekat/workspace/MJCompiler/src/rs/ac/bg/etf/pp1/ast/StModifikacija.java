// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class StModifikacija extends Assigning {

    private DesignatorZaDodelu DesignatorZaDodelu;
    private ExprListMod ExprListMod;

    public StModifikacija (DesignatorZaDodelu DesignatorZaDodelu, ExprListMod ExprListMod) {
        this.DesignatorZaDodelu=DesignatorZaDodelu;
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.setParent(this);
        this.ExprListMod=ExprListMod;
        if(ExprListMod!=null) ExprListMod.setParent(this);
    }

    public DesignatorZaDodelu getDesignatorZaDodelu() {
        return DesignatorZaDodelu;
    }

    public void setDesignatorZaDodelu(DesignatorZaDodelu DesignatorZaDodelu) {
        this.DesignatorZaDodelu=DesignatorZaDodelu;
    }

    public ExprListMod getExprListMod() {
        return ExprListMod;
    }

    public void setExprListMod(ExprListMod ExprListMod) {
        this.ExprListMod=ExprListMod;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.accept(visitor);
        if(ExprListMod!=null) ExprListMod.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.traverseTopDown(visitor);
        if(ExprListMod!=null) ExprListMod.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorZaDodelu!=null) DesignatorZaDodelu.traverseBottomUp(visitor);
        if(ExprListMod!=null) ExprListMod.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StModifikacija(\n");

        if(DesignatorZaDodelu!=null)
            buffer.append(DesignatorZaDodelu.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprListMod!=null)
            buffer.append(ExprListMod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StModifikacija]");
        return buffer.toString();
    }
}
