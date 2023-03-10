// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:51:6


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(ExprListMod ExprListMod);
    public void visit(Mulop Mulop);
    public void visit(MethodDecl MethodDecl);
    public void visit(VarIdent VarIdent);
    public void visit(FormParamsList FormParamsList);
    public void visit(AssignExprSemi AssignExprSemi);
    public void visit(Relop Relop);
    public void visit(TermList TermList);
    public void visit(DesignatorStatementOptions DesignatorStatementOptions);
    public void visit(ConstIdent ConstIdent);
    public void visit(StatementList StatementList);
    public void visit(Extends Extends);
    public void visit(PrintOptional PrintOptional);
    public void visit(ClassName ClassName);
    public void visit(Addop Addop);
    public void visit(DesignatorStatementDesignatorList DesignatorStatementDesignatorList);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(DeclList DeclList);
    public void visit(Designator Designator);
    public void visit(ArrayOpt ArrayOpt);
    public void visit(OptionalExpr OptionalExpr);
    public void visit(MethodName MethodName);
    public void visit(ConstIdents ConstIdents);
    public void visit(ClassBody ClassBody);
    public void visit(Condition Condition);
    public void visit(ConstValue ConstValue);
    public void visit(AssignExpr AssignExpr);
    public void visit(OptionalDesignator OptionalDesignator);
    public void visit(ActParsList ActParsList);
    public void visit(ArrayDecl ArrayDecl);
    public void visit(DesignatorPars DesignatorPars);
    public void visit(FactorExpr FactorExpr);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(DesignatorList DesignatorList);
    public void visit(DesignatorZaDodelu DesignatorZaDodelu);
    public void visit(Decl Decl);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(VarIdentList VarIdentList);
    public void visit(ClassDecl ClassDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(CondFact CondFact);
    public void visit(ElseOptional ElseOptional);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(DesignatorStatementExpr DesignatorStatementExpr);
    public void visit(ReadDesignator ReadDesignator);
    public void visit(FormParam FormParam);
    public void visit(FormPars FormPars);
    public void visit(Assigning Assigning);
    public void visit(Type Type);
    public void visit(NoBody NoBody);
    public void visit(Class_Body Class_Body);
    public void visit(NoExtends NoExtends);
    public void visit(YesExtends YesExtends);
    public void visit(Class_Name Class_Name);
    public void visit(Class_Decl Class_Decl);
    public void visit(LessEqual LessEqual);
    public void visit(Less Less);
    public void visit(GreaterEqual GreaterEqual);
    public void visit(Greater Greater);
    public void visit(NotEqual NotEqual);
    public void visit(Equal Equal);
    public void visit(Assignop Assignop);
    public void visit(Mod Mod);
    public void visit(Divide Divide);
    public void visit(Multiply Multiply);
    public void visit(Subtract Subtract);
    public void visit(Add Add);
    public void visit(DesignatorIdentArr DesignatorIdentArr);
    public void visit(DesignatorArr DesignatorArr);
    public void visit(NoDesignatorPars NoDesignatorPars);
    public void visit(Designator_Pars Designator_Pars);
    public void visit(NoFactorExpression NoFactorExpression);
    public void visit(FactorExpression FactorExpression);
    public void visit(ObicanDesignator ObicanDesignator);
    public void visit(FactorExpr_ FactorExpr_);
    public void visit(FactorNew FactorNew);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(TermFactor TermFactor);
    public void visit(Term_List Term_List);
    public void visit(Term Term);
    public void visit(NoOptionalExpression NoOptionalExpression);
    public void visit(OptionalExpression OptionalExpression);
    public void visit(MonkeyExpression MonkeyExpression);
    public void visit(NegativeTermExpression NegativeTermExpression);
    public void visit(TermExpression TermExpression);
    public void visit(AddExpression AddExpression);
    public void visit(DesignatorDot DesignatorDot);
    public void visit(NoDesignatorList NoDesignatorList);
    public void visit(DesignatorsList DesignatorsList);
    public void visit(DesignatorName DesignatorName);
    public void visit(DesignatorExpr DesignatorExpr);
    public void visit(ActParsItem ActParsItem);
    public void visit(NoActParsList NoActParsList);
    public void visit(Act_Pars Act_Pars);
    public void visit(ActPars_List ActPars_List);
    public void visit(SamoExpr SamoExpr);
    public void visit(SamoExprList SamoExprList);
    public void visit(DesignatorZaDodeluArr DesignatorZaDodeluArr);
    public void visit(DesignatorZaDodeluExp DesignatorZaDodeluExp);
    public void visit(StModifikacija StModifikacija);
    public void visit(DesignatorStatementOptionFirst DesignatorStatementOptionFirst);
    public void visit(DesignatorStatementDesignator_Item DesignatorStatementDesignator_Item);
    public void visit(DesignatorStatementDesignator_List DesignatorStatementDesignator_List);
    public void visit(NoDesignator NoDesignator);
    public void visit(YesDesignatorExp YesDesignatorExp);
    public void visit(YesDesignatorArr YesDesignatorArr);
    public void visit(YesDesignatorIdent YesDesignatorIdent);
    public void visit(DesignatorStatementOptionMinusMinus DesignatorStatementOptionMinusMinus);
    public void visit(DesignatorStatementOptionPlusPlus DesignatorStatementOptionPlusPlus);
    public void visit(DesignatorStatementOptionSecond DesignatorStatementOptionSecond);
    public void visit(DesignatorStatementThird DesignatorStatementThird);
    public void visit(DesignatorStatementSecond DesignatorStatementSecond);
    public void visit(DesignatorStatementFirst DesignatorStatementFirst);
    public void visit(ConditionFactExpr ConditionFactExpr);
    public void visit(ConditionFact ConditionFact);
    public void visit(ConditionTermItem ConditionTermItem);
    public void visit(ConditionTermList ConditionTermList);
    public void visit(ConditionItem ConditionItem);
    public void visit(ConditionList ConditionList);
    public void visit(ReadDesigArr ReadDesigArr);
    public void visit(ReadDesigExp ReadDesigExp);
    public void visit(NoElse NoElse);
    public void visit(YesElse YesElse);
    public void visit(Label Label);
    public void visit(AssignExpressionError AssignExpressionError);
    public void visit(SwapperStatement SwapperStatement);
    public void visit(LabelStatement LabelStatement);
    public void visit(GotoStatement GotoStatement);
    public void visit(Statements Statements);
    public void visit(PrintStNum PrintStNum);
    public void visit(PrintSt PrintSt);
    public void visit(ReadSt ReadSt);
    public void visit(ReturnStRet ReturnStRet);
    public void visit(ReturnStExpr ReturnStExpr);
    public void visit(ContinueSt ContinueSt);
    public void visit(BreakSt BreakSt);
    public void visit(WhileSt WhileSt);
    public void visit(IfElse IfElse);
    public void visit(AssignDesig AssignDesig);
    public void visit(DesignatorSt DesignatorSt);
    public void visit(NoStatementList NoStatementList);
    public void visit(Statement_List Statement_List);
    public void visit(MethodNameVoid MethodNameVoid);
    public void visit(MethodNameType MethodNameType);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(NoMethodDeclarations NoMethodDeclarations);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(FormParsNoArrayList FormParsNoArrayList);
    public void visit(FormParsArrayList FormParsArrayList);
    public void visit(FormParsNoArray FormParsNoArray);
    public void visit(FormParsArray FormParsArray);
    public void visit(NoFormParsList NoFormParsList);
    public void visit(YFormParsList YFormParsList);
    public void visit(NoArrayDeclaration NoArrayDeclaration);
    public void visit(ArrayDeclaration ArrayDeclaration);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(VarType VarType);
    public void visit(Var_Ident Var_Ident);
    public void visit(NoVarIdentList NoVarIdentList);
    public void visit(VarIdentError VarIdentError);
    public void visit(VarIdent_List VarIdent_List);
    public void visit(VarDeclError VarDeclError);
    public void visit(Var_Declaration Var_Declaration);
    public void visit(ConstType ConstType);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(ConstIdentt ConstIdentt);
    public void visit(NoConstIdents NoConstIdents);
    public void visit(YesConstIdents YesConstIdents);
    public void visit(ConstDeclarationn ConstDeclarationn);
    public void visit(ClassDeclaration_ ClassDeclaration_);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(NoDeclarationList NoDeclarationList);
    public void visit(Declarations Declarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
