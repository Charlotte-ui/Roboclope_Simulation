import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Appelle le programme de l'IA
 * @author CHarlotte Marty
 *
 */
public class PythonCaller implements Runnable {
	
	/**
	 * Programme du thread
	 */
	public void run() {
		lancerIA();
	}
	
	/**
	 * Execute un programme python
	 */
	private void lancerIA() {
		String pythonScriptPath = "python/helloPython.py";
		String[] cmd = new String[2];
		cmd[0] = "python"; // check version of installed python: python -V
		cmd[1] = pythonScriptPath;
		
		 
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr;
		System.err.println("Lancement de l'IA");

		try {
			pr = rt.exec(cmd);
			// retrieve output from python script
			BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while((line = bfr.readLine()) != null) {
				System.out.println();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	

}
