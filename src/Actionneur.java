
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;



public class Actionneur  {
	//http://wiringpi.com/pins/ 
	// create gpio controller instance
	// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    // (configure pin edge to both rising and falling to get notified for HIGH and LOW state
    // changes)
	final GpioController gpio = GpioFactory.getInstance();
	//INPUT
	GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,             // PIN NUMBER
            													"MyButton",                   // PIN FRIENDLY NAME (optional)
            													PinPullResistance.PULL_DOWN); // PIN RESISTANCE (optional)
	
	// provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
	// OUTPUT
	GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,   // PIN NUMBER
	                                                           "My LED",           // PIN FRIENDLY NAME (optional)
	                                                           PinState.LOW);      // PIN STARTUP STATE (optional)
	
	public void avance () {
		try {
			Thread.sleep(1000); // on attend 1s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
