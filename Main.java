import syntaxtree.*;
import visitor.*;
import java.io.*;

public class Main{
	public static void main(String [] args){
		if(args.length != 1){
			System.out.println("Usage: java Main <inputFile>");
			System.exit(1);
		}
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(args[0]);
			MiniJavaParser parser = new MiniJavaParser(fis);
			Goal root = parser.Goal();
			System.out.println("Program parsed successfully");
			DefCollectorVisitor eval = new DefCollectorVisitor();

			DefCollectorVisitor.symbolt.SymbolTablesInit();
			System.out.println("SymbolTables created successfully");

			//System.out.println(root.accept(eval, null));
			root.accept(eval, null);
			//DefCollectorVisitor.symbolt.print();

			CheckClassVVisitor eval2 = new CheckClassVVisitor();
			eval2.CheckClassObj();
			//System.out.println(root.accept(eval2, null));
			root.accept(eval2, null);

			TypeCheckVisitor eval3 = new TypeCheckVisitor();
			//System.out.println(root.accept(eval3, null));
			root.accept(eval3, null);

			System.out.println("\n...Program compilation complete!!!");

		/*	Offsets eval4 = new Offsets();
			System.out.println("\nOutput:");
			eval4.PrintOffsets();			*/

		}
		catch(ParseException ex){
			System.out.println(ex.getMessage());
		}
		catch(FileNotFoundException ex){
			System.err.println(ex.getMessage());
		}
		finally{
			try{
				if(fis != null) fis.close();
			}
			catch(IOException ex){
				System.err.println(ex.getMessage());
			}
		}
	}
}
