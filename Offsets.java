import java.util.*;

public class Offsets{

	public static Map<String, Integer> classOffsets = new HashMap<String, Integer>();
	public static Map<String, String> methods = new HashMap<String, String>();

	public static int varOff;
	public static int methodOff;

	public void PrintOffsets(){
		varOff=0;
		methodOff=0;
	
		int size=0;
		int classSize=0;
		int main=0;
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable1.entrySet()){
			String className1 = t.getKey();
			for(Map.Entry<String, String> e :t.getValue().entrySet()){
				if(e.getKey() != "main"){
					if(e.getValue()=="varMember"){
						size = returnSize(className1, e.getKey());
						classSize += size;
					}else{
						methods.put(className1, e.getKey());
					}
				}else{
					main=1;
				}
			}
			if(main==0){
				classOffsets.put(className1, classSize);
			}
			classSize = 0;
		}
		//System.out.println(methods.toString());
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
			String className2 = t.getKey();
			for(Map.Entry<String, String> e :t.getValue().entrySet()){
				if(e.getKey() != "main"){
					if(methods.containsValue(e.getKey()) == false){		//variable
						System.out.println(className2+"."+e.getKey()+"\t: "+varOff);
						if(e.getValue() == "int" || e.getValue() == "int[]"){
							varOff += 4;
						}else if(e.getValue() == "boolean"){
							varOff += 1;
						}else{
							varOff += classOffsets.get(e.getValue());
						}
					}else{		//method
							System.out.println(className2+"."+e.getKey()+"\t: "+methodOff);
							methodOff += 8;
					}
				}
			}
		}
	}

	public int returnSize(String classn, String fn){
		int size=0;
		for(Map.Entry<String, Map<String, String>> t :DefCollectorVisitor.symbolt.SymbolTable2.entrySet()){
			String className = t.getKey();
			if(className == classn){
				for(Map.Entry<String, String> e :t.getValue().entrySet()){
					if(e.getKey() == fn){		//variable
						if(e.getValue() == "int" || e.getValue() == "int[]"){
							return 4;
						}else if(e.getValue() == "boolean"){
							return 1;
						}else{
							//System.out.println("class");
							return 0;		//pedio tupou klashs(logw xronou den prolaba na to kanw kai etsi epistrefw ena upotypwdes 0)
						}				
					}
				}
			}
		}
		return -1;
	}
}
