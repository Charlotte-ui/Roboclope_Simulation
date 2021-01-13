import java.util.ArrayList;
import java.util.Collections;
/**
 * Gère la mémoire du robot
 * @author Charlotte Marty
 *
 */
public class Memoire implements Runnable {
	boolean VERBOSE = true;
	On interrupteur;
	static final ArrayList<Coordonnee> NO_MEMOIRE = new ArrayList<Coordonnee>() ; // oublier au bout d'un certains temps ?
	private Object verrou;
	private Coordonnee coordonneesCurrent ; // x, y, distance
	private ArrayList  <Coordonnee> coordonneesMemoire ;
	
	/**
	 * Constructeur de la mémoire
	 * @param interrupteur
	 */
	public Memoire(On interrupteur) {
		coordonneesCurrent = Coordonnee.getNO_COORDONNEES();
		coordonneesMemoire = NO_MEMOIRE;
		this.interrupteur = interrupteur; 
	}
	
	/**
	 * Programme du thread
	 */
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
	
	/**
	 * Ajoute des coordonnes a la memoire
	 * @param coordonnees
	 */
	public void addCoordonnes (Coordonnee coordonnees) {
		coordonneesMemoire.add(coordonnees);
		VERBOSE=true;
		System.err.println("Les coordonnées "+coordonnees.x+", "+coordonnees.y+" pour une distance de "+coordonnees.distance+" ont été ajoutées à la mémoire.");
		synchronized (verrou) {
			verrou.notify();
		}
	}
	
	/**
	 * 	attribue à la variable coordonneesCurrent les coordonnes les plus proches
	 */
	private void focusLePlusProche () {
		
		if (coordonneesMemoire.size() == 0) {
			coordonneesCurrent=Coordonnee.getNO_COORDONNEES();

		}
		else {
			Collections.sort(coordonneesMemoire);
			System.err.println("Les coordonn�es "+coordonneesMemoire.get(0).x+", "+coordonneesMemoire.get(0).y+" pour une distance de "+coordonneesMemoire.get(0).distance+" sont les plus proches.");
			coordonneesCurrent=coordonneesMemoire.get(0);
		}
	}
	
	/**
	 * vide tout les paramètres
	 */
	public void reinitializeParameters() {
		coordonneesCurrent=Coordonnee.getNO_COORDONNEES();
		coordonneesMemoire.clear();
	}

    /**
     * renvoit les coordonnes courantes
     * @return coordonneesCurrent
     */
	public Coordonnee getCoordonneesCurrent() {
		return coordonneesCurrent;
	}

	/**
	 * 
	 * @param coordonneesCurrent
	 */
	public void setCoordonneesCurrent(Coordonnee coordonneesCurrent) {
		this.coordonneesCurrent = coordonneesCurrent;
	}

	/**
	 * renvoit la liste des coordonnes en mémoire
	 * @return coordonneesMemoire
	 */
	public ArrayList<Coordonnee> getCoordonneesMemoire() {
		return coordonneesMemoire;
	}

	/**
	 * 
	 * @param coordonneesMemoire
	 */
	public void setCoordonneesMemoire(ArrayList<Coordonnee> coordonneesMemoire) {
		this.coordonneesMemoire = coordonneesMemoire;
	}
	
	/**
	 * verifie si au moins une position a été detecter
	 * @return
	 */
	public boolean isPositionDetecter () {
		return !coordonneesCurrent.equals(Coordonnee.getNO_COORDONNEES());
	}

	/**
	 * renvoit le verrou
	 * @return verrou
	 */
	public Object getVerrou() {
		return verrou;
	}

	/**
	 * 
	 * @param verrou
	 */
	public void setVerrou(Object verrou) {
		this.verrou = verrou;
	}

	



	

}
