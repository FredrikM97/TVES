package test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import main.Tesla;
/*
 * Assumption:
 * Sensors are same place on the front
 * 
 * In MoveForward does situation assume that we have one sensor in the front and one in the back? Or what is the specification of the sensors
 */
class mainTest {
	
	
	@Before 
	public void initialize() {
		
	}
	
	@Test
	void MoveForward(){
		// How do I change the sensor?????
		Tesla car = new Tesla();
		assertEquals(1, car.MoveForward().position);
		
	}
	
	
	@Test
    void MoveBackward(){}
    
    
	
	@Test
	void isEmpty(){}
	

    
	@Test
    void Park(){}
    
    
	@Test
    void UnPark(){}
    
    
	@Test
    void WhereIs(){}
	
    
	@Test
    void querySensors(){}
    
    
	@Test
    void filterNoise() {}
    
	
}

/**
 * Calculate the standard deviation for which would be considered noise or not
 * 
 * @param sensors Array of sensors
 * 
 * <br>
 * <b>Pre-condition</b> len(sensors) >= 10 Test-cases:
 */