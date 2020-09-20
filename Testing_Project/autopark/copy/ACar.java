package autopark.copy;

public abstract class ACar extends CarState {
	public Sensor ultrasound1 = null;
	public Sensor ultrasound2 = null;
	public Actuator moveCar = null;
	public Actuator parallelPark = null;
	public int streetLength = 500;
	public int carWidth = 170;
	public int stdThreshold = 10;

	/**
	 * This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described below and
	 * returns a data structure that contains the current position of the car, and the situation of the detected parking
	 * places up to now. The car cannot be moved forward beyond the end of the street. <br>
	 * 
	 * <br>
	 * <b>Pre-conditions:</b>
	 * <ul>
	 * <li>position &lt; streetLength</li>
	 * <li>moveCar instanceof autopark.Actuator</li>
	 * <li>!parked</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b> None
	 * 
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return State of the car
	 */
	public abstract StatusWrapper<CarState> moveForward();

	/**
	 * This method queries the two ultrasound sensors at least 5 times and filters the noise in their results and
	 * returns the distance to the nearest object in the right hand side. If one sensor is detected to continuously
	 * return very noisy output, it is completely disregarded. <br>
	 * 
	 * <br>
	 * <b>Pre-condition:</b>
	 * <ul>
	 * <li>ultrasound1 instanceof autopark.Sensor</li>
	 * <li>ultrasound2 instanceof autopark.Sensor</li>
	 * <li>sensorRead >= carWidth</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b> None
	 * 
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return Whether or not the current space observed by the sensors is empty
	 */
	public abstract StatusWrapper<Boolean> isEmpty();

	/**
	 * The same as above; only it moves the car 1 meter backwards. The car cannot be moved behind if it is already at
	 * the beginning of the street. <br>
	 * 
	 * <br>
	 * <b>Pre-conditions:</b>
	 * <ul>
	 * <li>0 &lt; position</li>
	 * <li>moveCar instanceof autopark.Actuator</li>
	 * <li>!parked</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b> None
	 * 
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return State of the car
	 */
	public abstract StatusWrapper<CarState> moveBackward();

	/**
	 * It moves the car to the beginning of the current 5 meter free stretch of parking place, if it is already detected
	 * or moves the car forwards towards the end of the street until such a stretch is detected. Then it performs a
	 * pre-programmed reverse parallel parking maneuver. <br>
	 * 
	 * <br>
	 * <b>Pre-conditions:</b> <br>
	 * <br>
	 * 
	 * <ul>
	 * <li>!parked</li>
	 * <li>parallelPark instanceof autopark.Actuator</li>
	 * <li>5 &lt;= parkingSpace</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b>
	 * <ul>
	 * <li>parked</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return If parking was successful or not
	 */
	public abstract StatusWrapper<Boolean> park();

	/**
	 * It moves the car forward (and to left) to front of the parking place, if it is parked. <br>
	 * 
	 * <br>
	 * <b>Pre-conditions:</b> <br>
	 * 
	 * <ul>
	 * <li>parked</li>
	 * <li>moveCar instanceof autopark.Actuator</li>
	 * <li></li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b>
	 * <ul>
	 * <li>!parked</li>
	 * </ul>
	 * 
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return If un-parking was successful or not
	 */
	public abstract StatusWrapper<Boolean> unPark();

	/**
	 * This method returns the current position of the car in the street as well as its situation (whether it is parked
	 * or not). <br>
	 * 
	 * <br>
	 * <b>Pre-condition:</b> None
	 * 
	 * <br>
	 * <b>Post-condition:</b>
	 * <ul>
	 * <li>this.compareTo(CarState) == 0</li>
	 * </ul>
	 * <br>
	 * <b>Test-cases:</b>
	 * 
	 * @return State of the car
	 */
	public abstract CarState whereIs();
}
