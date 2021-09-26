import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

/*...................................................*/
/*..............CheckClassVVisitor..........................*/
/*...................................................*/
public class CheckClassVVisitor extends GJDepthFirst<String, String>{

	/**
    	* f0 -> <IDENTIFIER>
    	*/
	public String visit(Identifier n, String argu) {
		String s=n.f0.toString();
		//System.out.print(s+"\t");
		return s;
	}

  /** f0 -> PrimaryExpression()
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
		int found=0;
		String cname=null;
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable1.entrySet()){
			String classname = t.getKey();
			for(Map.Entry<String, String> e : t.getValue().entrySet()){
				if(e.getKey() == s2){
					found=1;
					cname=classname;
				}
			}
		}
		if(found==1){
			found = 0;
			//System.out.println("-----");
			//System.out.println("Found name \""+s2+"\" "+cname);
		}else{
			System.out.println("Error: Name \""+s2+"\" not found");
			System.exit(4);
		}
		return null;
   	}

	//check for local variables of type class if they exist or not
	public void CheckClassObj(){
		//check local variables symbol table
		for(Map.Entry<String, Map<String, String>> t4 : DefCollectorVisitor.symbolt.SymbolTable4.entrySet()){
			String methodName = t4.getKey();
			for(Map.Entry<String, String> e4 : t4.getValue().entrySet()){
				if((e4.getValue() != "int") && (e4.getValue() != "boolean") && (e4.getValue() != "int[]")){
					if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(e4.getValue()) == true){
						;//System.out.println("Class object type \""+e4.getValue()+"\" found "+e4.getKey());
					}else{
						System.out.println("Error: Class object type \""+e4.getValue()+"\" not found "+e4.getKey());
						System.exit(1);
					}
				}
			}
		}
		//check parameters symbol table
		for(Map.Entry<String, Map<String, String>> t3 : DefCollectorVisitor.symbolt.SymbolTable3.entrySet()){
			String methodName = t3.getKey();
			for(Map.Entry<String, String> e3 : t3.getValue().entrySet()){
				if((e3.getValue() != "int") && (e3.getValue() != "boolean") && (e3.getValue() != "int[]")){
					if(e3.getValue() == "String[]"){
						continue;
					}
					if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(e3.getValue()) == true){
						;//System.out.println("Class object type \""+e3.getValue()+"\" found "+e3.getKey());
					}else{
						System.out.println("Error: Class object type \""+e3.getValue()+"\" not found "+e3.getKey());
						System.exit(2);
					}
				}
			}
		}
		//check class fields symbol table
		for(Map.Entry<String, Map<String, String>> t2 : DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
			String className = t2.getKey();
			for(Map.Entry<String, String> e2 : t2.getValue().entrySet()){
				if((e2.getValue() != "int") && (e2.getValue() != "boolean") && (e2.getValue() != "int[]")){
					if(e2.getValue()=="void"){
						continue;
					}
					if(DefCollectorVisitor.symbolt.SymbolTable1.containsKey(e2.getValue()) == true){
						;//System.out.println("Class object type \""+e2.getValue()+"\" found "+e2.getKey());
					}else{
						System.out.println("Error: Class object type \""+e2.getValue()+"\" not found "+e2.getKey());
						System.exit(3);
					}
				}
			}
		}
	}

}
