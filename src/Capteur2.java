
public class Capteur2 implements Runnable {
	private Object verrou=new Object();
	private Coordonnee coordonnees ;
	private On interrupteur;
	Memoire m;
	
	public Capteur2(Memoire m, On interrupteur) {
		super();
		coordonnees = Coordonnee.getNO_COORDONNEES();
		this.m=m;
		this.interrupteur=interrupteur;
		m.setVerrou(verrou);
	}


	@Override
	public void run() {

		while (interrupteur.isOn()) {
			detecter ();
		}
		
	}
	
	private void detecter () {	
		if (contactIA ()) {
			System.err.println("Contact IA");
			setCoordonnees(new Coordonnee(Math.random()*10,Math.random()*10,Math.random()*10));
			m.addCoordonnes(coordonnees);
		//	attendre(); dans cette version l'IA n'attend pas
		}	
	}
	
	//methode qui simule l'envoit de signal de l'IA
	private boolean contactIA () {
		try {
			Thread.sleep(1000); // on attend 10s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ((int)(Math.random()*5) ==0) ?true:false;
	}
	
	
	public void reinitializeParameters() {
		setCoordonnees(Coordonnee.getNO_COORDONNEES());
		m.reinitializeParameters();
	}
	
	
	public void attendre() {
		synchronized (verrou) {
			try {
				System.err.println("Le robot arrête d'écouter l'IA");
				verrou.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void seReveiller() {
		synchronized (verrou) {
			verrou.notify();
			System.err.println("Le robot recommence à écouter l'IA");
		}
	}

	public Coordonnee getCoordonnees() {
		return coordonnees;
	}


	public void setCoordonnees(Coordonnee coordonnees) {
		this.coordonnees =  coordonnees;
	}
}
