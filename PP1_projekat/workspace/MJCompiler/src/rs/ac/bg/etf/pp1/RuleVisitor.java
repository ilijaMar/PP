package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{

	int printCallCount = 0;
	int varDeclCount = 0;
	
	Logger log = Logger.getLogger(getClass());

	
    
    public void visit(ProgName ProgName) {
    	log.info("Prepoznato ime programa");
    }
    public void visit(Program Program) {
    	log.info("Prepoznao program");
    }
    public void visit(ConstIdentt ConstIdentt) {
    	log.info("Prepoznao konstantu:" + ConstIdentt.getConstName());
    	// + ConstIdentt.getConstName()
    }
    public void visit(Var_Ident Var_Ident) {
    	log.info("Prepoznao promenljivu:" + Var_Ident.getVarIdent());
    }
    
    public void visit(Class_Decl Class_Decl) {
    	log.info("Prepoznao klasu:" + Class_Decl.getClassName());
    }
    
    
    public void visit(MethodNameType MethodNameType) {
    	log.info("Prepoznao metodu:" + MethodNameType.getMethodName());
    }
    
   
    
    public void visit(MethodNameVoid MethodNameVoid) {
    	log.info("Prepoznao void metodu:" + MethodNameVoid.getMethodName());
    }
    
    

}