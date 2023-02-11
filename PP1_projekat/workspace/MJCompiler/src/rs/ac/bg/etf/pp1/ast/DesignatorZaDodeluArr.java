// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorZaDodeluArr extends DesignatorZaDodelu {

    private DesignatorArr DesignatorArr;

    public DesignatorZaDodeluArr (DesignatorArr DesignatorArr) {
        this.DesignatorArr=DesignatorArr;
        if(DesignatorArr!=null) DesignatorArr.setParent(this);
    }

    public DesignatorArr getDesignatorArr() {
        return DesignatorArr;
    }

    public void setDesignatorArr(DesignatorArr DesignatorArr) {
        this.DesignatorArr=DesignatorArr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArr!=null) DesignatorArr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArr!=null) DesignatorArr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArr!=null) DesignatorArr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorZaDodeluArr(\n");

        if(DesignatorArr!=null)
            buffer.append(DesignatorArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorZaDodeluArr]");
        return buffer.toString();
    }
}
