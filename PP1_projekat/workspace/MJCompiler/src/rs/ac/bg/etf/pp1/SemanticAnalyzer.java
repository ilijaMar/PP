package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());

	private boolean has_main;
	boolean has_return = false;
	boolean is_void = false;

	int varCount;

	Struct boolStruct = new Struct(Struct.Bool);
	Struct arrayStruct = new Struct(Struct.Array);
	private Struct currentType = null;
	private Struct currentTypeDesignator = null;

	int constValue = 0;
	private Struct constType;
	private int currentMethodParsCount = 0;
	private int currClassNumberOfFileds = 0;
	Obj currentMethod = null;
	Struct currentClass = null;

	private Boolean hasDesignatorList = null;
	private ArrayList<Struct> currentPars = null;
	private ArrayList<Struct> formParamsList = null;

	private int whileCnt = 0;

	// List<Struct> formParamsList;
	public SemanticAnalyzer() {
		super();
		Tab.insert(Obj.Type, "bool", boolStruct);
		//Tab.insert(Obj.Type, "array", arrayStruct);

	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	public boolean passed() {
		return !errorDetected;
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- PROGRAM
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Program program) {
		varCount = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if (!has_main) {
			//errorDetected = true;
			//log.error("Program nema main");
			report_error("Program nema main",program);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- TYPE
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());

		if (typeNode == Tab.noObj) {

			type.struct = Tab.noType;
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);

		} else {

			if (typeNode.getKind() == Obj.Type) {

				currentType = typeNode.getType();
				type.struct = typeNode.getType();

			} else {

				currentType = Tab.noType;
				type.struct = Tab.noType;
				report_error("Error: " + type.getTypeName() + " ne predstavlja tip", type);

			}
		}

	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * -----------------------------------------------------------------
	 * DEKLA_GLOB_CONST
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	/*
	 * public void visit(ConstDeclarationn constDecl) { currentType = null; }
	 * 
	 * public void visit(ConstType constType) { currentType = constType.struct;
	 * constType.struct = constType.getType().struct;
	 * 
	 * }
	 */
	public void visit(CharConst charConst) {
		constValue = charConst.getC1();
		constType = Tab.intType;
	}

	public void visit(NumConst numConst) {
		constValue = numConst.getN1();
		constType = Tab.intType;
		// report_info("CONST VALUE " + numConst.getN1() + " IS BEING USED!", numConst);
	}

	public void visit(BoolConst boolConst) {
		constType = boolStruct;
		if (boolConst.getB1().equals("false")) {
			constValue = 0;
		} else if (boolConst.getB1().equals("true")) {
			constValue = 1;
		} else {
			report_error("Bool mora biti true ili false ", null);
		}
	}
	public void visit(ConstType constType) {
		constType.struct = constType.getType().struct;
		currentType = constType.struct;
	}
	
	public void visit(ConstDeclarationn constDecl) {
		currentType = null;
	}
	public void visit(ConstIdentt constIdent) {
		report_info("Prepoznao konstantu: " + constIdent.getConstName(), constIdent);

		Obj constName = Tab.find(constIdent.getConstName());
		if (constName != Tab.noObj) {

			report_error("Konstanta " + constIdent.getConstName() + " je vec definisana", constIdent);

		} else if (!constType.assignableTo(currentType)) {
			report_error("POGRESAN TIP KONSTANTE ", null);
		} else {
			constName.setAdr(constValue);
			constIdent.obj = Tab.insert(Obj.Con, constIdent.getConstName(), currentType);
			constIdent.obj.setAdr(constValue);
		}

	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * -----------------------------------------------------------------
	 * DEKLA_GLOB_PROM
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(VarDecl varDecl) {
		currentType = null;
	}

	public void visit(VarType var_Type) {
		var_Type.struct = var_Type.getType().struct;
		currentType = var_Type.struct;

	}

	public void visit(Var_Ident var_ident) {

		Obj varName = null;
		Struct tip_niz = new Struct(Struct.Array, currentType);

		if (currentMethod != null || currentClass != null) {// local
			varName = Tab.currentScope().findSymbol(var_ident.getVarIdent());
			// report_info("Prepoznao LOKALNU promenljivu:",null);
		} else {// global
			varName = Tab.find(var_ident.getVarIdent());
			// report_info("Prepoznao GLOBALNU promenljivu:",null);
		}

		if (varName == Tab.noObj || varName == null) {

			currClassNumberOfFileds++;
			if (!(var_ident.getArrayDecl() instanceof NoArrayDeclaration)) {
				if (currentClass == null) {// obicna promenljiva
					var_ident.obj = Tab.insert(Obj.Var, var_ident.getVarIdent(), tip_niz);

				} else { // polje klase
					var_ident.obj = Tab.insert(Obj.Fld, var_ident.getVarIdent(), tip_niz);
					// varName.setLevel(2);
				}

				// varName.setFpPos(-1);
			} else {
				if (currentClass == null) {// obicna promenljiva
					var_ident.obj = Tab.insert(Obj.Var, var_ident.getVarIdent(), currentType);

				} else { // polje klase
					var_ident.obj = Tab.insert(Obj.Fld, var_ident.getVarIdent(), currentType);
					// varName.setLevel(2);
				}

				// varName.setFpPos(-1);
			}
		} else {
			report_error("Promenljiva " + var_ident.getVarIdent() + " je vec definisana", var_ident);
		}

		report_info("Prepoznao promenljivu:" + var_ident.getVarIdent() + "  trenutni tip je: " + currentType.getKind(), var_ident);
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * -----------------------------------------------------------------
	 * METODE_KONTEKST
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(MethodDeclaration methodDecl) {

		currentMethod.setLevel(currentMethodParsCount);
		currentMethodParsCount = 0;
		if (!has_return && currentMethod.getType() != Tab.noType) {// ako nije void
			report_error("Metoda: " + currentMethod.getName() + " nema return", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		has_return = false;
		is_void = false;
		currentMethod = null;
	}

	public void visit(ReturnStExpr r) {

		Struct tipMetode = currentMethod.getType();
		Struct tipExpr = r.getExpr().struct;
		// report_info("Tip metode je: "+currentMethod.getType().getKind()+" a tip
		// povratne vrednosti je: " + r.getExpr().struct.getKind(),r);
		if (!currentMethod.getType().equals(r.getExpr().struct)) {// ne poklapa se return tip sa tipom metode
			report_error("Ne poklapa se return tip sa tipom metode", r);
		}
		has_return = true;
	}

	public void visit(ReturnStRet r) {
		if (currentMethod.getType() != Tab.noType) {// int f() {return ;) greska
			report_error("Metoda mora biti void", null);
		}
		has_return = true;
	}

	public void visit(MethodNameType methodNameType) {
		Obj methodName = Tab.find(methodNameType.getMethodName());

		if (methodName == Tab.noObj) {
			methodNameType.obj = Tab.insert(Obj.Meth, methodNameType.getMethodName(), methodNameType.getType().struct);
			currentMethod = methodNameType.obj;
			Tab.openScope();
		} else {
			report_error("Greska: Metoda " + methodNameType.getMethodName() + " je vec deklarisana", methodNameType);
		}
		report_info("Prepoznao TYPE metodu:" + methodNameType.getMethodName(), methodNameType);
	}

	public void visit(MethodNameVoid methodNameVoid) {
		Obj methodName = Tab.find(methodNameVoid.getMethodName());

		is_void = true;

		if (methodName == Tab.noObj) {
			methodNameVoid.obj = Tab.insert(Obj.Meth, methodNameVoid.getMethodName(), Tab.noType);
			currentMethod = methodNameVoid.obj;
			Tab.openScope();
			if (methodNameVoid.getMethodName().equals("main")) {
				has_main = true;
			}
		} else {
			report_error("Greska: Metoda " + methodNameVoid.getMethodName() + " je vec deklarisana", methodNameVoid);
		}
		report_info("Prepoznao VOID metodu:" + methodNameVoid.getMethodName(), methodNameVoid);
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * -----------------------------------------------------------------
	 * FORMALNI_PARAMETRI_KONTEKST
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	public void visit(FormParsArray formParItem) {
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("Formalni parametar " + formParItem.getVarN() + " nije u metodi ", formParItem);
			
		} else {// localno
			varObj= Tab.currentScope().findSymbol(formParItem.getVarN());
		}
		if(currentType==Tab.noType) {
			
			report_error("Formalni parametar" + formParItem.getVarN() + " nema tip ", formParItem);
		}
		else if(varObj==null ||varObj== Tab.noObj) {//vec postoji
			
			varObj = Tab.insert(Obj.Var, formParItem.getVarN(), new Struct(Struct.Array,currentType));
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);				
			report_info("Formalni paramtear " + varObj.getName() + " je koriscen", formParItem);
		}
		else {
			report_error("Formalni parametar " + formParItem.getVarN() + " vec definisan ", formParItem);
		}
	}
	
	public void visit(FormParsNoArray formParItem){
		Obj varObj= null;
		
		if(currentMethod==null) {//globalno
			report_error("Formalni parametar " + formParItem.getVarN() + " nije u metodi ", formParItem);
			
		} else {// localno
			
			varObj= Tab.currentScope().findSymbol(formParItem.getVarN());
		}
		
		if(currentType==Tab.noType) {
			
			report_error("Formalni parametar" + formParItem.getVarN() + " nema tip ", formParItem);
			
		}
		else if(varObj==null ||varObj== Tab.noObj) {//vec postoji
			
			varObj = Tab.insert(Obj.Var, formParItem.getVarN(), currentType);
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);
			report_info("Formalni paramtear " + varObj.getName() + " je koriscen: ", formParItem);
		}
		else {
			report_error("Formalni parametar " + formParItem.getVarN() + " vec definisan ", formParItem);
		}
	}
	
	public void visit(FormParsArrayList formParItem){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("Formalni parametar " + formParItem.getVarN() + " nije u metodi ", formParItem);
			
		} else {// localno
			
			varObj= Tab.currentScope().findSymbol(formParItem.getVarN());
		}
		if(currentType==Tab.noType) {
			report_error("Formalni parametar" + formParItem.getVarN() + " nema tip ", formParItem);
		}
		else if(varObj==null ||varObj== Tab.noObj) {//vec postoji
			
			varObj = Tab.insert(Obj.Var, formParItem.getVarN(), new Struct(Struct.Array,currentType));
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);				
			report_info("Formalni paramtear " + varObj.getName() + " je koriscen: ", formParItem);
		}
		else {
			report_error("Formalni parametar " + formParItem.getVarN() + " vec definisan ", formParItem);
		}
	}
	
	public void visit(FormParsNoArrayList  formParItem){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("Formalni parametar " + formParItem.getVarN() + " nije u metodi ", formParItem);
			
		} else {// localno
			
			varObj= Tab.currentScope().findSymbol(formParItem.getVarN());
		}
		if(currentType==Tab.noType) {
			report_error("Formalni parametar" + formParItem.getVarN() + " nema tip ", formParItem);
		}
		else if(varObj==null ||varObj== Tab.noObj) {//vec postoji
			varObj = Tab.insert(Obj.Var, formParItem.getVarN(), currentType);
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);
			report_info("Formalni paramtear " + varObj.getName() + " je koriscen: ", formParItem);
		}
		else {
			report_error("Formalni parametar " + formParItem.getVarN() + " vec definisan ", formParItem);
		}
	}
	
	
	/*
	public void visit(Form_Param formParItem) {
		Struct type = formParItem.getType().struct;
		Obj param = Tab.currentScope().findSymbol(formParItem.getFormParam());

		Struct tip_niz = new Struct(Struct.Array, currentType);

		if (param == null || param == Tab.noObj) { //
			formParItem.obj = Tab.insert(Obj.Var, formParItem.getFormParam(), type);
			formParItem.obj.setFpPos(currentMethodParsCount++);
		} else {
			if (param.getKind() != Obj.Var && param.getKind() != Obj.Con) {
				if (formParItem.getArrayOpt() instanceof NoArrayOpt) {
					formParItem.obj = Tab.insert(Obj.Var, formParItem.getFormParam(), type);
				} else {
					formParItem.obj = Tab.insert(Obj.Var, formParItem.getFormParam(), tip_niz);
				}
				formParItem.obj.setFpPos(currentMethodParsCount++);
			} else {
				report_error("Greska: Formalni parametar " + formParItem.getFormParam() + " je vec deklarisan.",
						formParItem);
			}
		}
		report_info("Prepoznao formalni parametar metode:" + formParItem.getFormParam(), formParItem);
	}
	*/
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * -----------------------------------------------------------------
	 * KLASA_KONTEKST
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(Class_Decl class_Decl) {
		Tab.chainLocalSymbols(currentClass);
		Tab.closeScope();
		currentClass = null;

	}

	public void visit(Class_Name class_name) {
		// Obj typeNode = Tab.find(type.getType().getTypeName());
		Obj className = Tab.find(class_name.getClassName());

		// Struct classStruct=new Struct(4);

		if (className == Tab.noObj) {
			currentClass = new Struct(4);
			className = Tab.insert(Obj.Type, class_name.getClassName(), currentClass);

			Tab.openScope();

		} else {
			report_error("Greska: Klasa " + class_name.getClassName() + " je vec deklarisan.", class_name);
		}

		report_info("Prepoznao klasu :" + class_name.getClassName(), class_name);
	}

	public void visit(YesExtends yes_extends) {
		Obj typeNode = Tab.find(yes_extends.getType().getTypeName());

		if (typeNode == Tab.noObj) {

			// type.struct = Tab.noType;
			report_error("Nije pronadjena klasa " + yes_extends.getType().getTypeName() + " u tabeli simbola", null);

		}
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- EXPRESIONS
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(AddExpression exprAddop) {
		exprAddop.struct = Tab.noType;
		
		if (!exprAddop.getTerm().struct.equals(Tab.intType)) {
			
			exprAddop.struct = Tab.noType;
			report_error("Izraz nije INT", exprAddop);
		} else if (!exprAddop.getExpr().struct.equals(Tab.intType)) {
			
			exprAddop.struct = Tab.noType;
			report_error("Izraz nije INT", exprAddop);
		}
		
		else if (!exprAddop.getExpr().struct.compatibleWith(exprAddop.getTerm().struct)) {
			report_error("Expr nije kompeta. sa termom", exprAddop);
		} else {
			exprAddop.struct = Tab.intType;
		}
	}
	public void visit(MonkeyExpression monkey) {
		monkey.struct = Tab.intType;
	}
	public void visit(NegativeTermExpression expression) {
		expression.struct = expression.getTerm().struct;
	}

	/*
	 * public void visit(Expr expr) { expr.struct = expr.getExpression().struct; }
	 */
	public void visit(TermExpression termExpression) {
		termExpression.struct = termExpression.getTerm().struct;
	}
	
	public void visit(OptionalExpression optionalExpression) {
		optionalExpression.struct = optionalExpression.getExpr().struct;
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- TERM
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(Term term) {
		term.struct = term.getTermList().struct;
	}

	public void visit(TermFactor termFactor) {
		termFactor.struct = termFactor.getFactor().struct;
	}

	public void visit(Term_List termList) {
		/*
		Struct f = termList.getFactor().struct;
		Struct tl = termList.getTermList().struct;
		if (tl.equals(f) && tl == Tab.intType) {
			termList.struct = tl;
			//report_info("Validan termList", termList);
		} else {
			if(f.getKind()==3 || tl.getKind()==3) {
				termList.struct = tl;
			}else {
				report_error("Greska na liniji " + termList.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje.",null);
				
				termList.struct = Tab.noType;
			}
			
		}
		
		*/
		if (!termList.getTermList().struct.equals(Tab.intType)) {
			
			termList.getTermList().struct = Tab.noType;
			report_error("Term nije INT ", termList);
		}
		else if (! termList.getFactor().struct.equals(Tab.intType)) {
			termList.struct = Tab.noType;
			report_error("Factor nije INT ", termList);
		}
		else {
			termList.struct = Tab.intType;
		}
		
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- FACTOR
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(FactorExpression expression) {
		if (expression.getExpr().struct != Tab.intType) {
			report_error("Greska: Tip unutar [] mora biti int", expression);
		}else {
			expression.struct = new Struct(Struct.Array, currentType);
		}
		//expression.struct = expression.getExpr().struct;
	}

	public void visit(FactorDesignator factorDesignator) {
		factorDesignator.struct = factorDesignator.getDesignatorExpr().obj.getType();
	}

	public void visit(FactorNum factorNum) {
		factorNum.struct = Tab.intType;
	}

	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}

	public void visit(FactorBool factorBool) {
		factorBool.struct = boolStruct;
	}

	public void visit(FactorNew factorNew) {
		if (!((FactorExpression)factorNew.getFactorExpr()).getExpr().struct.equals(Tab.intType)) {
			
			factorNew.struct= Tab.noType;
			report_error("Index niza mora biti INT ", factorNew);
    	}
    	else {
    		//factorNew.struct = new Struct(Struct.Array, currentType);
    		factorNew.struct = factorNew.getType().struct;
	   }
		
	}

	public void visit(FactorExpr_ expr) {
		expr.struct = expr.getExpr().struct;
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- DESIGNATOR
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(DesignatorStatementOptionPlusPlus designatorPlus) {
		SyntaxNode parent=designatorPlus.getParent();
		Obj cvor=null;
		if(parent instanceof DesignatorStatementFirst) {
			cvor=((DesignatorStatementFirst)parent).getDesignatorExpr().obj;
		}else if(parent instanceof DesignatorStatementThird){
			cvor=((DesignatorStatementThird)parent).getDesignatorArr().obj;
		}
		if (cvor.getKind() != Obj.Var
				&& cvor.getKind() != Obj.Elem
				&& cvor.getKind() != Obj.Fld)
			
			report_error("Designator: " + cvor.getName()+ " mora biti VAR,ELEM ILI FIELD ", designatorPlus);
					
					
		else if (!cvor.getType().equals(Tab.intType))
				
			report_error("Designator: " + cvor.getName() + " mora biti INT ", designatorPlus);
					
					
		
	}

	public void visit(DesignatorStatementOptionMinusMinus designatorMinus) {
		SyntaxNode parent=designatorMinus.getParent();
		Obj cvor=null;
		if(parent instanceof DesignatorStatementFirst) {
			cvor=((DesignatorStatementFirst)parent).getDesignatorExpr().obj;
		}else if(parent instanceof DesignatorStatementThird){
			cvor=((DesignatorStatementThird)parent).getDesignatorArr().obj;
		}
		
		if (cvor.getKind() != Obj.Var
				&& cvor.getKind() != Obj.Elem
				&& cvor.getKind() != Obj.Fld) {	
				
						
			report_error("Designator: "+ cvor.getName()+ " mora biti VAR,ELEM ILI FIELD ", designatorMinus);
					
					
		} else if (!cvor.getType().equals(Tab.intType))
				
			report_error("Designator: "+ cvor.getName()+ " mora biti INT ", designatorMinus);
					
					
		
	}
	/*
	public void visit(DesignatorStatementOptionFirst designatorAssignop) {
		if (((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj.getKind() != Obj.Var
				&& ((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj
						.getKind() != Obj.Elem
				&& ((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj
						.getKind() != Obj.Fld) {
			report_error("Designator: "
					+ ((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj.getName()
					+ " mora biti VAR,ELEM ILI FIELD ", designatorAssignop);
		} // da li je leva strana dodeljiva desnoj
		else if(((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj.getType().getKind()==3) {
			Struct leva=((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj.getType().getElemType();
			Struct desna=designatorAssignop.getExpr().struct;
			
				//desna=leva;
			if(!desna.assignableTo(leva)) {
				report_error("Leva strana nije dodeljiva desnoj NIZ LEVA:"+leva.getKind()+"   DESNA:"+desna.getKind(), designatorAssignop);
			}
		} else {
			Struct leva=((DesignatorStatementFirst) designatorAssignop.getParent()).getDesignatorExpr().obj.getType();
			Struct desna=designatorAssignop.getExpr().struct;
			if(!desna.assignableTo(leva)) {
				report_error("Leva strana nije dodeljiva desnoj LEVA:"+leva.getKind()+"   DESNA:"+desna.getKind(), designatorAssignop);
			}
		}
		
	}
	*/
	public void visit(DesignatorStatementOptionFirst designatorAssignop) {
		if ( designatorAssignop.getDesignatorZaDodelu().obj.getKind() != Obj.Var
				&& designatorAssignop.getDesignatorZaDodelu().obj.getKind() != Obj.Elem
				&& designatorAssignop.getDesignatorZaDodelu().obj.getKind() != Obj.Fld) {		
				
						
			report_error("Designator: "+ designatorAssignop.getDesignatorZaDodelu().obj.getName()+ " mora biti VAR,ELEM ILI FIELD ", designatorAssignop);
					
					
		} // da li je leva strana dodeljiva desnoj
		else if(designatorAssignop.getDesignatorZaDodelu().obj.getType().getKind()==3) {
			Struct leva=designatorAssignop.getDesignatorZaDodelu().obj.getType().getElemType();
			Struct desna=designatorAssignop.getExpr().struct;
			
				//desna=leva;
			if(!desna.assignableTo(leva)) {
				report_error("Leva strana nije dodeljiva desnoj NIZ LEVA:"+leva.getKind()+"   DESNA:"+desna.getKind(), designatorAssignop);
			}
		} else {
			Struct leva=designatorAssignop.getDesignatorZaDodelu().obj.getType();
			Struct desna=designatorAssignop.getExpr().struct;
			if(!desna.assignableTo(leva)) {
				report_error("Leva strana nije dodeljiva desnoj LEVA:"+leva.getKind()+"   DESNA:"+desna.getKind(), designatorAssignop);
			}
		}
	}
	public void visit(DesignatorZaDodeluExp zaDodeluExp) {
		zaDodeluExp.obj=zaDodeluExp.getDesignatorExpr().obj;
		//((DesignatorStatementOptionFirst)zaDodeluExp.getParent()).getDesignatorZaDodelu().obj=zaDodeluExp.obj;
	}
	public void visit(DesignatorZaDodeluArr zaDodeluArr) {
		zaDodeluArr.obj=zaDodeluArr.getDesignatorArr().obj;
		//((DesignatorStatementOptionFirst)zaDodeluArr.getParent()).getDesignatorZaDodelu().obj=zaDodeluArr.obj;
	}
	public void visit(DesignatorDot designatorDot) {
		Obj fieldNode = Tab.find(designatorDot.getFieldName());
		// Provera da li postoji u klasi
		if (fieldNode != Tab.noObj) {
			designatorDot.struct = fieldNode.getType();
		} else {
			report_error("Greska: Ne postoji identifikator " + designatorDot.getFieldName(), designatorDot);
		}
	}
	
	public void visit(ReadDesigExp zaDodeluExp) {
		zaDodeluExp.obj=zaDodeluExp.getDesignatorExpr().obj;
		//((DesignatorStatementOptionFirst)zaDodeluExp.getParent()).getDesignatorZaDodelu().obj=zaDodeluExp.obj;
	}
	public void visit(ReadDesigArr zaDodeluArr) {
		zaDodeluArr.obj=zaDodeluArr.getDesignatorArr().obj;
		//((DesignatorStatementOptionFirst)zaDodeluArr.getParent()).getDesignatorZaDodelu().obj=zaDodeluArr.obj;
	}
	
	/*
	
	
	
	 */
	public void visit(DesignatorsList designatorsList) {
		if (designatorsList.getDesignator().struct == arrayStruct) {
			if (designatorsList.getDesignatorList() instanceof NoDesignatorList) {
				hasDesignatorList = false;
			} else {
				hasDesignatorList = true;
			}
			designatorsList.struct = arrayStruct;
		}
	}

	public void visit(NoDesignatorList noDesignatorList) {
		noDesignatorList.struct = Tab.noType;
	}

	/*
	 * public void visit(DesignatorName designatorName) { Obj name =
	 * Tab.find(designatorName.getVarName()); designatorName.obj=Tab.noObj; if (name
	 * != Tab.noObj) { designatorName.obj = name; } }
	 */
	public void visit(DesignatorName designatorIdent) {

		Obj desObj = Tab.find(designatorIdent.getVarName());
		
		if (desObj == Tab.noObj) {
			designatorIdent.obj = Tab.noObj;
			report_error("Designator: " + designatorIdent.getVarName()+ " nije definisan ", designatorIdent);
					
		} else if (desObj.getKind() != Obj.Con && desObj.getKind() != Obj.Var && desObj.getKind() != Obj.Meth && desObj.getKind() != Obj.Fld) {
				
			//report_error("TIP PROMENLJIVE JE:" + desObj.getKind(), null);
			//designatorIdent.obj = Tab.noObj;
			report_error("Designator " + designatorIdent.getVarName() + " tip nije dobar ",designatorIdent);
					
		} else {
			designatorIdent.obj = desObj;

			if (designatorIdent.obj.getLevel() == 1
					&& (designatorIdent.obj.getKind() == Obj.Var || designatorIdent.obj.getKind() == Obj.Con)) {
				 //report_info("~~USING DESIGNATOR LOCAL VAR "+designatorIdent.getVarName()+"!",designatorIdent);
				
			} else if (designatorIdent.obj.getLevel() == 0
					&& (designatorIdent.obj.getKind() == Obj.Var || designatorIdent.obj.getKind() == Obj.Con)) {
				 //report_info("~~USING DESIGNATOR GLOBAL VAR "+designatorIdent.getVarName()+"! "+designatorIdent.obj.getKind(), designatorIdent);
				// 
			} else {
			}
		}
	}
	
	public void visit(ObicanDesignator designatorExpression) {
		designatorExpression.struct = designatorExpression.getDesignatorArr().obj.getType();
	}
	
	public void visit(DesignatorExpr designatorExpression) {
		Obj name = Tab.find(designatorExpression.getDesignatorName().getVarName());
		
		if (name == Tab.noObj) {
			
			designatorExpression.obj = Tab.noObj;
			report_error("Greska: Promenljiva " + designatorExpression.getDesignatorName().getVarName()+ " nije deklarisana", designatorExpression);
					
		} else {
			
			designatorExpression.obj = name;
			if (hasDesignatorList == Boolean.FALSE) {
				if (name.getType().getKind() != designatorExpression.getDesignatorList().struct.getKind()) {
					report_error("Greska: Promenljiva " + name.getName() + " nije niz!", designatorExpression);
				} else {
					report_info("Pristup nizu validan", designatorExpression);
				}
			} else if (hasDesignatorList == null) {
				// report_info("Pristup samo identifikatoru."+name.getType().getKind(),
				// designatorExpression);
			} else {
				
			}
		}
		hasDesignatorList = null;
	}

	public void visit(ActParsItem actPars) {
		if (currentPars == null)
			currentPars = new ArrayList();
		currentPars.add(actPars.getExpr().struct);
	}

	public void visit(DesignatorStatementFirst designatorStatementExpression) {
		Boolean flag = false;
		if (designatorStatementExpression.getDesignatorStatementOptions() instanceof DesignatorStatementOptionSecond) {
			String name = designatorStatementExpression.getDesignatorExpr().getDesignatorName().getVarName();
			Obj method = Tab.find(name);
			if (method != Tab.noObj) {

				if (method.getKind() == Obj.Meth) {

					DesignatorStatementOptions designatorStatement = designatorStatementExpression
							.getDesignatorStatementOptions();
					if (designatorStatement instanceof DesignatorStatementOptionSecond) {

						ActParsList actPars = ((DesignatorStatementOptionSecond) designatorStatement).getActParsList();
						if (actPars instanceof ActPars_List || actPars instanceof Act_Pars) {
							// report_error("NASAO METODU", designatorStatementExpression);
							ArrayList<Obj> vars = new ArrayList(method.getLocalSymbols());
							for (int i = 0; i < currentPars.size(); i++) {
								if (currentPars.get(i) == null || i >= method.getLevel()) {
									report_error("Greska: Navedeni argumenti se ne poklapaju sa parametrima funkcije "
											+ name, designatorStatementExpression);
								} else if (!currentPars.get(i).assignableTo(vars.get(i).getType())) {
									report_error("Greska: Stvarni argumenti nisu istog tipa kao formalni",
											designatorStatementExpression);
								} else {
									flag = true;
									// report_info("Uspesan poziv funkcije: " + name, designatorStatement);
								}
							}
						} else if (actPars instanceof NoActParsList && currentPars == null && method.getLevel() == 0) {
							report_info("Uspesan poziv funkcije: " + name, designatorStatement);
						} else {
							report_error("Greska: Navedeni argumenti se ne poklapaju sa parametrima funkcije " + name,
									designatorStatementExpression);

						}
					}
					method.getLocalSymbols();
				}
			} else {
				report_error("Greska: Funkcija " + name + " nije definisana.", designatorStatementExpression);
			}
			if (flag)
				report_info("Uspesan poziv funkcije: " + name, designatorStatementExpression);
			// report_info("Poziv funkcije ", designatorStatementExpression);
			currentPars = null;
		}
	}
	
	
	 public void visit(DesignatorIdentArr designator) {//  NIZ!
		 Obj desObj = Tab.find(designator.getDesAName());
		 
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 report_error("Niz " + designator.getDesAName() + " nije definisan ", designator);
		}
		 else if(desObj.getType().getKind() != Struct.Array || desObj.getKind() != Obj.Var ) {
			 designator.obj= Tab.noObj;
			 report_error("Tip niza " + designator.getDesAName() + " nije dobar "+desObj.getType().getKind()+desObj.getKind(), designator);
		 }
		 else {
			 designator.obj= desObj;
			
		 }
	 }
	 public void visit(DesignatorArr designator) {//  ELEMENT!
		 
		 Obj desObj = designator.getDesignatorIdentArr().obj;
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 report_error("Niz " + designator.getDesignatorIdentArr().getDesAName()+ " vec definisan ", designator);
		}
		 else if(!designator.getExpr().struct.equals(Tab.intType)) {
			 designator.obj= Tab.noObj;
			 report_error("Tip niza " + designator.getDesignatorIdentArr().getDesAName() + " nije dobar ", designator);
		 }
		 else {
			 //designator.obj= new Obj(Obj.Elem,desObj.getName() + "[$]", desObj.getType().getElemType());
			 designator.obj= new Obj(Obj.Elem,desObj.getName(), desObj.getType().getElemType());
			 //report_info("Koristi se element niza " + desObj.getName() + "!", designator);
		 }
	 }
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- STATEMENTS
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public void visit(IfElse singleStatementIf) {
		if (!singleStatementIf.getCondition().struct.equals(boolStruct)) {
			report_error("Uslov " + singleStatementIf.getCondition().struct.getKind() + "mora biti BOOL",
					singleStatementIf);
		}
	}

	public void visit(WhileSt StatementWhile) {
		whileCnt -= 1;
		if (!StatementWhile.getCondition().struct.equals(boolStruct)) {
			report_error("Uslov " + StatementWhile.getCondition().struct.getKind() + "mora biti BOOL",
					StatementWhile);
		}
	}

	public void visit(BreakSt StatementBreak) {
		if (whileCnt == 0) {
			report_error("BREAK mora biti u LOOP", StatementBreak);
		}
	}

	public void visit(ContinueSt StatementContinue) {
		if (whileCnt == 0) {
			report_error("CONTINUE mora biti u LOOP", StatementContinue);
		}
	}

	public void visit(ReadSt StatementRead) {
		if (StatementRead.getReadDesignator().obj.getKind() != Obj.Var
				&& StatementRead.getReadDesignator().obj.getKind() != Obj.Elem
				&& StatementRead.getReadDesignator().obj.getKind() != Obj.Fld) {
			
			report_error(" READ Iskaz mora biti  VAR,ELEM ili FIELD", StatementRead);
			
		} else if (!StatementRead.getReadDesignator().obj.getType().equals(Tab.intType)
				&& !StatementRead.getReadDesignator().obj.getType().equals(Tab.charType)
				&& !StatementRead.getReadDesignator().obj.getType().equals(boolStruct)) {
			report_error(" READ Iskaz mora biti INT,CHAR ili BOOL", StatementRead);
		} 
	}

	public void visit(PrintSt ssPrint) {
		if (!ssPrint.getExpr().struct.equals(Tab.intType) && !ssPrint.getExpr().struct.equals(Tab.charType)
				&& !ssPrint.getExpr().struct.equals(boolStruct)) 
			report_error("PRINT Iskaz mora biti INT,CHAR ili BOOL"+ssPrint.getExpr().struct.getKind(), ssPrint);
		
	}
	
	public void visit(PrintStNum ssPrint) {
		if (!ssPrint.getExpr().struct.equals(Tab.intType) && !ssPrint.getExpr().struct.equals(Tab.charType)
				&& !ssPrint.getExpr().struct.equals(boolStruct)) 
			report_error("PRINT Iskaz mora biti INT,CHAR ili BOOL"+ssPrint.getExpr().struct.getKind(), ssPrint);
		
	}
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- CONDITIONS
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	
	public void visit(ConditionItem conditionItem) {
		conditionItem.struct = conditionItem.getCondTerm().struct;
	}
	
	public void visit(ConditionList conditionList) {
		if (!conditionList.getCondition().struct.equals(boolStruct)) {
			
    		conditionList.struct =Tab.noType;
			report_error("Uslov nije tipa BOOL", conditionList);
		}
    	else if (!conditionList.getCondTerm().struct.equals(boolStruct)) {
    		
    		conditionList.struct =Tab.noType;
			report_error("Uslov nije tipa BOOL", conditionList);
		}
    	else {
    		conditionList.struct = boolStruct;
		}
	}

	
	public void visit(ConditionTermItem conditionTermItem) {
		conditionTermItem.struct = conditionTermItem.getCondFact().struct;
	}
	
	public void visit(ConditionTermList conditionTermList) {
		if (!conditionTermList.getCondTerm().struct.equals(boolStruct)) {
			
			conditionTermList.struct =Tab.noType;
			report_error("Uslov nije tipa BOOL", conditionTermList);
		}
    	else if (!conditionTermList.getCondFact().struct.equals(boolStruct)) {
    		
    		conditionTermList.struct =Tab.noType;
			report_error("Uslov nije tipa BOOL", conditionTermList);
		}
    	else {
    		conditionTermList.struct = boolStruct;
		}
	}
	
	
	
	public void visit(ConditionFact conditionFact) {
		if(!conditionFact.getExpr().struct.compatibleWith(conditionFact.getExpr1().struct)){
			conditionFact.struct= Tab.noType;
    		report_error("Uslovne prom. moraju biti komepatbi.", conditionFact);
    	}
    	else if(conditionFact.getExpr().struct.isRefType()
    			|| conditionFact.getExpr1().struct.isRefType()){
    		 if((conditionFact.getRelop() instanceof Equal)
 	    			|| (conditionFact.getRelop() instanceof NotEqual)){
    			 conditionFact.struct= boolStruct;
 	    	}
    		 else {
    			 conditionFact.struct= Tab.noType;
	 	    	 report_error("Mora != ili ==", conditionFact);
    		 }
    	}
    	else {
    		
    		conditionFact.struct= boolStruct;
    	}
	}
	
	 public void visit(ConditionFactExpr condFactNoList) {
	    	if(!condFactNoList.getExpr().struct.equals(boolStruct)){
	    		condFactNoList.struct= Tab.noType;
	    		report_error("Uslov nije tipa BOOL", condFactNoList);
	    		}
	    	else {
	    		condFactNoList.struct= boolStruct;
	    	}
	    }
	
	 /*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * ----------------------------------------------------------------- Kobasica
	 * ----------------------------------------------------------------
	 */
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		
	 //DesignatorStatementSecond
	 
	 public void visit(DesignatorStatementSecond designatorStatementSecond) {
		 //desObj.getType().getElemType()
		 if(currentTypeDesignator!=designatorStatementSecond.getDesignatorIdentArr().obj.getType().getElemType()) {
			 //report_error("TIPOVI NISU KOMEPTABILNI: ",designatorStatementSecond);
		 }
		 //Obj designatorListObj = designatorStatementSecond.getDesignatorStatementDesignatorList().obj;//za kobasicu
		 //designatorListObj.setLevel(designatorListObj.getLevel());//za kobasicu
		 //report_error("",designatorStatementSecond);
	 }
	 
	 //DesignatorStatementDesignator_List
	 
	 public void visit(DesignatorStatementDesignator_List designatorStatementDesignator_List) {
		 Obj designatorListObj = designatorStatementDesignator_List.getDesignatorStatementDesignatorList().obj;//za kobasicu
		 Obj designatorOptObj = designatorStatementDesignator_List.getOptionalDesignator().obj;//za kobasicu
		 
		 if(designatorListObj == null) {
			 designatorStatementDesignator_List.obj = new Obj(designatorOptObj.getKind(), "DesignatorList", designatorOptObj.getType());
			 //designatorStatementDesignator_List.obj.setLevel(designatorOptObj.getLevel() + 1);
			 return;
		 }else {
			 designatorStatementDesignator_List.obj=designatorOptObj;
		 }
		 //designatorStatementDesignator_List.obj = new Obj(designatorListObj.getKind(), "DesignatorList", designatorOptObj.getType());
		 //designatorStatementDesignator_List.obj.setLevel(designatorListObj.getLevel() + designatorOptObj.getLevel());//za kobasicu
	 }
	 
	 //DesignatorStatementDesignator_Item
	 public void visit(DesignatorStatementDesignator_Item designatorStatementDesignator_Item) {
		 designatorStatementDesignator_Item.obj = designatorStatementDesignator_Item.getOptionalDesignator().obj;
		// designatorStatementDesignator_Item.obj = new Obj(designatorStatementDesignator_Item.getOptionalDesignator().obj.getKind(), designatorStatementDesignator_Item.getOptionalDesignator().obj.getName(), designatorStatementDesignator_Item.getOptionalDesignator().obj.getType());//za kobasicu
		
		// designatorStatementDesignator_Item.obj.setLevel(1);//za kobasicu
	 }
	 
	 //DesignatorStatementDesignator_Item
	 public void visit(YesDesignatorIdent yesDesignatorIdent) {
		Obj desObj = Tab.find(yesDesignatorIdent.getDesignatorName().getVarName());
		 
		Struct trenutni_tip=desObj.getType();
		if(currentTypeDesignator!=null) {
			
			if(currentTypeDesignator!=trenutni_tip) {
				report_error("ELEMENTI MORAJU BITI ISTOG TIPA",yesDesignatorIdent);
			}
		}
		else if(currentTypeDesignator==null) {
			currentTypeDesignator=desObj.getType();
		}
			if (desObj == Tab.noObj) {
				
				yesDesignatorIdent.getDesignatorName().obj = Tab.noObj;
				report_error("Designator " + yesDesignatorIdent.getDesignatorName().getVarName()+ "nije definisan ", yesDesignatorIdent.getDesignatorName());
						
			} else if (desObj.getKind() != Obj.Elem && desObj.getKind() != Obj.Var && desObj.getKind() != Obj.Fld) {
					
				
				yesDesignatorIdent.getDesignatorName().obj = Tab.noObj;
				report_error("Designator " + yesDesignatorIdent.getDesignatorName().getVarName() + " tipa nije dobar ",yesDesignatorIdent.getDesignatorName());
						
			} else {
				
				//yesDesignatorIdent.getDesignatorName().obj = desObj;
				//yesDesignatorIdent.getDesignatorName().obj.setLevel(1);//za kobasicu
				
				yesDesignatorIdent.obj=yesDesignatorIdent.getDesignatorName().obj;//za kobasicu
				//yesDesignatorIdent.obj.setLevel(1);
				
				
				if (yesDesignatorIdent.getDesignatorName().obj.getLevel() == 1
						&& (yesDesignatorIdent.getDesignatorName().obj.getKind() == Obj.Var || yesDesignatorIdent.getDesignatorName().obj.getKind() == Obj.Con)) {
					 //report_info("Koriscena lokalna "+designatorIdent.getVarName()+"!",designatorIdent);
					
				} else if (yesDesignatorIdent.getDesignatorName().obj.getLevel() == 0
						&& (yesDesignatorIdent.getDesignatorName().obj.getKind() == Obj.Var || yesDesignatorIdent.getDesignatorName().obj.getKind() == Obj.Con)) {
					 //report_info("Koriscena globalna "+designatorIdent.getVarName()+"! "+designatorIdent.obj.getKind(), designatorIdent);
					// 
				} else {
				}
			}
	 }
	 
	 //DesignatorStatementDesignator_Item
	 public void visit(YesDesignatorArr yesDesignatorArr) {
		 Obj desObj = yesDesignatorArr.getDesignatorArr().getDesignatorIdentArr().obj;
		 if (desObj == Tab.noObj) {
			 yesDesignatorArr.getDesignatorArr().obj= Tab.noObj;
			 report_error("Designator niz " + yesDesignatorArr.getDesignatorArr().getDesignatorIdentArr().getDesAName()+ " vec definisan ", yesDesignatorArr.getDesignatorArr());
		}
		 else if(!yesDesignatorArr.getDesignatorArr().getExpr().struct.equals(Tab.intType)) {
			 yesDesignatorArr.getDesignatorArr().obj= Tab.noObj;
			 report_error("Designator niz " + yesDesignatorArr.getDesignatorArr().getDesignatorIdentArr().getDesAName() + " tip nije dobar ", yesDesignatorArr.getDesignatorArr());
		 }
		 else {
			 //designator.obj= new Obj(Obj.Elem,desObj.getName() + "[$]", desObj.getType().getElemType());
			 yesDesignatorArr.obj=yesDesignatorArr.getDesignatorArr().obj;
			 //yesDesignatorArr.obj=new Obj(Obj.Elem,desObj.getName(), desObj.getType().getElemType());//mozda je ovo
			 //yesDesignatorArr.getDesignatorArr().obj= new Obj(Obj.Elem,desObj.getName(), desObj.getType().getElemType());//mozda je ovo
			 
			 //yesDesignatorArr.getDesignatorArr().obj.setLevel(1);//za kobasicu??
			 //yesDesignatorArr.obj=yesDesignatorArr.getDesignatorArr().obj;//za kobasicu??
			 report_info("Koriscen element niza " + desObj.getName() + "!", yesDesignatorArr.getDesignatorArr());
		 }
	 }
	 
	 public void visit(NoDesignator noDesignator) {
		 noDesignator.obj = Tab.noObj;
		
		 //noDesignator.obj.setLevel(1);
	 }
	 
	 
	
	 public void visit(YesDesignatorExp noDesignator) {
		 noDesignator.obj=new Obj(Obj.Var,"Kurac", Tab.intType);
	 }
	 
	 
	 
	 
	
	 
	
	 
	 
	
	 
	 
	
	
	
}
