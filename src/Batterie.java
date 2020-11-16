
public class Batterie implements Runnable {

	private On interrupteur;

	public Batterie(On interrupteur) {
		this.interrupteur = interrupteur;
	}





	// s'il n'y a plus de batterie, le programme s'arrête
	public void run() {
		while (interrupteur.isOn()) {
			if (isDecharge()) {
				interrupteur.setOn(false);
				System.err.println("   ~  Plus de batterie   ~  ");
			}
		}	
	}
	
	
	
	
	// test s'il reste de la batterie, pour le moment attente d'une minute
		private boolean isDecharge () {
			
			try {
				Thread.sleep(1000*60); // on attend 1min
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
			return true;
		}

}
