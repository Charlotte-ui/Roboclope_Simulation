import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Récupère les fichier crés par l'IA
 * @author Charlotte Marty
 *
 */
public class RecupererDataIA implements Runnable {
	private Object verrou=new Object();
	private Coordonnee coordonnees ;
	private On interrupteur;
	Memoire m;
	
	/**
	 * Constructeur 
	 * @param interrupteur
	 * @param m
	 */
	public RecupererDataIA(On interrupteur, Memoire m) {
		super();
		coordonnees = Coordonnee.getNO_COORDONNEES();
		this.m=m;
		this.interrupteur=interrupteur;
		m.setVerrou(verrou);
	}

	/**
	 * Programme du thread
	 */
	public void run() {
		int frame =1; // correspond au numéro de frame de l'image
		
		while (interrupteur.isOn()) {
			detecter (frame);
			frame++;
		}
	}
	
	/**
	 * Cherche les résultats concernant une frame particulière
	 * @param frame
	 */
	private void detecter (int frame) {	
		boolean contactIA = false;
		double x=0, y=0, d=0;
		
		while (!contactIA) {
	        File f = new File("python/"+frame+".txt");
	        if(f.canRead()) {
	        	Path chemin = Paths.get("python/"+frame+".txt");
		        InputStream input = null;
		        try {
		            input = Files.newInputStream(chemin);
		             
		            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		            x=Double.parseDouble(reader.readLine());
		            y=Double.parseDouble(reader.readLine());
		            d=Double.parseDouble(reader.readLine());
		            contactIA=true;
		            input.close();
		 
		        } catch (IOException e) {
		            System.out.println("Message " + e);
		        }
	        }
			
		}


		if (contactIA) {
			System.err.println("Contact IA");
			setCoordonnees(new Coordonnee(x,y,d));
			m.addCoordonnes(coordonnees);
			contactIA=false;
		}
	}
	
	public void reinitializeParameters() {
		setCoordonnees(Coordonnee.getNO_COORDONNEES());
		m.reinitializeParameters();
	}
	

	public Coordonnee getCoordonnees() {
		return coordonnees;
	}


	public void setCoordonnees(Coordonnee coordonnees) {
		this.coordonnees =  coordonnees;
	}

}
