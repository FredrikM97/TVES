package test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import main.Tesla;

class mainTest {
	Tesla car;
	
	@Before 
	public void initialize() {
		car = new Tesla();
	}
	
	@Test
	void moveForward_PositionEqualsStreetLength_CarState(){
		car.
		
	}
	@Test
	void moveForward_PositionEqualsStreetLength_IllegalStateException(){
		car.
		
	}
	@Test
	void moveForward_InstanceOfActuatorExists_NullPointerException(){
		car.
		
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