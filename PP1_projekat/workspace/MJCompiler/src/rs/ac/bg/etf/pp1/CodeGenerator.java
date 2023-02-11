package rs.ac.bg.etf.pp1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPC;
	
	public int getMainPC() {
		return mainPC;
	}
	Stack<Integer> stackElemenata = new Stack<Integer>();
	Stack<Obj> stackElemenataObj = new Stack<Obj>();
	Stack<SyntaxNode> stackElemenataNode = new Stack<SyntaxNode>();
	
	
	Stack<Obj> stackElemenataObjMod = new Stack<Obj>();
	Stack<Integer> stackElemenataIntMod = new Stack<Integer>();
	int flager=0;
	//////////////////////////////////////////////////////////////STEJTMENT//////////////////////////////////////////////////////////////////////
	
	public void visit(PrintSt printSt) { //print naredba na steku ocekuje sta ispisujemo i sirinu na koju ispisujemo
		Struct st = printSt.getExpr().struct;
		Struct boolStruct = new Struct(Struct.Bool);
		if(st.getKind() == Struct.Int || st.getKind() == Struct.Bool){
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else if (st.getKind() == Struct.Array && st.getElemType() == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else{
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	public void visit(PrintStNum print) {
		Code.loadConst(print.getN2());
		Struct boolStruct = new Struct(Struct.Bool);
		if(print.getExpr().struct == Tab.intType || print.getExpr().struct == boolStruct) {
			Code.put(Code.print);
		}
		else {
			Code.put(Code.bprint);
		}
	}
	
	public void visit(ReadSt singleStatementRead) {
		if (singleStatementRead.getReadDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(singleStatementRead.getReadDesignator().obj);
	} 
	
	
	
	
	//////////////////////////////////////////////////////////////METODE//////////////////////////////////////////////////////////////////////
	//moramo da znamo gde pocinje prva instrukcija metode (PC) 
	//pre poziva metode prvo na ExpStack stavljamo arugmente meto de
	//mi objektnom cvoru koji predstavljam metodu postavljamo adresu prve instrukcije, i to radimo kada visitujemo cvor koji kreira objektni cvor
	public void visit(MethodNameVoid methodNameVoid) {
		
		methodNameVoid.obj.setAdr(Code.pc);
		
		if("main".equalsIgnoreCase(methodNameVoid.getMethodName())){
			mainPC = Code.pc;
		}
		
		Code.put(Code.enter);
		Code.put(methodNameVoid.obj.getLevel());
		Code.put(methodNameVoid.obj.getLocalSymbols().size());
	}
	public void visit(MethodNameType methodNameType) {
		
		methodNameType.obj.setAdr(Code.pc);	
		
		Code.put(Code.enter);
		Code.put(methodNameType.obj.getLevel());
		Code.put(methodNameType.obj.getLocalSymbols().size());
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnStRet singleStatementRet) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	
	public void visit(ReturnStExpr singleStatementRetExpr) {// expr je vec na steku
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	//////////////////////////////////////////////////////////////FACTOR//////////////////////////////////////////////////////////////////////
	
	
	public void visit(FactorNum numConst) { //stavljanje konstante na ExpStek
		Obj cnst = Tab.insert(Obj.Con, "$", Tab.intType);
		cnst.setLevel(0);
		cnst.setAdr(numConst.getN1());
		
		Code.load(cnst);
		
		//Code.loadConst(9);
		if(flager==1) {
			stackElemenataObjMod.push(null);
			stackElemenataIntMod.push(numConst.getN1());
		}
		
	}
	
	public void visit(FactorChar factor) {
		Code.loadConst(factor.getC1());// stavljanje const na stek
	}

	@Override
	public void visit(FactorBool factor) {
		//Code.loadConst((factor.getB1().equals("true")) ? 1 : 0);// stavljanje const na stek
		String trueOrFalse = factor.getB1();
		Struct boolStruct = new Struct(Struct.Bool);
		Obj toLoad = Tab.insert(Obj.Con, trueOrFalse, boolStruct);
		
		if(trueOrFalse.equals("true")) {
			toLoad.setAdr(1); //mark true
		}
		else {
			toLoad.setAdr(0); //mark false
		}
		
		toLoad.setLevel(0);
		Code.load(toLoad);
	}
	
	public void visit(FactorNew factorNew) {
		if(factorNew.getFactorExpr() instanceof FactorExpr) {
			Code.put(Code.newarray);
			if(factorNew.getType().struct == Tab.charType) {
				Code.put(0);
			}
			else {
				Code.put(1);
			}
		}
	}
	
	public void visit(FactorDesignator factorDes) {
		//Code.loadConst(8);
		if(flager==1) {
			stackElemenataObjMod.push(factorDes.getDesignatorExpr().obj);
			stackElemenataIntMod.push(0);
		}
		//Code.load(factorDes.getDesignatorExpr().obj);
	}
	public void visit(ObicanDesignator obican) {
		Code.load(obican.getDesignatorArr().obj);
		
		//Code.loadConst(7);
		if(flager==1) {
			stackElemenataObjMod.push(obican.getDesignatorArr().obj);
			stackElemenataIntMod.push(0);
		}
	}
	
	
	//////////////////////////////////////////////////////////////DESIGNATOR//////////////////////////////////////////////////////////////////////
	
	
	
	
	public void visit(DesignatorStatementOptionFirst designatorAssignop) {
		Code.store(designatorAssignop.getDesignatorZaDodelu().obj);// Expr je na steku i izracunat; samo treba ubaciti u Design
	}
	
	public void visit(DesignatorExpr designatorExpr) {
		SyntaxNode parent = designatorExpr.getParent();
		
		if(parent instanceof DesignatorStatementOptionFirst) {
			//designator je sa leve strane i ne radim nista
			
		} else if(parent instanceof StModifikacija) {
			flager=1;
		}
		else {
			if(designatorExpr.obj.getKind()==Obj.Con) {
				Code.loadConst(designatorExpr.obj.getAdr()); 		//designator je sa desne strane i konstanta je
			}else {
				Code.load(designatorExpr.obj); //designator je sa desne strane i promenljiva je je
			}
			 
			//Code.load(designatorExpr.obj.getAdr()); 
		}
		
		
	}
	public void visit(DesignatorStatementOptionPlusPlus designator) {
		
		SyntaxNode parent=designator.getParent();
		Obj cvor=null;
		if(parent instanceof DesignatorStatementFirst) {
			cvor=((DesignatorStatementFirst)parent).getDesignatorExpr().obj;
		}else if(parent instanceof DesignatorStatementThird){
			cvor=((DesignatorStatementThird)parent).getDesignatorArr().obj;
		}
		
		if (cvor.getKind() == Obj.Elem) {
			Code.put(Code.dup2);// ako je niz load ce pokupiti adr i index, a onda store nece imati...
		} 
		Code.load(cvor);// inc design
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(cvor);
	}
	
	public void visit(DesignatorStatementOptionMinusMinus designator) {
		
		SyntaxNode parent=designator.getParent();
		Obj cvor=null;
		if(parent instanceof DesignatorStatementFirst) {
			cvor=((DesignatorStatementFirst)parent).getDesignatorExpr().obj;
		}else if(parent instanceof DesignatorStatementThird){
			cvor=((DesignatorStatementThird)parent).getDesignatorArr().obj;
		}
		
		if (cvor.getKind() == Obj.Elem) {
			Code.put(Code.dup2);// ako je niz load ce pokupiti adr i index, a onda store nece imati...
		} 
		Code.load(cvor);// inc design
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(cvor);
	}
	
	public void visit(DesignatorIdentArr designatorArr) {
		Code.load(designatorArr.obj);
	}
	
	
	//////////////////////////////////////////////////////////////TERM//////////////////////////////////////////////////////////////////////
	
	
	
	public void visit(AddExpression addExp) {
		if(addExp.getAddop() instanceof Add) {
			Code.put(Code.add);
		}
		else {
			Code.put(Code.sub);
		}
	}
	
	public void visit(MonkeyExpression MonkeyExp) {
		Code.put(Code.add);
		Code.put(Code.dup);
		Code.put(Code.mul);
	}
	public void visit(NegativeTermExpression negExpr) {
		Code.put(Code.neg);
		
	}
	
	public void visit(Term_List termMul) {
		if(termMul.getMulop() instanceof Multiply) {
			Code.put(Code.mul);
		}
		else if(termMul.getMulop() instanceof Divide) {
			Code.put(Code.div);
		}
		else {
			Code.put(Code.rem);
		}
	}
	
	//DesignatorArr
	public void visit(DesignatorArr nizDodela) {
		Obj niz = nizDodela.getDesignatorIdentArr().obj;
		
		
		
		//niz
		//index
		//broje lemenata niza
		
		Code.put(Code.dup);
		Code.load(niz);
		Code.put(Code.arraylength);
			
		Integer fixJmp = Code.pc;
		
		Code.putFalseJump(Code.gt, 0);
		Code.put(Code.trap);
		Code.fixup(fixJmp + 1);
		
		//Code.put(Code.pop);
	}
	
	
	
	public void visit(DesignatorStatementSecond kobasica) {
		
		Obj nizDesnaStrana = kobasica.getDesignatorIdentArr().obj;
		int brojElSaLeve=stackElemenata.size();
		
		
		Code.put(Code.pop);
		
		Code.loadConst(brojElSaLeve);
		Code.load(nizDesnaStrana);
		Code.put(Code.arraylength);
			
		Integer fixJmp = Code.pc;
		
		Code.putFalseJump(Code.gt, 0);
		Code.put(Code.trap);
		Code.fixup(fixJmp + 1);
		
		
		for(int i = brojElSaLeve-1; i >= 0; i--) {
			
			int vrh=stackElemenata.pop();
			Obj vrhObj=stackElemenataObj.pop();
			SyntaxNode node=stackElemenataNode.pop();
			
			if(vrh==0) continue;
			else if(vrh==1) {
				Code.load(nizDesnaStrana);
				Code.loadConst(i);
				Code.put(Code.aload);
				Code.store(vrhObj);
			}
			else if(vrh==2) {
				//while()
				Code.load(nizDesnaStrana);
				Code.loadConst(i);
				Code.put(Code.aload);
				Code.put(Code.astore);
				
			}
			
		}
		stackElemenata = new Stack<Integer>();
		stackElemenataObj=new Stack<Obj>();
		stackElemenataNode=new Stack<SyntaxNode>();
	}
	
	
	
	public void visit(NoDesignator noDesig) {
		stackElemenata.push(0);
		stackElemenataObj.push(null);
		stackElemenataNode.push(null);
		
		
	}
	
	public void visit(YesDesignatorIdent yesDesig) {
		stackElemenata.push(1);
		stackElemenataObj.push(yesDesig.obj);
		stackElemenataNode.push(yesDesig);
	}
	
	public void visit(YesDesignatorArr arrDesig) {
		
		stackElemenata.push(2);
		stackElemenataObj.push(arrDesig.obj);
		stackElemenataNode.push(arrDesig);
	}
	
	

	///////////////////////////////////////////////////////////////////MODIFIKACIJA JANUAR///////////////////////////////////
	
	
	
	int brojElSaDesne=0;
	public void visit(StModifikacija modifikacija) {
		Obj nizLevaStrana = modifikacija.getDesignatorZaDodelu().obj;
		
		//Code.put(Code.pop);
		//Code.loadConst(11);
		//Code.loadConst(brojElSaDesne);
		//Code.loadConst(11);
		for(int i = brojElSaDesne-1; i >= 0; i--) {
			
			
			
			
			//Code.loadConst(11);
			Code.load(nizLevaStrana);//niz
			Code.put(Code.dup_x1);
			Code.loadConst(i);//index
			Code.put(Code.dup_x2);//vrednost
			
			
			
			Code.put(Code.pop);
			Code.put(Code.pop);
			
			Code.put(Code.astore);
			
			
		}
		brojElSaDesne=0;
		//Code.put(Code.pop);
		
	}
	
	public void visit(SamoExpr exp) {
		brojElSaDesne+=1;
	}
	public void visit(SamoExprList exp) {
		brojElSaDesne+=1;
	}
	
	///////////////////////////////////////////////////////////////////MODIFIKACIJA GOTO///////////////////////////////////
	
	private Map<String,Integer> statementLabels=new HashMap<>();	
	private Map<String,List<Integer>> gotoAdrs=new HashMap<>();	
		
	
	public void visit(Label labelColon)
	{	
		SyntaxNode parent=labelColon.getParent();
		if(parent instanceof GotoStatement) {
			return;
		}
		Code.loadConst(11);
		statementLabels.put(labelColon.getI1(), Code.pc);	
		if(gotoAdrs.containsKey(labelColon.getI1()))
			while(gotoAdrs.get(labelColon.getI1()).size()>0)		
				Code.fixup(gotoAdrs.get(labelColon.getI1()).remove(0));
			
	}	
	public void visit(GotoStatement statement_goto)
	{
		if(statementLabels.containsKey(statement_goto.getLabel().getI1()))
		Code.putJump(statementLabels.get(statement_goto.getLabel().getI1()));
		else
		{
			Code.putJump(0);
			
			int wrongOffset=Code.pc-2;
			
			List<Integer> list;
			
			if(gotoAdrs.containsKey(statement_goto.getLabel().getI1()))

				list=gotoAdrs.get(statement_goto.getLabel().getI1());

			else
			{
				list=new ArrayList<Integer>();
				
				gotoAdrs.put(statement_goto.getLabel().getI1(), list);
			}
			list.add(wrongOffset);
		}
	}
	
	
	public void visit(SwapperStatement statement_swap) {
		
		Obj tmp=new Obj(Obj.Var,"Tmp", Tab.intType);
		Obj niz=statement_swap.getDesignatorIdentArr().obj;
		/*
		Code.put(Code.dup2);
		Code.put(Code.dup2);
		Code.put(Code.pop);
		Code.load(tmp);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.load(niz);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.aload);
		Code.store(tmp);
		
		Code.put(Code.pop);
		Code.load(niz);
		Code.put(Code.dup_x2);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.aload);
		Code.put(Code.astore);
		
		Code.load(niz);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.load(tmp);
		Code.put(Code.astore);
		
		Code.put(Code.pop);
		Code.put(Code.pop);
		*/
		
		Code.load(niz);
		Code.put(Code.dup2);
		Code.put(Code.pop);
		Code.put(Code.aload);
		
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		
		Code.put(Code.dup);
		Code.load(niz);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.aload);
		
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.load(niz);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		
		Code.put(Code.astore);
		Code.put(Code.astore);
		
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	



}
