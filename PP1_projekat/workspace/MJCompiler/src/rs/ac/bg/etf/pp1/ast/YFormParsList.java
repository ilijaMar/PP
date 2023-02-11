// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class YFormParsList extends FormPars {

    private FormParamsList FormParamsList;

    public YFormParsList (FormParamsList FormParamsList) {
        this.FormParamsList=FormParamsList;
        if(FormParamsList!=null) FormParamsList.setParent(this);
    }

    public FormParamsList getFormParamsList() {
        return FormParamsList;
    }

    public void setFormParamsList(FormParamsList FormParamsList) {
        this.FormParamsList=FormParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParamsList!=null) FormParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParamsList!=null) FormParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParamsList!=null) FormParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YFormParsList(\n");

        if(FormParamsList!=null)
            buffer.append(FormParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YFormParsList]");
        return buffer.toString();
    }
}
