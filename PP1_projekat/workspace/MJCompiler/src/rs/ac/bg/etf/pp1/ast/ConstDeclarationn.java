// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationn extends ConstDecl {

    private ConstType ConstType;
    private ConstIdent ConstIdent;
    private ConstIdents ConstIdents;

    public ConstDeclarationn (ConstType ConstType, ConstIdent ConstIdent, ConstIdents ConstIdents) {
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
        this.ConstIdent=ConstIdent;
        if(ConstIdent!=null) ConstIdent.setParent(this);
        this.ConstIdents=ConstIdents;
        if(ConstIdents!=null) ConstIdents.setParent(this);
    }

    public ConstType getConstType() {
        return ConstType;
    }

    public void setConstType(ConstType ConstType) {
        this.ConstType=ConstType;
    }

    public ConstIdent getConstIdent() {
        return ConstIdent;
    }

    public void setConstIdent(ConstIdent ConstIdent) {
        this.ConstIdent=ConstIdent;
    }

    public ConstIdents getConstIdents() {
        return ConstIdents;
    }

    public void setConstIdents(ConstIdents ConstIdents) {
        this.ConstIdents=ConstIdents;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstType!=null) ConstType.accept(visitor);
        if(ConstIdent!=null) ConstIdent.accept(visitor);
        if(ConstIdents!=null) ConstIdents.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
        if(ConstIdent!=null) ConstIdent.traverseTopDown(visitor);
        if(ConstIdents!=null) ConstIdents.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        if(ConstIdent!=null) ConstIdent.traverseBottomUp(visitor);
        if(ConstIdents!=null) ConstIdents.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationn(\n");

        if(ConstType!=null)
            buffer.append(ConstType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstIdent!=null)
            buffer.append(ConstIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstIdents!=null)
            buffer.append(ConstIdents.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationn]");
        return buffer.toString();
    }
}
