package main;

public abstract class ACar extends CarState{
	
	/**
	 * Move car 1 meter forward from current position, returns current position and
	 * number of detected parking spaces
	 * 
	 * <br>
	 * <b>Pre-condition</b> if position &lt; 500
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new - 1
	 * 
	 * @return data structure with position and situation 
	 * 
	 * <b>Test-cases:
	 * 
	 * 
	 */
	public abstract CarState MoveForward();
	
	/**
	 * Move car 1 meter backwards, returns current position and number of detected
	 * parking spaces
	 * 
	 * <br>
	 * <b>Pre-condition</b> if position > 0 <br>
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new + 1
	 * 
	 * <br>
	 * <b>Post-condition</b> if not empty number of detected parking spaces should
	 * be 0
	 * 
	 * @return data structure with position and number of detected parking spaces
	 *         
	 * <b>Test-cases:
	 */
	public abstract CarState MoveBackward();
	
	/**
	 * Query sensors and filter noise based on 5 input signals, Return the distance
	 * of nearest object. If average value is greater than threshhold the place is
	 * considered empty.
	 * 
	 * <br>
	 * <b>Pre-condition</b> Sensors exists
	 * 
	 * @return average if average >= 5 return boolean True 
	 * 
	 * <b>Test-cases:
	 */
	public abstract boolean isEmpty();
	
	/**
	 * Check for availible spaces, if space >=5, MoveBackwards and park, otherwise
	 * continue to search for parking
	 * 
	 * <br>
	 * <b>Pre-condition</b> if position >= 5
	 * 
	 * <br>
	 * <b>Pre-condition</b> if parked == False
	 * 
	 * <br>
	 * <b>Post-condition</b> if parking exists but parking is false
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new + 5 
	 * 
	 * <b>Test-cases:
	 */
	public abstract void Park();
	
	/**
	 * Move out from parking and MoveForward 5 steps to be in front of parking
	 * 
	 * <br>
	 * <b>Pre-condition</b> if position >= 0
	 * 
	 * <br>
	 * <b>Pre-condition</b> if parked == True
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new - 5 
	 * 
	 * <b>Test-cases:
	 */
	public abstract void UnPark();
	
	/**
	 * Returns the current position of car and situation if car is parked or not
	 * 
	 * <br>
	 * <b>Post-condition</b> position <= 500 and position >=0
	 * 
	 * @return position and state of car 
	 * 
	 * <b>Test-cases:
	 */
	public abstract CarState WhereIs();
}

class Tesla extends ACar {
	private Actuator gottaMovit;
	private Sensor sensor;
	
	public Tesla() {}

	
	public CarState MoveForward() {
		if (position < 500) {
			// Do some error
		}
		position += 1;
		if (isEmpty()) {
			parkingSpace += 1;
		} else {
			parkingSpace = 0;
		}
		return new CarState(this);
	}

	
	public CarState MoveBackward() {
		if (position > 0) {
			// Do some error
		}
		position -= 1;
		if (isEmpty()) {
			parkingSpace -= 1;
		} else {
			parkingSpace = 0;
		}
		return new CarState(this);
	}

	
	public boolean isEmpty() {
		// Query sensors 5 times
		// Filter noise
		// if distance > threshhold and within boundery -> is Empty equals true
		return true;
	}

	
	public void Park() {
		if (parked) {
			/* TODO already parked!); */ }
		while (parkingSpace < 5 || position > 500) {
			MoveForward();
		}
		System.out.println("Pre-programmed reverse parallel parking maneuve");
	}

	
	public void UnPark() {

	}

	
	public CarState WhereIs() {
		return new CarState(this);
	}

	
	public void filterNoise() {

	}
}