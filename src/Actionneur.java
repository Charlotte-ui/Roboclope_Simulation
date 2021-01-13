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

// erreur normal � l'ex�cution https://github.com/Pi4J/pi4j/issues/411
// doit �tre lanc�e sur une rasberry pi apr�s avoir install� ce truc http://wiringpi.com/download-and-install/

/**
 * Controle les mouvements du robot
 * @author Charlotte Marty
 *
 */
public class Actionneur  {
	
	// create gpio controller instance
	
	/**
	 * gpio controller
	 */
	GpioController gpio;
	/**
	 * output pin du port A du moteur 1 
	 */
    GpioPinDigitalOutput motor1pinA; // tous les pin des moteurs en output
    /**
	 * output pin du port B du moteur 1 
	 */
    GpioPinDigitalOutput motor1pinB;
    /**
	 * output pin du port E du moteur 1 
	 */
    GpioPinDigitalOutput motor1pinE;
    /**
	 * output pin du port A du moteur 2 
	 */
    GpioPinDigitalOutput motor2pinA;
    /**
	 * output pin du port B du moteur 2 
	 */
    GpioPinDigitalOutput motor2pinB;
    /**
	 * output pin du port E du moteur 2 
	 */
    GpioPinDigitalOutput motor2pinE;
	
    /**
     * Constructeur de l'actionneur 
     * @param portM1A
     * @param portM1B
     * @param portM1E
     * @param portM2A
     * @param portM2B
     * @param portM2E
     */
	public Actionneur(int portM1A, int portM1B, int portM1E, int portM2A, int portM2B, int portM2E) {
		
		gpio = GpioFactory.getInstance();
				
		motor1pinA = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM1A), "m1A");
	    motor1pinB = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM1B), "m1B");
	    motor1pinE = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM1E), "m1E");
	    motor2pinA = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM2A), "m2A");
	    motor2pinB = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM2B), "m2B");
	    motor2pinE = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(portM2E), "m2E");
		
		// initialize wiringPi library, this is needed for PWM
        Gpio.wiringPiSetup();
        
        // softPwmCreate(int pin, int value, int range)
        // the range is set like (min=0 ; max=100)
        
        SoftPwm.softPwmCreate(portM1A, 0, 100);
        SoftPwm.softPwmCreate(portM1B, 0, 100);
        SoftPwm.softPwmCreate(portM2A, 0, 100);
        SoftPwm.softPwmCreate(portM2B, 0, 100);
        
        
        
	}
	
	/**
	 * Fait tourner le moteur clockwise pendant un certain nombre de secondes
	 * @param motor
	 * @param second
	 */
	public void rotateMotorClockwise (int motor, int second) {
		if (motor==1) {
			motor1pinA.high();
			motor1pinB.low();
			motor1pinE.high();
		}
		if (motor==2) {
			motor2pinA.high();
			motor2pinB.low();
			motor2pinE.high();
		}

		// wait some seconds
		wait(second);
	}

	/**
	 * Fait tourner le moteur counter-clockwise pendant un certain nombre de secondes
	 * @param motor
	 * @param second
	 */
	public void rotateMotorCounterClockwise (int motor, int second) {
		if (motor==1) {
			motor1pinA.low();
			motor1pinB.high();
			motor1pinE.high();
		}
		if (motor==2) {
			motor2pinA.low();
			motor2pinB.high();
			motor2pinE.high();
		}

		// wait some seconds
		wait(second);
	}
	
	/**
	 * stop un moteur
	 * @param motor
	 */
	public void stopMotor (int motor) {
		if (motor==1) {
			motor1pinE.low();
		}
		if (motor==2) {
		
			motor2pinE.low();
		}
	}

	/**
	 * Fait tourner les deux moteurs clockwise pendant un certain nombre de secondes
	 * @param second
	 */
	public void avance (int second) {
		rotateMotorClockwise(1,0); //premier moteur
		rotateMotorClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	/**
	 * Fait tourner les deux moteurs counter-clockwise pendant un certain nombre de secondes
	 * @param second
	 */	
	public void recule (int second) {
		rotateMotorCounterClockwise(1,0); //premier moteur
		rotateMotorCounterClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	/**
	 * Fait tourner moteur 1 clockwise et moteur 2 counter-clockwise pendant un certain nombre de secondes
	 * <b>attention, en fonction des branchements droite et  gauche sont inversee</b> 
	 * @param second
	 */	
	public void droite (int second) {
		rotateMotorClockwise(1,0); //premier moteur
		rotateMotorCounterClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}
	
	/**
	 * Fait tourner moteur 2 clockwise et moteur 1 counter-clockwise pendant un certain nombre de secondes
	 * <b>attention, en fonction des branchements droite et  gauche sont inversee</b> 
	 * @param second
	 */	
	public void gauche (int second) {
		rotateMotorCounterClockwise(1,0); //premier moteur
		rotateMotorClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	/**
	 * stop les deux moteurs
	 */
	public void stop () {
		stopMotor(1);
		stopMotor(2);
	}
	
	/**
	 * shutdown le gpio controller
	 */
	public void shutdown() {
		gpio.shutdown();	
	}

	/**
	 * fait patienter le thread pendant n secondes
	 * @param n
	 */
	private void wait (int n) {
		try {
			Thread.sleep(n*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mets à jour la PWM value du pin donnée. La valeur est vérifiée pour être dans la plage (entre 1 et 100) et les pins qui n'ont pas été préalablement initialisées via softPwmCreate seront ignorées.
	 * <b>motorPin doit concerner les ports A et B. A fait avancer, B reculer</b>
	 * @param motorPin
	 * @param speed
	 * @throws InterruptedException
	 */
	public void setSpeed (int motorPin, int speed) throws InterruptedException {
		SoftPwm.softPwmWrite(motorPin, speed);
	}
	

}
