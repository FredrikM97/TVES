package autopark;

public abstract class ACar extends CarState {
	private Sensor ultrasound1 = null;
	private Sensor ultrasound2 = null;
	private Actuator moveCar = null;
	private Actuator parallelPark = null;
	private int streetLength = 500;
	private int carWidth = 170;
	private int stdThreshold = 10;

	/**
	 * This method moves the car 1 meter forward, queries the two sensors through
	 * the isEmpty method described below and returns a data structure that contains
	 * the current position of the car, and the situation of the detected parking
	 * places up to now. The car cannot be moved forward beyond the end of the
	 * street. <br>
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
	 * <br>
	 * <b>Test-cases:</b>
	 * <ul>
	 * <li>moveForward_noErrors_CarState</li>
	 * <li>moveForward_parked_notPosible</li>
	 * <li>moveForward_noSpace_notPosible</li>
	 * <li>moveForward_noInit_noInit</li>
	 * <li>moveForward_brokenActuator_unexpectedState</li>
	 * <li>moveForward_workingSensors_CarState</li>
	 * </ul>
	 * 
	 * @return State of the car
	 */
	public abstract StatusWrapper<CarState> moveForward();

	/**
	 * This method queries the two ultrasound sensors at least 5 times and filters
	 * the noise in their results and returns the distance to the nearest object in
	 * the right hand side. If one sensor is detected to continuously return very
	 * noisy output, it is completely disregarded. <br>
	 * 
	 * <br>
	 * <b>Pre-condition:</b>
	 * <ul>
	 * <li>ultrasound1 instanceof autopark.Sensor</li>
	 * <li>ultrasound2 instanceof autopark.Sensor</li>
	 * 
	 * </ul>
	 * 
	 * <br>
	 * <b>Post-condition:</b>
	 * <ul>
	 * <li>sensorRead &gt;= carWidth</li>
	 * </ul>
	 * <br>
	 * <b>Test-cases:</b>
	 * <ul>
	 * <li>isEmpty_noErrors_true</li>
	 * <li>isEmpty_notEmpty_false</li>
	 * <li>isEmpty_noInit_noInit</li>
	 * <li>isEmpty_sensor2noInit_noInit</li>
	 * <li>isEmpty_stdSensors_notPossible</li>
	 * <li>isEmpty_Sensors1isStd_true</li>
	 * <li>isEmpty_Sensors2isStd_true</li>
	 * </ul>
	 * 
	 * @return Whether or not the current space observed by the sensors is empty
	 */
	public abstract StatusWrapper<Boolean> isEmpty();

	/**
	 * The same as above; only it moves the car 1 meter backwards. The car cannot be
	 * moved behind if it is already at the beginning of the street. <br>
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
	 * <br>
	 * <b>Test-cases:</b>
	 * <ul>
	 * <li>moveBackward_noError_CarState</li>
	 * <li>moveBackward_parked_notPossible</li>
	 * <li>moveBackward_noSpace_notPossible</li>
	 * <li>moveBackward_noInit_noInit</li>
	 * <li>moveBackward_brokenActuator_unexpectedState</li>
	 * <li>moveBackward_workingSensor_CarState</li>
	 * </ul>
	 * 
	 * @return State of the car
	 */
	public abstract StatusWrapper<CarState> moveBackward();

	/**
	 * It moves the car to the beginning of the current 5 meter free stretch of
	 * parking place, if it is already detected or moves the car forwards towards
	 * the end of the street until such a stretch is detected. Then it performs a
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
	 * <ul>
	 * <li>park_noError_true</li>
	 * <li>park_noSpace_notPossible</li>
	 * <li>park_parked_notPossible</li>
	 * <li>park_noInit_noInit</li>
	 * <li>park_brokenActuator_unexpectedState</li>
	 * </ul>
	 * 
	 * @return If parking was successful or not
	 */
	public abstract StatusWrapper<Boolean> park();

	/**
	 * It moves the car forward (and to left) to front of the parking place, if it
	 * is parked. <br>
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
	 * <ul>
	 * <li>unPark_noError_true</li>
	 * <li>unPark_notParked_true</li>
	 * <li>unPark_noInit_noInit</li>
	 * <li>unPark_actuatorFail_unexpectedState</li>
	 * 
	 * </ul>
	 * 
	 * @return If un-parking was successful or not
	 */
	public abstract StatusWrapper<Boolean> unPark();

	/**
	 * This method returns the current position of the car in the street as well as
	 * its situation (whether it is parked or not). <br>
	 * 
	 * <br>
	 * <b>Pre-condition:</b> None
	 * 
	 * <br>
	 * <br>
	 * <b>Post-condition:</b>
	 * <ul>
	 * <li>this.compareTo(CarState) == 0</li>
	 * </ul>
	 * <br>
	 * <b>Test-cases:</b>
	 * <ul>
	 * <li>whereIs_anyState_CarState</li>
	 * 
	 * </ul>
	 * 
	 * @return State of the car
	 */
	public abstract CarState whereIs();

	public Sensor getUltrasound1() {
		return ultrasound1;
	}

	public void setUltrasound1(Sensor ultrasound1) {
		this.ultrasound1 = ultrasound1;
	}

	public Sensor getUltrasound2() {
		return ultrasound2;
	}

	public void setUltrasound2(Sensor ultrasound2) {
		this.ultrasound2 = ultrasound2;
	}

	public Actuator getMoveCar() {
		return moveCar;
	}

	public void setMoveCar(Actuator moveCar) {
		this.moveCar = moveCar;
	}

	public Actuator getParallelPark() {
		return parallelPark;
	}

	public void setParallelPark(Actuator parallelPark) {
		this.parallelPark = parallelPark;
	}

	public int getStreetLength() {
		return streetLength;
	}

	public void setStreetLength(int streetLength) {
		this.streetLength = streetLength;
	}

	public int getCarWidth() {
		return carWidth;
	}

	public void setCarWidth(int carWidth) {
		this.carWidth = carWidth;
	}

	public int getStdThreshold() {
		return stdThreshold;
	}

	public void setStdThreshold(int stdThreshold) {
		this.stdThreshold = stdThreshold;
	}
}
