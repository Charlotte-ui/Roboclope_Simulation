
public class Agent {
	static final int CHERCHE_MEGOT = 1;
	static final int A_TROUVER_MEGOT = 2;
	static final int RAMASSE_MEGOT = 3;
	static final int MAX_MEGOTS = 10;

	public static void main(String[] args) {
		On interrupteur = new On();
		interrupteur.setOn(true);
		int total_megot = 0;
		int etat = 1;
		
		Memoire m = new Memoire(interrupteur);
		Capteur2 c = new Capteur2(m,interrupteur);
		Actionneur a = new Actionneur();
		Batterie b = new Batterie(interrupteur);
		
		new Thread(b).start();
		new Thread(c).start();
		Thread memory = new Thread(m);
		memory.start();


		while (interrupteur.isOn()) {
			
			switch (etat) {
			case CHERCHE_MEGOT : 
				System.out.println("Le robot cherche un mégot.");
				a.avance();
				if (m.isPositionDetecter()) etat=A_TROUVER_MEGOT;
				break;
			case A_TROUVER_MEGOT : 
				System.out.println("Le robot s'oriente vers le mégot en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"] a une distance de "+m.getCoordonneesCurrent().getDistance());
				a.avance();
				if (m.getCoordonneesCurrent().getDistance() <= 0) etat=RAMASSE_MEGOT;
				else m.getCoordonneesCurrent().setDistance(m.getCoordonneesCurrent().getDistance()-1); // petite ligne de simulation en attendant d'avoir un vrai retour du robot
				break;
			case RAMASSE_MEGOT : 
				System.out.println("Le robot ramasse le mégot en ["+m.getCoordonneesCurrent().getX()+","+m.getCoordonneesCurrent().getY()+"]. Il a rammassé en tout "+(++total_megot)+" mégots.");
				c.reinitializeParameters();
				if (total_megot == MAX_MEGOTS) {
					System.out.println("La poche à mégots est pleine.");
					interrupteur.setOn(false);
				}
				etat=CHERCHE_MEGOT;
				break;
			}
			
			
		}
		System.out.println("Le robot rentre à la base");

	}
	
	
	
	
	

}
