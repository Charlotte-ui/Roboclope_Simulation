/**
 * Container permettant à tout les threads de savoir quand il faurt s'arrêter (le java c'est relou car on ne peut pas passer les types valeurs en references)... 
 * @author Charlotte Marty
 *
 */
public class On {
	private boolean on;

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}
	

}
