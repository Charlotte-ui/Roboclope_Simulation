import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

//In Pi4j PWM values go from 0 to 100
// PWM --> https://fr.wikipedia.org/wiki/Modulation_de_largeur_d%27impulsion 

// https://javatutorial.net/raspberry-pi-control-dc-motor-speed-and-direction-java

public class Actionneur  {
	
	
	// create gpio controller instance
	final GpioController gpio = GpioFactory.getInstance();
	// gpio.shutdown(); --> pour arrêter
	
	GpioPinDigitalOutput pinLeftOutput;
	GpioPinDigitalOutput pinRightOutput;
	GpioPinDigitalOutput pinSynchroOutput;
	
	public Actionneur(Pin pinLeft, Pin pinRight, Pin pinSynchro) {
		pinLeftOutput = gpio.provisionDigitalOutputPin(pinLeft, "PinLeft");;
		pinRightOutput = gpio.provisionDigitalOutputPin(pinRight, "PinRight");;
		pinSynchroOutput = gpio.provisionDigitalOutputPin(pinSynchro, "PinSynchro");
		
		// initialize wiringPi library, this is needed for PWM
        Gpio.wiringPiSetup();
        
        // softPwmCreate(int pin, int value, int range)
        // the range is set like (min=0 ; max=100)
        SoftPwm.softPwmCreate(pinLeft.getAddress(), 0, 100);
        SoftPwm.softPwmCreate(pinRight.getAddress(), 0, 100);
        SoftPwm.softPwmCreate(pinSynchro.getAddress(), 0, 100);
        
	}
	
	//rotate motor clockwise for 3 seconds
	public void rotateMotorClockwise () {
		pinLeftOutput.high();  
		pinRightOutput.low();
		pinSynchroOutput.high(); 
     // wait 3 seconds
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//rotate motor counter clockwise for 3 seconds
		public void rotateMotorCounterClockwise () {
			pinLeftOutput.low();  
			pinRightOutput.high();
			pinSynchroOutput.high(); 
	     // wait 3 seconds
	        try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
        // stop motor
		public void stop () {
			pinSynchroOutput.low();
		}
		
		
	    public void setSpeed (int speed) throws InterruptedException {
	        // softPwmWrite(int pin, int value)
	        // This updates the PWM value on the given pin. The value is checked to
	        // be in-range and pins that haven't previously been initialized via 
	        // softPwmCreate will be silently ignored.
	        SoftPwm.softPwmWrite(pinLeftOutput.getPin().getAddress(), speed);
	        SoftPwm.softPwmWrite(pinRightOutput.getPin().getAddress(), speed);
	        SoftPwm.softPwmWrite(pinSynchroOutput.getPin().getAddress(), speed);
	    }
		
		
		
		

	public void avance () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
