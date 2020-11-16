
public class Capteur implements Runnable {
	static final int[] NO_COORDONNEES = new int[2];
	private Object verrouCapteur=new Object();

	private boolean detection;
	private int[] coordonnees ; 
	private int distance ;
	
	public Capteur() {
		super();
		detection = false;
		coordonnees = NO_COORDONNEES;
		distance = -1;
	}


	@Override
	public void run() {

		while (true) {
			detecter ();
		}
		
	}
	
	private void detecter () {	
		if (contactIA ()) {
			System.err.println("Contact IA");
			try {
				setParameters(true, new int[] {(int)(Math.random()*10),(int)(Math.random()*10)},(int)(Math.random()*10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			attendre();
		}	
	}
	
	//methode qui simule l'envoit de signal de l'IA
	private boolean contactIA () {
		try {
			Thread.sleep(1000); // on attend 10s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ((int)(Math.random()*10) ==0) ?true:false;
	}
	
	private synchronized void setParameters(boolean detection, int[] coordonnees, int distance) throws InterruptedException {
		setDetection(detection);
		setCoordonnees(coordonnees);
		setDistance(distance);
	}
	
	public void reinitializeParameters() {
		try {
			setParameters(false, NO_COORDONNEES, -1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void attendre() {
		synchronized (verrouCapteur) {
			try {
				System.err.println("Le robot arrête d'écouter l'IA");
				verrouCapteur.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void seReveiller() {
		synchronized (verrouCapteur) {
			verrouCapteur.notify();
			System.err.println("Le robot recommence à écouter l'IA");
		}
	}
	

	public boolean isDetection() {
		return detection;
	}


	public void setDetection(boolean detection) {
		this.detection = detection;
	}


	public int[] getCoordonnees() {
		return coordonnees;
	}


	public void setCoordonnees(int[] coordonnees) {
		this.coordonnees = coordonnees;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	
	

}
