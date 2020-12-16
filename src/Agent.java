import java.io.*;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class Agent {
	static final int CHERCHE_MEGOT = 1;
	static final int A_TROUVER_MEGOT = 2;
	static final int RAMASSE_MEGOT = 3;
	static final int VIDE_MEGOTS = 4;
	static final int MAX_MEGOTS = 10;
	// A avance, B recule
	static final int m1A = 4; // dans l'expemple, 4 et 5 sont branchés sur 6 pour le moteur 1
	static final int m1B = 5;
	static final int m1E = 6;
	static final int m2A = 0; // dans l'expemple, 0 et 2 sont branchés sur 3 pour le moteur 2
	static final int m2B = 3;
	static final int m2E = 2;

	public static void main(String[] args) {
		On interrupteur = new On();
		interrupteur.setOn(true);
		int total_megot = 0;
		int etat = 1;
		
		Memoire m = new Memoire(interrupteur);
		Capteur2 c = new Capteur2(m,interrupteur);
//		Actionneur a = new Actionneur(m1A,m1B,m1E,m2A,m2B,m2E);
		Batterie b = new Batterie(interrupteur);		
		
		new Thread(b).start();
		new Thread(c).start();
		Thread memory = new Thread(m);
		memory.start();
		
		
		
		String pythonScriptPath = "python/helloPython.py";
		String[] cmd = new String[2];
		cmd[0] = "python"; // check version of installed python: python -V
		cmd[1] = pythonScriptPath;
		 
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(cmd);
			System.out.println(pr.isAlive());
			// retrieve output from python script
			BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "test";
			System.out.println(line); 
			while((line = bfr.readLine()) != null) {
				// display each output line form python script
				System.out.println(line);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		try {
			ProcessBuilder pb = new ProcessBuilder("python", "helloPython.py"); 
			pb.directory(new File("python")); 
			Process p = pb.start();
			// retrieve output from python script
			BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "test";
			System.out.println(line); 
			while((line = bfr.readLine()) != null) {
				// display each output line form python script
				System.out.println(line);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		BufferedReader fr;
		String chaineLue;
		try {
			fr=new BufferedReader(new FileReader(pythonScriptPath));
			chaineLue=fr.readLine();
			while (chaineLue != null) {   
				chaineLue=fr.readLine();
				System.out.println(chaineLue);
			}
			 fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		 
	

		while (interrupteur.isOn()) {
			switch (etat) {
			case CHERCHE_MEGOT : 
				System.out.println("Le robot cherche un mégot.");
				simule_avancee();
				if (m.isPositionDetecter()) etat=A_TROUVER_MEGOT;
				break;
			case A_TROUVER_MEGOT : 
				System.out.println("Le robot s'oriente vers le mégot en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"] a une distance de "+m.getCoordonneesCurrent().getDistance());
				simule_avancee();
				if (m.getCoordonneesCurrent().getDistance() <= 0) etat=RAMASSE_MEGOT;
				else m.getCoordonneesCurrent().setDistance(m.getCoordonneesCurrent().getDistance()-1); // petite ligne de simulation en attendant d'avoir un vrai retour du robot
				break;
			case RAMASSE_MEGOT : 
				System.out.println("Le robot ramasse le mégot en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"]. Il a rammassé en tout "+(++total_megot)+" mégots.");
				c.reinitializeParameters();
				if (total_megot == MAX_MEGOTS) {
					System.out.println("La poche à mégots est pleine.");
					etat=VIDE_MEGOTS;
				}
				else etat=CHERCHE_MEGOT;
				break;
			case VIDE_MEGOTS : 
				System.out.println("Le robot vide ses mégots.");
				total_megot = 0;
				etat=CHERCHE_MEGOT;
				break;
			}
			
			
		}
		System.out.println("Le robot rentre à la base");

	}
	
	
	public static void simule_avancee () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
