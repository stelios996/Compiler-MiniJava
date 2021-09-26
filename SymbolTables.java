import java.util.*;
/*
+----------+--------------+------+
 classname | classmembers | spec |	//1os krataw ta melh klashs
+----------+--------------+------+
spec -> object
	 -> method

+----------+--------------+------+
 classname | classmembers | type |	//2os krataw typous melwn klashs
+----------+--------------+------+

+-----------+------------+------+
 methodName | parameters | type |	//3os krataw typous parametrwn me8odwn
+-----------+------------+------+

+-----------+----------------+------+
 methodName | localVariables | type |	//4os krataw typous topikwn metablhtwn
+-----------+----------------+------+
*/


/*.....................................*/
/*........Symbol Tables Class..........*/
/*.....................................*/
public class SymbolTables{

	public static Map<String, Map<String, String>> SymbolTable1;
	public static Map<String, Map<String, String>> SymbolTable2;
	public static Map<String, Map<String, String>> SymbolTable3;
	public static Map<String, Map<String, String>> SymbolTable4;

	public void SymbolTablesInit(){
	
		SymbolTable1 = new HashMap<String, Map<String, String>>();
		SymbolTable2 = new HashMap<String, Map<String, String>>();
		SymbolTable3 = new HashMap<String, Map<String, String>>();
		SymbolTable4 = new HashMap<String, Map<String, String>>();
	}

	public void print(){
		System.out.println("...Printing SymbolTable1");
		for(Map.Entry<String, Map<String, String>> t :this.SymbolTable1.entrySet()){
			String className1 = t.getKey();
			System.out.println();
			for(Map.Entry<String, String> e :t.getValue().entrySet())
				System.out.println("className: "+className1 + "\t-classMember: "+e.getKey()+"\t-spec: "+e.getValue());
		}

		System.out.println("...Printing SymbolTable2");
		for(Map.Entry<String, Map<String, String>> t :this.SymbolTable2.entrySet()){
			String className2 = t.getKey();
			System.out.println();
			for(Map.Entry<String, String> e :t.getValue().entrySet())
				System.out.println("className: "+className2 + "\t-classMember: "+e.getKey()+"\t-type: "+e.getValue());
		}

		System.out.println("...Printing SymbolTable3");
		for(Map.Entry<String, Map<String, String>> t :this.SymbolTable3.entrySet()){
			String methodName1 = t.getKey();
			System.out.println();
			for(Map.Entry<String, String> e :t.getValue().entrySet())
				System.out.println("methodName: "+methodName1 + "\t-parameter: "+e.getKey()+"\t-type: "+e.getValue());
		}
	
		System.out.println("...Printing SymbolTable4");
		for(Map.Entry<String, Map<String, String>> t :this.SymbolTable4.entrySet()){
			String methodName2 = t.getKey();
			System.out.println();
			for(Map.Entry<String, String> e :t.getValue().entrySet())
				System.out.println("methodName: "+methodName2 + "\t-localVariable: "+e.getKey()+"\t-type: "+e.getValue());
		}
	}

}
