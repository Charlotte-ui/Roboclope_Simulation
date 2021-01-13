/**
 * Controle la batterie du robot
 * @author Charlotte Marty
 *
 */
public class Batterie implements Runnable {
	/**
	 * l'interrupteur permet à tout les autres thread de savoir quand se stopper
	 */
	private On interrupteur;

	/**
	 * Constructeur de la batterie
	 * @param interrupteur
	 */
	public Batterie(On interrupteur) {
		this.interrupteur = interrupteur;
	}

	/**
	 * Dès qu'il n'y a plus de batterie, le programme s'arrete
	 */
	public void run() {
		while (interrupteur.isOn()) {
			if (isDecharge()) {
				interrupteur.setOn(false);
				System.err.println("   ~  Plus de batterie   ~  ");
			}
		}	
	}
	
	/**
	 * vérifie s'il reste de la batterie ; pour le moment attente d'une minute
	 * @return
	 */
		private boolean isDecharge () {
			
			try {
				Thread.sleep(1000*60); // on attend 1min
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			return true;
		}

}
