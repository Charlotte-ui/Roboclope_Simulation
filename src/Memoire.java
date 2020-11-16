import java.util.ArrayList;
import java.util.Collections;

public class Memoire implements Runnable {
	boolean VERBOSE = true;
	On interrupteur;
	static final ArrayList<Coordonnee> NO_MEMOIRE = new ArrayList<Coordonnee>() ; // oublier au bout d'un certains temps ?
	private Object verrou;
	private Coordonnee coordonneesCurrent ; // x, y, distance
	private ArrayList  <Coordonnee> coordonneesMemoire ;
	
	public Memoire(On interrupteur) {
		coordonneesCurrent = Coordonnee.getNO_COORDONNEES();
		coordonneesMemoire = NO_MEMOIRE;
		this.interrupteur = interrupteur;
	}
	
	
	public void run() {
		while (interrupteur.isOn()) {
			synchronized (verrou) {
			try {
				verrou.wait();
				focusLePlusProche();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}		
		}
	}
	
	
	public void addCoordonnes (Coordonnee coordonnees) {
		coordonneesMemoire.add(coordonnees);
		VERBOSE=true;
		System.err.println("Les coordonnées "+coordonnees.x+", "+coordonnees.y+" pour une distance de "+coordonnees.distance+" ont été ajoutés à la mémoire.");
		synchronized (verrou) {
			verrou.notify();
		}
	}
	
	private void focusLePlusProche () {
		
		if (coordonneesMemoire.size() == 0) {
			coordonneesCurrent=Coordonnee.getNO_COORDONNEES();

		}
		else {
			Collections.sort(coordonneesMemoire);
			System.err.println("Les coordonnées "+coordonneesMemoire.get(0).x+", "+coordonneesMemoire.get(0).y+" pour une distance de "+coordonneesMemoire.get(0).distance+" sont les plus proches.");
			coordonneesCurrent=coordonneesMemoire.get(0);
		}
	}
	
	public void reinitializeParameters() {
		coordonneesCurrent=Coordonnee.getNO_COORDONNEES();
		coordonneesMemoire.clear();
	}


	public Coordonnee getCoordonneesCurrent() {
		return coordonneesCurrent;
	}


	public void setCoordonneesCurrent(Coordonnee coordonneesCurrent) {
		this.coordonneesCurrent = coordonneesCurrent;
	}


	public ArrayList<Coordonnee> getCoordonneesMemoire() {
		return coordonneesMemoire;
	}


	public void setCoordonneesMemoire(ArrayList<Coordonnee> coordonneesMemoire) {
		this.coordonneesMemoire = coordonneesMemoire;
	}
	
	public boolean isPositionDetecter () {
		return !coordonneesCurrent.equals(Coordonnee.getNO_COORDONNEES());
	}


	public Object getVerrou() {
		return verrou;
	}


	public void setVerrou(Object verrou) {
		this.verrou = verrou;
	}

	



	

}
