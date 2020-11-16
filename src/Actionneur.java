
public class Actionneur  {

	
	public void avance () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
