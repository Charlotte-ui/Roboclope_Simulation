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
	static final int m1A = 4; // dans l'expemple, 4 et 5 sont branch�s sur 6 pour le moteur 1
	static final int m1B = 5;
	static final int m1E = 6;
	static final int m2A = 0; // dans l'expemple, 0 et 2 sont branch�s sur 3 pour le moteur 2
	static final int m2B = 3;
	static final int m2E = 2;

	public static void main(String[] args) {
		On interrupteur = new On();
		interrupteur.setOn(true);
		int total_megot = 0;
		int etat = 1;
		
		Memoire m = new Memoire(interrupteur);
		Capteur2 c = new Capteur2(m,interrupteur);
		Actionneur a = new Actionneur(m1A,m1B,m1E,m2A,m2B,m2E);
		Batterie b = new Batterie(interrupteur);		
		
		new Thread(b).start();
		new Thread(c).start();
		Thread memory = new Thread(m);
		memory.start();


		while (interrupteur.isOn()) {
			switch (etat) {
			case CHERCHE_MEGOT : 
				System.out.println("Le robot cherche un m�got.");
				simule_avancee();
				if (m.isPositionDetecter()) etat=A_TROUVER_MEGOT;
				break;
			case A_TROUVER_MEGOT : 
				System.out.println("Le robot s'oriente vers le m�got en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"] a une distance de "+m.getCoordonneesCurrent().getDistance());
				simule_avancee();
				if (m.getCoordonneesCurrent().getDistance() <= 0) etat=RAMASSE_MEGOT;
				else m.getCoordonneesCurrent().setDistance(m.getCoordonneesCurrent().getDistance()-1); // petite ligne de simulation en attendant d'avoir un vrai retour du robot
				break;
			case RAMASSE_MEGOT : 
				System.out.println("Le robot ramasse le m�got en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"]. Il a rammass� en tout "+(++total_megot)+" m�gots.");
				c.reinitializeParameters();
				if (total_megot == MAX_MEGOTS) {
					System.out.println("La poche � m�gots est pleine.");
					etat=VIDE_MEGOTS;
				}
				else etat=CHERCHE_MEGOT;
				break;
			case VIDE_MEGOTS : 
				System.out.println("Le robot vide ses m�gots.");
				total_megot = 0;
				etat=CHERCHE_MEGOT;
				break;
			}
			
			
		}
		System.out.println("Le robot rentre � la base");

	}
	
	
	public static void simule_avancee () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
