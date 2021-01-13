/**
 * Gère les coordonnées renvoyees parl'IA
 * @author Charlotte Marty
 *
 */
public class Coordonnee implements Comparable {
	/**
	 * Quand le robot ne doit pas avoir en mémoire de coordonées
	 */
	final static Coordonnee NO_COORDONNEES = new Coordonnee(0,0,-1);
	/**
	 * abscisse de la position du robot 
	 */
	double x;
	/**
	 * ordonné de la position du robot
	 */
	double y;
	/**
	 * distance entre le robot et le mégot (unités arbitraires)
	 * correspond à la taille de la boite détectée par l'IA
	 */
	double distance;
	
	/**
	 * Constructeur de Coordonnée
	 * @param x
	 * @param y
	 * @param distance
	 */
	public Coordonnee(double x, double y, double distance) {
		super();
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	
	/**
	 * Compare deux Coordonnées entre elles
	 * 	pour le moment est priorise la position la plus proche 
	 *  on peut potentiellement changer en prennant en priorite la position "droit devant", cad x et y proche de 0, ou un melange des deux
	 */
	public int compareTo(Object o) {
		Coordonnee c = (Coordonnee)o;
		if (distance == c.distance) return 0;
        if (distance > c.distance) return 1;
        return 0;
	}

	/**
	 * renvoit x
	 * @return x
	 */
	double getX() {
		return x;
	}
	/**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * renvoit y
	 * @return y
	 */
	public double getY() {
		return y;
	}
	/**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * renvoit la distance
	 * @return distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * 
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * renvoit l'emplacement de coordonnees vide
	 * @return NO_COORDONNEES
	 */
	public static Coordonnee getNO_COORDONNEES() {
		return NO_COORDONNEES;
	}



	
	
	
	
	

}
