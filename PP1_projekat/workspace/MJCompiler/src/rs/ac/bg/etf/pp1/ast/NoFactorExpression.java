// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public class NoFactorExpression extends FactorExpr {

    public NoFactorExpression () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoFactorExpression(\n");

        buffer.append(tab);
        buffer.append(") [NoFactorExpression]");
        return buffer.toString();
    }
}