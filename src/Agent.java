import java.io.*;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Controle l'agent
 * Classe qui possède le Main
 * @author Charlotte Marty
 *
 */
public class Agent {
	
	// Etat de l'agent
	
	/**
	 * E1 : le robot cherche les mégots
	 */
	static final int CHERCHE_MEGOT = 1;
	/**
	 * E2 : le robot a repéré un mégot et vas le chercher
	 */
	static final int A_TROUVER_MEGOT = 2;
	/**
	 * E3 : le robot est devant le mégot et doit le ramasser
	 */
	static final int RAMASSE_MEGOT = 3;
	/**
	 * E4 : la poche a mégots du robot est pleine et il doit la vider
	 */
	static final int VIDE_MEGOTS = 4;
	
	/**
	 * constante : nombre maximal de mégots transportables
	 */
	static final int MAX_MEGOTS = 10;

	// identifiant des ports de la RasberryPI
	// A avance, B recule
	/**
	 * identifiant du port A du moteur 1 
	 */
	static final int m1A = 4; // dans l'expemple, 4 et 5 sont branch�s sur 6 pour le moteur 1
	static final int m1B = 5;
	/**
	 * identifiant du port E du moteur 1 
	 */
	static final int m1E = 6;
	/**
	 * identifiant du port A du moteur 2 
	 */
	static final int m2A = 0; // dans l'expemple, 0 et 2 sont branch�s sur 3 pour le moteur 2
	/**
	 * identifiant du port E du moteur 2 
	 */
	static final int m2B = 3;
	/**
	 * identifiant du port E du moteur 2 
	 */
	static final int m2E = 2;
	
	/** 
     * <b>Programme principale</b> 
     * Démarre les threads et gère l'agent 
     * 
     */ 
	public static void main(String[] args) {
		On interrupteur = new On();
		interrupteur.setOn(true);
		int total_megot = 0;
		int etat = CHERCHE_MEGOT;
		
		Memoire m = new Memoire(interrupteur);
		PythonCaller c = new PythonCaller();
//		Actionneur a = new Actionneur(m1A,m1B,m1E,m2A,m2B,m2E);
		Batterie b = new Batterie(interrupteur);	
		RecupererDataIA data = new RecupererDataIA(interrupteur, m);
		
		new Thread(b).start(); 
		new Thread(c).start();
		Thread memory = new Thread(m);
		memory.start();
		new Thread(data).start();
	
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
				System.out.println("Le robot ramasse le mégot en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"]. Il a ramassé en tout "+(++total_megot)+" mégots.");
				data.reinitializeParameters();
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
	
	/**
	 * Simule le mouvement du robot avec un temps d'attente
	 */
	public static void simule_avancee () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
