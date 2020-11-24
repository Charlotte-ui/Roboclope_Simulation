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
	
    GpioPinDigitalOutput motor1pinA; // tous les pin des moteurs en output
    GpioPinDigitalOutput motor1pinB;
    GpioPinDigitalOutput motor1pinE;
    GpioPinDigitalOutput motor2pinA;
    GpioPinDigitalOutput motor2pinB;
    GpioPinDigitalOutput motor2pinE;
	
	public Actionneur(int portM1A, int portM1B, int portM1E, int portM2A, int portM2B, int portM2E) {
		
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
	
	

	//rotate a motor clockwise for some seconds
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

	//rotate a motor counter clockwise for some seconds
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
	
    // stop one motor
	public void stopMotor (int motor) {
		if (motor==1) {
			motor1pinE.low();
		}
		if (motor==2) {
		
			motor2pinE.low();
		}
	}

	//rotate both motors clockwise for n seconds
	public void avance (int second) {
		rotateMotorClockwise(1,0); //premier moteur
		rotateMotorClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	//rotate both motors counter clockwise for n seconds
	public void recule (int second) {
		rotateMotorCounterClockwise(1,0); //premier moteur
		rotateMotorCounterClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	// attention, en fonction des branchements droite et  gauche sont inversé
	//rotate motor 1 clockwise and motor 2 counter clockwise for some seconds
	public void droite (int second) {
		rotateMotorClockwise(1,0); //premier moteur
		rotateMotorCounterClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	// attention, en fonction des branchements droite et  gauche sont inversé
	//rotate motor 2 clockwise and motor 1 counter clockwise for some seconds
	public void gauche (int second) {
		rotateMotorCounterClockwise(1,0); //premier moteur
		rotateMotorClockwise(2,0); //second moteur

		// wait some seconds
		wait(second);
	}

	// stop both motors
	public void stop () {
		stopMotor(1);
		stopMotor(2);
	}



	public void shutdown() {
		gpio.shutdown();	
	}


	private void wait (int n) {
		try {
			Thread.sleep(n*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// motorPin désigne les port A et B des moteurs 1 et 2. A fait avancer, B reculer
	// la vitesse est en pourcentage entre 1 et 100
	// This updates the PWM value on the given pin. The value is checked to be in-range and pins that haven't previously been initialized via  softPwmCreate will be silently ignored.
	public void setSpeed (int motorPin, int speed) throws InterruptedException {
		SoftPwm.softPwmWrite(motorPin, speed);
	}


	// méthode qui attend, uniquement pour la simulation
	public void simule_avancee () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
