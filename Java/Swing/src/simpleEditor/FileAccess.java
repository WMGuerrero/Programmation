package simpleEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileAccess {
	
	// Dans cette implémentation, le fichier reste toujours ouvert pour lecture, sauf aux moments d’écriture.
	// Par conséquent, le fichier est toujours en utilisation tant que la fenêtre de l’application reste ouverte.

	static String currentfname = "";  // le nom complet (incluant le chemin)
	static boolean notSaved = false;
	static BufferedReader myFileIn = null;
	
	 static void mySetCurrentFile(String fname)throws IOException {
		 if (currentfname!=fname){
			 if (myFileIn!=null) {myFileIn.close();}
			 currentfname = fname;
			 Main.frame.setTitle("Simple Editor: "+fname);
			 if(fname!=""){
				 myFileIn =new BufferedReader (new FileReader(fname));
			 }		 
	     }	 
	 }
	static void myReadFile (String fname) throws IOException {	
	     mySetCurrentFile(fname);
		 Main.frame.textArea.setText("");
		 String v= myFileIn.readLine();
			while (v!=null){
				Main.frame.textArea.append(v+'\n'); 
				v= myFileIn.readLine();
			}
		 notSaved = false;
	 }

	 static void myWriteFile(String fname) throws IOException {	
		 mySetCurrentFile("");
		 PrintWriter myFileOut =new PrintWriter( new BufferedWriter (new FileWriter(fname)));			
		 myFileOut.write(Main.frame.textArea.getText());
	     myFileOut.close(); 
	     mySetCurrentFile(fname);
		 notSaved = false;
	 }
	 
}
