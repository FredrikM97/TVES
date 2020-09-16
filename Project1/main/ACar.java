package main;

import java.util.ArrayList;

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
	 * <br>Test-cases:
	 * 
	 * @return data structure containing position and number of detected parking spaces 
	 * 
	 * 
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
	 * <br>Test-cases:
	 * 
	 * @return data structure containing position and number of detected parking spaces
	 *         
	 * 
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
	 * <br>Test-cases:
	 * 
	 * @return average if average >= 5 return boolean True 
	 * 
	 * 
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
	 * <br>Test-cases:
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new + 5 
	 * 
	 * 
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
	 * <br>Test-cases:
	 * 
	 * <br>
	 * <b>Post-condition</b> if position = position_new - 5 
	 * 
	 * 
	 */
	public abstract void UnPark();
	
	/**
	 * Returns the current position of car and situation if car is parked or not
	 * 
	 * <br>
	 * <b>Post-condition</b> position <= 500 and position >=0
	 * 
	 * <br>Test-cases:
	 * 
	 * @return position and state of car 
	 * 
	 * 
	 */
	public abstract CarState WhereIs();
}
