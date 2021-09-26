import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

/*...................................................*/
/*..............TypeCheckVisitor.....................*/
/*...................................................*/
public class TypeCheckVisitor extends GJDepthFirst<String, String>{

	public static int returnExprFlag;
	public static int paramCount;	

	/**
    	* f0 -> <INTEGER_LITERAL>
    	*/
   	public String visit(IntegerLiteral n, String argu) {
		Integer x = Integer.parseInt(n.f0.toString());
		//System.out.print(x+"\t");
		return "int";
   	}


	/**
    	* f0 -> <IDENTIFIER>
    	*/
	public String visit(Identifier n, String argu) {
		String s=n.f0.toString();
		//System.out.print(s+"\t");
		return s;
  	}

   	/**
    	* f0 -> "true"
    	*/
   	public String visit(TrueLiteral n, String argu) {
		Boolean b=true;
		String str=Boolean.toString(b);
	//	System.out.print(str+"\t");
		return "boolean";
   	}

	/**
    	* f0 -> "false"
    	*/
   	public String visit(FalseLiteral n, String argu) {
		Boolean b=false;
		String str=Boolean.toString(b);
	//	System.out.print(str+"\t");
		return "boolean";
   	}

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   	public String visit(MethodDeclaration n, String argu) {
		returnExprFlag=0;
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		String methodName = n.f2.accept(this, argu);
      		n.f3.accept(this, argu);
      		n.f4.accept(this, argu);
      		n.f5.accept(this, argu);
      		n.f6.accept(this, argu);
      		n.f7.accept(this, argu);
      		n.f8.accept(this, methodName);
      		n.f9.accept(this, argu);
		returnExprFlag=1;
	      	n.f10.accept(this, methodName);
		returnExprFlag=0;
      		n.f11.accept(this, argu);
      		n.f12.accept(this, argu);
		return null;
   	}

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   	public String visit(ArrayAssignmentStatement n, String argu) {
      		String s0 = n.f0.accept(this, argu);
	  	String type0 = this.findType(argu, s0);
		if(type0 != "int[]"){
			System.out.println("Error: Id Array type \""+type0+"\"");
			System.exit(12);
		}else{
			;//System.out.println("Id Array OK \""+type0+"\"");
		}
      		n.f1.accept(this, argu);
      		String type1 = n.f2.accept(this, argu);
		if(type1 != "int"){
			System.out.println("Error: Array index type \""+type1+"\"");
			System.exit(13);
		}else{
			;//System.out.println("Array index type \""+type1+"\"");
		}
      		n.f3.accept(this, argu);
      		n.f4.accept(this, argu);
      		String type2 = n.f5.accept(this, argu);
		if(type2 != "int"){
			System.out.println("Error: ArrayAssignment type \""+type2+"\"");
			System.exit(14);
		}else{
			;//System.out.println("ArrayAssignment type \""+type2+"\"");
		}
      		n.f6.accept(this, argu);
		return null;
   }


   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   	public String visit(AssignmentStatement n, String argu) {
      		String s0 = n.f0.accept(this, argu);
	  	String type0 = this.findType(argu, s0);
      		n.f1.accept(this, argu);
      		String type1 = n.f2.accept(this, argu);
      		n.f3.accept(this, argu);
		if(type0==type1){
			;//System.out.println("Assignment of types \""+type0+"\" ok");
		}else{
			System.out.println("Error: Incompatible assignment of types \""+type0+"\" and \""+type1+"\"");
			System.exit(9);
		}
		return null;
   	}

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   	public String visit(IfStatement n, String argu) {
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		String type = n.f2.accept(this, argu);
		if(type!="boolean"){
			System.out.println("Error: If expression is type \""+type+"\"");
			System.exit(11);
		}else{
			;//System.out.println("If expression OK");
		}
      		n.f3.accept(this, argu);
      		n.f4.accept(this, argu);
      		n.f5.accept(this, argu);
      		n.f6.accept(this, argu);
		return null;
   	}

	/**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   	public String visit(WhileStatement n, String argu) {
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		String type = n.f2.accept(this, argu);
		if(type!="boolean"){
			System.out.println("Error: while expression is type \""+type+"\"");
			System.exit(11);
		}else{
			;//System.out.println("While expression OK");
		}
      		n.f3.accept(this, argu);
      		n.f4.accept(this, argu);
		return null;
   	}


   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   	public String visit(PrintStatement n, String argu) {
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		String type = n.f2.accept(this, argu);
      		n.f3.accept(this, argu);
      		n.f4.accept(this, argu);
		if(type == "int"){
			;//System.out.println("Print statement type OK");
		}else{
			System.out.println("Error :Print statement type \""+type+"\"");
			System.exit(10);
		}
		return null;
   	}


    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | Clause()
    */
   	public String visit(Expression n, String argu) {
      		return n.f0.accept(this, argu);
   	}

   /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
   	public String visit(AndExpression n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(s0) == true){
			//System.out.println("clause Allocation expr OK  \""+s0+"\"");
			type0=s0;
		}else if(s0=="int"){
			type0="int";
		}else if(s0=="boolean"){
			type0="boolean";
		}else if(s0=="int[]"){
			type0="int[]";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
		if(type0!="boolean"){
			System.out.println("Error: clause incompatible type");
			System.exit(8);
		}

      		n.f1.accept(this, argu);
      		String s1 = n.f2.accept(this, argu);
		if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(s1) == true){
			//System.out.println("clause Allocation expr OK  \""+s1+"\"");
			type1=s1;
		}else if(s1=="int"){
			type1="int";
		}else if(s1=="boolean"){
			type1="boolean";
		}else if(s1=="int[]"){
			type1="int[]";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type1!="boolean"){
			System.out.println("Error: clause incompatible type");
			System.exit(8);
		}
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == "boolean"){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(9);
			}
		}
		return "boolean";
	}


   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   	public String visit(CompareExpression n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
      		n.f1.accept(this, argu);
      		String s1 = n.f2.accept(this, argu);
		if(s1=="int"){
			type1="int";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type1=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type0 == type1){
			if(type0=="int" || type0=="int[]"){
				;//System.out.println(type0+" "+s0+" < "+type1+" "+s1+" = "+"boolean");
			}else{
				System.out.println("Error: compare between incompatible types");
				System.exit(5);
			}
		}else{
			System.out.println("Error: different type values");
			System.exit(4);
		}
		//find return type of argu
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == "boolean"){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(5);
			}
		}
		return "boolean";
   	}


    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   	public String visit(PlusExpression n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
      		n.f1.accept(this, argu);
      		String s1 = n.f2.accept(this, argu);
		if(s1=="int"){
			type1="int";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type1=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type0 == type1){
			if(type0=="int" || type0=="int[]"){
				;//System.out.println(type0+" "+s0+" + "+type1+" "+s1+" = "+type0);
			}else{
				System.out.println("Error: plus between incompatible types");
				System.exit(5);
			}
		}else{
			System.out.println("Error: different type values");
			System.exit(4);
		}
		//find return type of argu
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(type0 == arguType){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(5);
			}
		}
		return type0;
   	}

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   	public String visit(MinusExpression n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
      		n.f1.accept(this, argu);
      		String s1 = n.f2.accept(this, argu);
		if(s1=="int"){
			type1="int";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type1=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type0 == type1){
			if(type0=="int" || type0=="int[]"){
				;//System.out.println(type0+" "+s0+" - "+type1+" "+s1+" = "+type0);
			}else{
				System.out.println("Error: minus between incompatible types");
				System.exit(5);
			}
		}else{
			System.out.println("Error: different type values");
			System.exit(4);
		}
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(type0 == arguType){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(5);
			}
		}
		return type0;
   	}

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   	public String visit(TimesExpression n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
      		n.f1.accept(this, argu);
      		String s1 = n.f2.accept(this, argu);
		if(s1=="int"){
			type1="int";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type1=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type0 == type1){
			if(type0=="int" || type0=="int[]"){
				;//System.out.println(type0+" "+s0+" * "+type1+" "+s1+" = "+type0);
			}else{
				System.out.println("Error: times between incompatible types");
				System.exit(5);
			}
		}else{
			System.out.println("Error: different type values");
			System.exit(4);
		}
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(type0 == arguType){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(5);
			}
		}
		return type0;
   	}

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   	public String visit(ArrayLookup n, String argu) {
		String type0=null,type1=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
		if(type0 != "int[]"){
			System.out.println("Error: primary expression has to be of type int[] "+s0);
			System.exit(7);
		}
      		n.f1.accept(this, argu);
     		String s1 = n.f2.accept(this, argu);
		if(s1=="int"){
			type1="int";
		}else if(s1=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type1=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type1+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type1+"\"");
				System.exit(12);
			}
		}else{
			type1 = this.findType(argu, s1);
		}
		if(type1 != "int"){
			System.out.println("Error: primary expression has to be of type int "+s1);
			System.exit(8);
		}
      		n.f3.accept(this, argu);
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == "int"){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(5);
			}
		}
		return "int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   	public String visit(ArrayLength n, String argu) {
		String type0=null;
      		String s0 = n.f0.accept(this, argu);
		if(s0=="int"){
			type0="int";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
		if(type0 != "int[]"){
			System.out.println("Error: primary expression has to be of type int[] "+s0);
			System.exit(8);
		}
      		n.f1.accept(this, argu);
      		n.f2.accept(this, argu);
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == "int"){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(9);
			}
		}
		return "int";
   	}

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   	public String visit(MessageSend n, String argu) {
		int isClassType=0;
		String type0=null;
      		String s0 = n.f0.accept(this, argu);
		if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(s0) == true){
			//System.out.println("MessageSend primary expr OK class \""+s0+"\"");
			type0 = s0;
			isClassType = 1;
		}else if(s0=="int"){
			type0="int";
		}else if(s0=="boolean"){
			type0="boolean";
		}else if(s0=="int[]"){
			type0="int[]";
		}else if(s0=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, s0);
		}
	      	n.f1.accept(this, argu);
	      	String s1 = n.f2.accept(this, argu);
		int found=0;
		String classN=null;
		String rtype=null;
		String methodName=null;
		int numPars=0, foundPars=0;
		if(isClassType==1 || ((type0!="int") && (type0!="boolean") && (type0!="int[]"))){
			for(Map.Entry<String, Map<String, String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				classN = t.getKey();
				if(classN == type0){
					for(Map.Entry<String, String> e: t.getValue().entrySet()){
						if(e.getKey()==s1){
							found=1;
							rtype=e.getValue();
							break;			
						}
					}
					if(found==1){
						break;
					}
				}
			}
			//find number of method parameters
			for(Map.Entry<String, Map<String, String>> t: DefCollectorVisitor.symbolt.SymbolTable3.entrySet()){
				methodName = t.getKey();
				if(methodName == s1){
					for(Map.Entry<String, String> e: t.getValue().entrySet()){
						numPars++;		
					}
					foundPars=1;
					break;
				}
				if(foundPars==1){
					break;
				}
			}
			if(foundPars==1){
				;//System.out.println("Number of parameters of \""+s1+"\" is "+numPars);
			}else{
				numPars=0;
				//System.out.println("Number of parameters of \""+s1+"\" is "+numPars);
			}
		
			if(found==1){
				;//System.out.println("Found method \""+s1+"\" in class \""+classN+"\" with return type \""+rtype+"\"");
			}else{
				System.out.println("Error: Cannot find method \""+s1+"\" in class \""+type0+"\"");
				System.exit(10);
			}
		}
		if(type0=="int" || type0=="int[]" || type0=="boolean"){
			System.out.println("Error: invalid type MessageSend \""+type0+"\"");
			System.exit(11);
		}
      		n.f3.accept(this, argu);
		paramCount=0;
      		n.f4.accept(this, argu);
		if(paramCount==numPars){
			;//System.out.println("Number of parameters in call of \""+s1+"\" OK "+paramCount);
		}else{
			System.out.println("Error: number of parameters in call of \""+s1+"\" doesnt match "+paramCount);
			System.exit(13);
		}
		paramCount=0;
      		n.f5.accept(this, argu);
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == rtype){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(9);
			}
		}

		return rtype; 
   	}

   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
   	public String visit(ExpressionList n, String argu) {
      		String type0 = n.f0.accept(this, argu);
		paramCount++;
      		String type1 = n.f1.accept(this, argu);
		return null;
   	}

   /**
    * f0 -> ( ExpressionTerm() )*
    */
   	public String visit(ExpressionTail n, String argu) {
      		return n.f0.accept(this, argu);
   	}

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   	public String visit(ExpressionTerm n, String argu) {
      		n.f0.accept(this, argu);
      		String type1 = n.f1.accept(this, argu);
		paramCount++;
		return null;
   	}


    /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
   	public String visit(Clause n, String argu) {
		String r = n.f0.accept(this, argu);
		String type0=null;
		int isclasstype=0;
		if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(r) == true){
			//System.out.println("clause Allocation expr OK  \""+r+"\"");
			type0=r;
			isclasstype=1;
		}else if(r=="int"){
			type0="int";
		}else if(r=="boolean"){
			type0="boolean";
		}else if(r=="int[]"){
			type0="int[]";
		}else if(r=="this"){
			int foundFlag=0;
			for(Map.Entry<String, Map<String,String>> t: DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String className=t.getKey();
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==argu){
						type0=className;
						foundFlag=1;
						break;
					}
				}
				if(foundFlag==1){
					break;
				}
			}
			if(foundFlag==1){
				foundFlag=0;
				//System.out.println("This type \""+type0+"\"");
			}else{
				System.out.println("Error: This type does not exist \""+type0+"\"");
				System.exit(12);
			}
		}else{
			type0 = this.findType(argu, r);
		}
//		if(type0!="int" && type0!="int[]" && type0!="boolean" && isclasstype==0){
//			System.out.println("Error: clause incompatible type");
//			System.exit(8);
//		}
		if(returnExprFlag==1){	
			String arguType = findMethodReturnType(argu);
			if(arguType == type0){
				;//System.out.println("Return type of \""+argu+"\" OK");
			}else{
				System.out.println("Error: invalid return type of \""+argu+"\"");
				System.exit(9);
			}
		}
		return type0;
   	}

    /**
    * f0 -> "!"
    * f1 -> Clause()
    */
   	public String visit(NotExpression n, String argu) {
      		n.f0.accept(this, argu);
      		String s1 = n.f1.accept(this, argu);
		return s1;
   	}

   /**
    * f0 -> "this"
    */
   	public String visit(ThisExpression n, String argu) {
		return "this";
   	}

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   	public String visit(ArrayAllocationExpression n, String argu) {
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		n.f2.accept(this, argu);
      		String type = n.f3.accept(this, argu);
		if(type != "int"){
			System.out.println("Error: ArrayAllocation expr type \""+type+"\"");
			System.exit(11);
		}else{
			;//System.out.println("ArrayAllocation expr type \"int\" OK");
		}
      		n.f4.accept(this, argu);
		return "int[]";
   	}

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   	public String visit(AllocationExpression n, String argu) {
      		n.f0.accept(this, argu);
      		String s = n.f1.accept(this, argu);
		if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(s) == true){
			;//System.out.println("Allocation expr OK \""+s+"\"");
		}else{
			System.out.println("Error :Allocation expr \""+s+"\"");
			System.exit(12);
		}
      		n.f2.accept(this, argu);
      		n.f3.accept(this, argu);
		return s;
   	}

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   	public String visit(BracketExpression n, String argu) {
      		n.f0.accept(this, argu);
      		String type = n.f1.accept(this, argu);
      		n.f2.accept(this, argu);
		return type;
   	}

	//search functions

	public String findType(String methodName, String s){
		String type=null;
		int found1=0,found2=0,found3=0;
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable4.entrySet()){
			String mName = t.getKey();
			if(mName == methodName){
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey() == s){
						type = e.getValue(); 	
						found1 = 1;
						break;
					}
				}
				if(found1==1){
					break;
				}
			}
		}
		if(found1==0){
			for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable3.entrySet()){
				String mName = t.getKey();
				if(mName == methodName){
					for(Map.Entry<String, String> e: t.getValue().entrySet()){
						if(e.getKey() == s){
							type = e.getValue(); 	
							found2 = 1;
							break;
						}
					}
					if(found2==1){
						break;
					}
				}
			}

		}
		if(found1==0 && found2==0){
			//search for the class the method belongs to
			String className=null;
			int cflag=0;
			for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable1.entrySet()){
				String classN = t.getKey();
				for(Map.Entry<String, String> e :t.getValue().entrySet()){
					if(e.getKey()==methodName){
						className = classN;
						cflag=1;
						break;
					}
				}
				if(cflag==1){
					break;
				}
			}
			if(cflag==0){
				System.out.println("Error: could not find class that method \""+methodName+"\" belongs to");
				System.exit(5);
			}else{
				;//System.out.println("Found class that method \""+methodName+"\" belongs to: "+className);
			}
			
			for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
				String cName = t.getKey();
				if(cName == className){
					for(Map.Entry<String, String> e: t.getValue().entrySet()){
						if(e.getKey() == s){
							type = e.getValue(); 	
							found3 = 1;
							break;
						}
					}
					if(found3==1){
						break;
					}
				}
			}

		}
		if(found1==1 || found2==1 || found3==1){
			//System.out.println("Type of \""+s+"\" in method \""+methodName+"\" is \""+type+"\"");
			return type;
		}else if(found1==0 && found2==0 && found3==0){
			System.out.println("Error :Type of \""+s+"\" in method \""+methodName+"\" not found");
			System.exit(2);
		}
		return "notfound";
	}

	public String findMethodReturnType(String methodName){
		String arguType=null;
		int found=0;
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
			String className = t.getKey();
			for(Map.Entry<String, String> e: t.getValue().entrySet()){
				if(e.getKey() == methodName){
					arguType = e.getValue();
					found = 1;
					break;
				}
			}
			if(found==1){
				break;
			}
		}
		if(found == 1){
			//System.out.println("Return type of method \""+methodName+"\" found: "+arguType);
			found = 0;
		}else{
			System.out.println("Error: Return type of method \""+methodName+"\" not found");
			System.exit(1);
		}
		
		return arguType;
	}

}
