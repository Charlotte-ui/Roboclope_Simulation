
public class Coordonnee implements Comparable {
	
	final static Coordonnee NO_COORDONNEES = new Coordonnee(0,0,-1);
	
	double x;
	double y;
	double distance;
	
	public Coordonnee(double x, double y, double distance) {
		super();
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	
	// pour le moment est prioris� la position la plus proche ; on peut potentiellement changer en prennant en priorit� la position "droit devant", c�d x et y proche de 0, ou un m�lange des deux
	public int compareTo(Object o) {
		Coordonnee c = (Coordonnee)o;
		if (distance == c.distance) return 0;
        if (distance > c.distance) return 1;
        return 0;
	}

	
	double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public static Coordonnee getNO_COORDONNEES() {
		return NO_COORDONNEES;
	}



	
	
	
	
	

}
