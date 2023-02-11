// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class Act_Pars extends ActParsList {

    private ActParsItem ActParsItem;

    public Act_Pars (ActParsItem ActParsItem) {
        this.ActParsItem=ActParsItem;
        if(ActParsItem!=null) ActParsItem.setParent(this);
    }

    public ActParsItem getActParsItem() {
        return ActParsItem;
    }

    public void setActParsItem(ActParsItem ActParsItem) {
        this.ActParsItem=ActParsItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsItem!=null) ActParsItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsItem!=null) ActParsItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsItem!=null) ActParsItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Act_Pars(\n");

        if(ActParsItem!=null)
            buffer.append(ActParsItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Act_Pars]");
        return buffer.toString();
    }
}
