import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

/*...................................................*/
/*..............DefCollectorVisitor..................*/
/*...................................................*/
public class DefCollectorVisitor extends GJDepthFirst<String, String>{

	public static SymbolTables symbolt = new SymbolTables();

	public static Map<String, String> classMember = new HashMap<String, String>();
	public static Map<String, String> classMemberType = new HashMap<String, String>();
	public static Map<String, String> methodVars = new HashMap<String, String>();
	public static Map<String, String> methodParams = new HashMap<String, String>();	

	public static Map<String, String> classOrMethod = new HashMap<String, String>();
	/**
    	* f0 -> <INTEGER_LITERAL>
    	*/
	public String visit(IntegerLiteral n, String argu) {
		Integer x = Integer.parseInt(n.f0.toString());
		//System.out.print(x+"\t");
		return Integer.toString(x);
   	}

	/**
    	* f0 -> <IDENTIFIER>
    	*/
	public String visit(Identifier n, String argu) {
		String s=n.f0.toString();
//		System.out.print(s+"\t");
		return s;
  	}

   	/**
    	* f0 -> "true"
    	*/
   	public String visit(TrueLiteral n, String argu) {
		Boolean b=true;
		String str=Boolean.toString(b);
	//	System.out.print(str+"\t");
		return str;
   	}

	/**
    	* f0 -> "false"
    	*/
   	public String visit(FalseLiteral n, String argu) {
		Boolean b=false;
		String str=Boolean.toString(b);
	//	System.out.print(str+"\t");
		return str;
   	}

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n, String argu) {
		String s = n.f0.toString();
      	return s;
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
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
	return "arrayAlloc";
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public String visit(AllocationExpression n, String argu) {
      n.f0.accept(this, argu);
      String s1 = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
	return "alloc";
   }


   	/**
    	* f0 -> "class"
    	* f1 -> Identifier()
    	* f2 -> "{"
    	* f3 -> "public"
    	* f4 -> "static"
    	* f5 -> "void"
    	* f6 -> "main"
    	* f7 -> "("
    	* f8 -> "String"
    	* f9 -> "["
    	* f10 -> "]"
    	* f11 -> Identifier()
    	* f12 -> ")"
    	* f13 -> "{"
    	* f14 -> ( VarDeclaration() )*
    	* f15 -> ( Statement() )*
    	* f16 -> "}"
    	* f17 -> "}"
    	*/
   	public String visit(MainClass n, String argu) {
      		n.f0.accept(this, argu);
      		String s1 = n.f1.accept(this, argu);
		//System.out.println("class "+s1);
		Map<String, String> member = new HashMap<String, String>();
		member.put("main", "methodMember");				//insert to SymbolTable1(className|member|spec)
		symbolt.SymbolTable1.put(s1, member);
		
		Map<String, String> type = new HashMap<String, String>();
		type.put("main", "void");					//insert to SymbolTable2(className|member|type)
		symbolt.SymbolTable2.put(s1, type);

		classOrMethod.put(s1, "class");					//insert to classOrMethod
		classOrMethod.put("main", "method");
		
		n.f2.accept(this, argu);
		n.f3.accept(this, argu);
	        n.f4.accept(this, argu);
      	 	n.f5.accept(this, argu);
      	 	n.f6.accept(this, argu);
      		n.f7.accept(this, argu);
      		n.f8.accept(this, argu);
      		n.f9.accept(this, argu);
      		n.f10.accept(this, argu);
		String s11=n.f11.accept(this, argu);
		methodParams.put(s11, "String[]");			//insert to SymbolTable3(method|param|type)
		symbolt.SymbolTable3.put("main", methodParams);

      		n.f12.accept(this, argu);
      		n.f13.accept(this, argu);
      		n.f14.accept(this, argu);
      		n.f15.accept(this, argu);
      		n.f16.accept(this, argu);
      		n.f17.accept(this, argu);
		return null;
   	}

	
   	/**
    	* f0 -> "class"
    	* f1 -> Identifier()
    	* f2 -> "{"
    	* f3 -> ( VarDeclaration() )*
    	* f4 -> ( MethodDeclaration() )*
    	* f5 -> "}"
    	*/
   	public String visit(ClassDeclaration n, String argu) {
      		
      		n.f0.accept(this, argu);

      		String s1 = n.f1.accept(this, argu);
		//System.out.println("class "+s1);
		if(classOrMethod.containsKey(s1)==false){			//check if className already exists
			classOrMethod.put(s1, "class");				//insert to classOrMethod
		}else{
			System.out.println("Error: classname \""+s1+"\" already exists");
			System.exit(1);
		}			

	      	n.f2.accept(this, argu);
		n.f3.accept(this, s1);		//pass className to VarDeclaration()
		n.f4.accept(this, s1);		//pass className to MethodDeclaration()
      		n.f5.accept(this, argu);
      		return null;
   	}


   	/**
    	* f0 -> "int"
    	*/
   	public String visit(IntegerType n, String argu) {
		String i = n.f0.toString();
		//System.out.print(i+" ");
		return i;
   	}

   	/**
    	* f0 -> "boolean"
    	*/
   	public String visit(BooleanType n, String argu) {
      		String b = n.f0.toString();
		return b;
   	}

	/**
    	* f0 -> "int"
    	* f1 -> "["
    	* f2 -> "]"
    	*/
   	public String visit(ArrayType n, String argu) {
      		String s0 = n.f0.toString();
		String s1 = n.f1.toString();
		String s2 = n.f2.toString();
		String s = s0+s1+s2;
		return "int[]";
   	}

	/**
    	* f0 -> ArrayType()
    	*       | BooleanType()
    	*       | IntegerType()
    	*       | Identifier()
    	*/
   	public String visit(Type n, String argu) {

		String type = n.f0.accept(this, argu);
		//System.out.println(type);
      		return type;
   	}

   	/**
    	* f0 -> Type()
    	* f1 -> Identifier()
    	* f2 -> ";"
    	*/
   	public String visit(VarDeclaration n, String argu) {
      		
		//System.out.println("argu "+argu);
      		String s0 = n.f0.accept(this, argu);
      		String s1 = n.f1.accept(this, argu);
      		String s2 = n.f2.toString(); 
		if(classOrMethod.containsKey(s1)==true){
			System.out.println("Error: name \""+s1+"\" already exists");
			System.exit(4);
		}

		if(classOrMethod.containsKey(argu)==true && classOrMethod.get(argu)=="class"){
			if(classMember.isEmpty()==true || symbolt.SymbolTable1.containsKey(argu)==false){
				classMember = new HashMap<String, String>();
			}
	
			if(classMember.containsKey(s1) == false){
				classMember.put(s1, "varMember");		//insert to SymbolTable1(className|member|spec)
				symbolt.SymbolTable1.put(argu, classMember);
			}else{
				System.out.println("Error: name \""+s1+"\" already exists");
				System.exit(3);
			}

			if(classMemberType.isEmpty()==true || symbolt.SymbolTable2.containsKey(argu)==false){
				classMemberType = new HashMap<String, String>();
			}
			if(classMemberType.containsKey(s1) == false){
				classMemberType.put(s1, s0);			//insert to SymbolTable2(className|member|type)
				symbolt.SymbolTable2.put(argu, classMemberType);
			}else{
				System.out.println("Error2");
			}
		}else if(classOrMethod.containsKey(argu)==true && classOrMethod.get(argu)=="method"){
			for(Map.Entry<String, Map<String, String>> t4 : this.symbolt.SymbolTable4.entrySet()){
				String methodName = t4.getKey();		//check if local variable is already being used in the method
				if(methodName==argu){
					for(Map.Entry<String, String> e : t4.getValue().entrySet()){
						if(e.getKey()==s1){
							System.out.println("Error: name \""+s1+"\" already exists as local variable"+e.getKey()+e.getValue());
							System.exit(7);
						}
					}
				}
			}
			for(Map.Entry<String, Map<String, String>> t3 : this.symbolt.SymbolTable3.entrySet()){
				String mname = t3.getKey();			//check if local variable name is already being used as a parameter in the method
				if(mname==argu){
					for(Map.Entry<String, String> e : t3.getValue().entrySet()){
						if(e.getKey()==s1){
							System.out.println("Error: name \""+s1+"\" already exists as parameter"+e.getKey()+e.getValue());
							System.exit(8);
						}
					}
				}
			}
			if(methodVars.isEmpty()==true || symbolt.SymbolTable4.containsKey(argu)==false){
				methodVars = new HashMap<String, String>();
			}
	
			if(methodVars.containsKey(s1) == false){
				methodVars.put(s1, s0);				//insert to SymbolTable4(methodName|localVar|type)
				symbolt.SymbolTable4.put(argu, methodVars);
			}else{
				System.out.println("Error4");
			}
		}
		String s = s0+s1+s2;
		return null;
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
	
      		n.f0.accept(this, argu);

      		String s1 = n.f1.accept(this, argu);
      		String s2 = n.f2.accept(this, argu);
		if(classOrMethod.containsKey(s2)==true){		//check if methodName already exists
			System.out.println("Error: methodName \""+s2+"\" already exists");
			System.exit(2);
		}
		if(classMember.isEmpty()==true || symbolt.SymbolTable1.containsKey(argu)==false){
			classMember = new HashMap<String, String>();
		}
		if(classMember.containsKey(s2) == false){
			classMember.put(s2, "methodMember");			//insert to SymbolTable1(className|member|spec)
			symbolt.SymbolTable1.put(argu, classMember);
		}else{
			System.out.println("Error: name \""+s2+"\" already exists");
			System.exit(2);
		}
		if(classMemberType.isEmpty()==true || symbolt.SymbolTable2.containsKey(argu)==false){
			classMemberType = new HashMap<String, String>();
		}
		if(classMemberType.containsKey(s2) == false){
			classMemberType.put(s2, s1);				//insert to SymbolTable2(className|member|type)
			symbolt.SymbolTable2.put(argu, classMemberType);
		}else{
			System.out.println("Error2");
		}

		classOrMethod.put(s2, "method");				//insert to classOrMethod
      		n.f3.accept(this, argu);
			n.f4.accept(this, s2);		//pass methodName to FormalParameterList()
      		n.f5.accept(this, argu);
      		n.f6.accept(this, argu);
			n.f7.accept(this, s2);		//pass methodName to VarDeclaration()
			n.f8.accept(this, s2);		//pass methodName to Statement()
      		n.f9.accept(this, argu);
			n.f10.accept(this, s2);		//pass methodName to Expression()
      		n.f11.accept(this, argu);
      		n.f12.accept(this, argu);
      		return null;
   	}

	/**
    	* f0 -> FormalParameter()
    	* f1 -> FormalParameterTail()
    	*/
   	public String visit(FormalParameterList n, String argu) {
     		
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		return null;
   	}

	/**
    	* f0 -> Type()
    	* f1 -> Identifier()
    	*/
   	public String visit(FormalParameter n, String argu) {
      		
      		String s0 = n.f0.accept(this, argu);
      		String s1 = n.f1.accept(this, argu);
		if(classOrMethod.containsKey(s1)==true){
			System.out.println("Error: name \""+s1+"\" already exists");
			System.exit(4);
		}
		for(Map.Entry<String, Map<String, String>> t: this.symbolt.SymbolTable3.entrySet()){//check if parameter name already exists
			String methodName = t.getKey();						    //in parameter list
			if(methodName==argu){
				for(Map.Entry<String, String> e: t.getValue().entrySet()){
					if(e.getKey()==s1){
						System.out.println("Error: parameter name \""+s1+"\" already exists "+argu+e.getValue());
						System.exit(5);
					}
				}
			}
		}
		int flag=0;
		String temp=null;				//check if parameter name is a class member name
		for(Map.Entry<String, Map<String, String>> t1 :this.symbolt.SymbolTable1.entrySet()){
			String className = t1.getKey();					//searching for the class that the method exists
			for(Map.Entry<String, String> e1 :t1.getValue().entrySet()){
				if(e1.getKey()==argu){
					temp=className;
					flag=1;
					break;
				}
			}
			if(flag==1){
				flag=0;
				break;
			}
		}
		//className now has the name of the class that the method is a member of
		for(Map.Entry<String, Map<String, String>> t1 :this.symbolt.SymbolTable1.entrySet()){
			String cName = t1.getKey();				//check if parameter name already exists as a class member name
			if(cName==temp){
				for(Map.Entry<String, String> e: t1.getValue().entrySet()){
					if(e.getKey()==s1){
						System.out.println("Error: name \""+s1+"\" already exists as class member"+e.getKey()+e.getValue());
						System.exit(6);
					}
				}
			}
		}
		if(methodParams.isEmpty()==true || symbolt.SymbolTable3.containsKey(argu)==false){
			methodParams = new HashMap<String, String>();
		}
		if(methodParams.containsKey(s1) == false){
			methodParams.put(s1, s0);			//insert to SymbolTable3(method|param|type)
			symbolt.SymbolTable3.put(argu, methodParams);
		}else if(methodParams.containsKey(s1)==true && methodParams.get(s1)==s0 /*kai kati allo*/){
			methodParams.put(s1,s0);			//insert to SymbolTable3(method|param|type)
			symbolt.SymbolTable3.put(argu, methodParams);
		}else{
			System.out.println("Error");
		}
      		return null;
   	}

	/**
    	* f0 -> ( FormalParameterTerm() )*
    	*/
   	public String visit(FormalParameterTail n, String argu) {
      		return n.f0.accept(this, argu);
   	}

   	/**
    	* f0 -> ","
    	* f1 -> FormalParameter()
    	*/
   	public String visit(FormalParameterTerm n, String argu) {
      		
      		n.f0.accept(this, argu);
      		n.f1.accept(this, argu);
      		return null;
   	}

	//........................................................................................................................
	//check for local variable names

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public String visit(Statement n, String argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
		this.SearchSymbolTables(s0, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
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
		this.SearchSymbolTables(s0, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
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
      n.f2.accept(this, argu);
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
      n.f2.accept(this, argu);
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
		n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
		return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public String visit(CompareExpression n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String p2 = n.f2.accept(this, argu);
		int i;
		String value=null;
		for(i=0;i<2;i++){
			if(i==0){
				value = p1;
			}else if(i==1){
				value = p2;
			}
			switch(value){
				case "true":
					System.out.println("Error: compare boolean "+value);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: compare boolean "+value);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in compare expression");
					System.exit(13);
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier or Integer
					if(this.isNumber(value)==true){
						;//System.out.println("number "+value);
					}else{
						this.SearchSymbolTables(value, argu);
					}
			}
		}
		return null;
   }

	/**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public String visit(PlusExpression n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String p2 = n.f2.accept(this, argu);
		int i;
		String value=null;
		for(i=0;i<2;i++){
			if(i==0){
				value = p1;
			}else if(i==1){
				value = p2;
			}
			switch(value){
				case "true":
					System.out.println("Error: add boolean "+value);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: add boolean "+value);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in plus expression");
					System.exit(13);
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier or Integer
					if(this.isNumber(value)==true){
						;//System.out.println("number "+value);
					}else{
						this.SearchSymbolTables(value, argu);
					}
			}
		}

		return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public String visit(MinusExpression n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String p2 = n.f2.accept(this, argu);
		int i;
		String value=null;
		for(i=0;i<2;i++){
			if(i==0){
				value = p1;
			}else if(i==1){
				value = p2;
			}
			switch(value){
				case "true":
					System.out.println("Error: subtract boolean "+value);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: subtract boolean "+value);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in minus expression");
					System.exit(13);
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier or Integer
					if(this.isNumber(value)==true){
						;//System.out.println("number "+value);
					}else{
						this.SearchSymbolTables(value, argu);
					}
			}
		}
		return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public String visit(TimesExpression n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String p2 = n.f2.accept(this, argu);
		int i;
		String value=null;
		for(i=0;i<2;i++){
			if(i==0){
				value = p1;
			}else if(i==1){
				value = p2;
			}
			switch(value){
				case "true":
					System.out.println("Error: multiply boolean "+value);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: multiply boolean "+value);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in times expression");
					System.exit(13);
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier or Integer
					if(this.isNumber(value)==true){
						;//System.out.println("number "+value);
					}else{
						this.SearchSymbolTables(value, argu);
					}
			}
		}
		return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String p2 = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
		int i;
		String value=null;
		for(i=0;i<2;i++){
			if(i==0){
				value = p1;
			}else if(i==1){
				value = p2;
			}
			switch(value){
				case "true":
					System.out.println("Error: array lookup boolean "+value);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: array lookup boolean "+value);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in array lookup expression");
					System.exit(13);
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier ot Integer
					if(this.isNumber(value)==true){
						;//System.out.println("number "+value);
					}else{
						this.SearchSymbolTables(value, argu);
					}
			}
		}
		return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n, String argu) {
      String p1 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
		switch(p1){
			case "true":
					System.out.println("Error: array length boolean "+p1);
					System.exit(11);
					break;
				case "false":
					System.out.println("Error: array length boolean "+p1);
					System.exit(12);
					break;
				case "this":
					break;
				case "alloc":
					System.out.println("Error: allocation in array length expression");
					System.exit(13);
					break;
			case "arrayAlloc":
					break;
			case "bracketExpr":
					break;
			default:	//identifier or Integer
				if(this.isNumber(p1)==true){
						//System.out.println("number "+p1);
						System.out.println("Error: array length of integer");
						System.exit(14);
				}else{
					this.SearchSymbolTables(p1, argu);
				}
		}
		return null;
   }

   /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
   public String visit(Clause n, String argu) {
		String r =  n.f0.accept(this, argu);
		if(r=="notExpr"){
			;
		}else{
			switch(r){
				case "true":
					break;
				case "false":
					break;
				case "this":
					break;
				case "alloc":
					break;
				case "arrayAlloc":
					break;
				case "bracketExpr":
					break;
				default:	//identifier or integer
					if(this.isNumber(r)==true){
						;//System.out.println("number "+r);
					}else{
						this.SearchSymbolTables(r, argu);
					}
			}
		}
		return null;
   }

   /**
    * f0 -> "!"
    * f1 -> Clause()
    */
   public String visit(NotExpression n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
		return "notExpr";
   }

	/**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public String visit(BracketExpression n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
		return "bracketExpr";
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

      String p0 = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String s2 = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
		switch(p0){
			case "true":
				break;
			case "false":
				break;
			case "this":
				break;
			case "alloc":
				break;
			case "arrayAlloc":
					break;
			case "bracketExpr":
					break;
			default:	//identifier or Integer
				if(this.isNumber(p0)==true){
						;//System.out.println("number "+p0);
				}else{
					this.SearchSymbolTables(p0, argu);
				}
		}
		return null;
   }

   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
   public String visit(ExpressionList n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
      n.f1.accept(this, argu);
		return null;
   }



	public boolean isNumber(String s){

		boolean flag=true;
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e){
			flag=false;
		}	
		return flag;
	}

	//search symbol tables for name
	public void SearchSymbolTables(String s, String argu){
		int flag1=0,flag2=0,flag3=0;
		if(this.symbolt.SymbolTable4.containsKey(argu)==true){
			for(Map.Entry<String, Map<String,String>> t4 :this.symbolt.SymbolTable4.entrySet()){
				String methodName = t4.getKey();
				if(methodName == argu){
					for(Map.Entry<String, String> e: t4.getValue().entrySet()){
						if(e.getKey() == s){
							//System.out.println("Name \""+s+"\" exists as local Variable "+methodName);
							flag1=2;
							break;
						}else{
							flag1=1;
						}
					}
					if(flag1==2){
						flag1=0;break;
					}
				}
			}
		}else{
			flag1=1;
		}
		if(flag1==1){
			if(this.symbolt.SymbolTable3.containsKey(argu)==true){
				for(Map.Entry<String, Map<String,String>> t3 :this.symbolt.SymbolTable3.entrySet()){
					String methodName1 = t3.getKey();
					if(methodName1 == argu){
						for(Map.Entry<String, String> e: t3.getValue().entrySet()){
							if(e.getKey() == s){
								//System.out.println("Name \""+s+"\" exists as parameter "+methodName1);
								flag2=2;	
								break;						
							}else{
								flag2=1;
							}
						}
						if(flag2==2){
							flag2=0;break;
						}
					}
				}
			}else{
				flag2=1;
			}
		}
		if(flag1==1 && flag2==1){
			String cname=null;
			for(Map.Entry<String, Map<String,String>> t1 :this.symbolt.SymbolTable1.entrySet()){
				String className = t1.getKey();
				for(Map.Entry<String, String> e: t1.getValue().entrySet()){
					if(e.getKey() == argu){
						cname = className;
					}
				}
			}
				for(Map.Entry<String, Map<String,String>> t1 :this.symbolt.SymbolTable1.entrySet()){
					String className1 = t1.getKey();
					if(className1 == cname){
						for(Map.Entry<String, String> e: t1.getValue().entrySet()){
							if(e.getKey() == s){
								//System.out.println("Name \""+s+"\" exists as class member "+className1);
								flag3=2;
								break;
							}else{
								flag3=1;
							}
						}
						if(flag3==2){
							flag3=0;break;
						}
					}
				}
		}
		if(flag1==1 && flag2==1 && flag3==1){
				System.out.println("Error: Name \""+s+"\" does not exist");
				flag1=0;flag2=0;flag3=0;
				System.exit(10);
		}
		flag1=0;flag2=0;flag3=0;
	}
}
