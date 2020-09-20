package Testing;

import org.junit.Before;
import org.junit.Test;

import autopark.Actuator;
import autopark.Car;
import autopark.CarState;
import autopark.Sensor;
import autopark.StatusWrapper;

public class CarTest {
	Car wroom;
	Actuator workingActuator;
	Actuator brokenActuator;
	Sensor sensor200;
	Sensor sensor0;
	Sensor sensorSTD;
	public static boolean toggle = true;

	@Before
	public void setUp() throws Exception {
		wroom = new Car();
		workingActuator = new Actuator() {
			public boolean activate(int... args) {
				return true;
			}
		};
		brokenActuator = new Actuator() {
			public boolean activate(int... args) {
				return false;
			}
		};
		sensor200 = new Sensor() {
			public int read() {
				return 200;
			}
		};
		sensor0  = new Sensor() {
			public int read() {
				return 0;
			}
		};
		sensorSTD  = new Sensor() {
			public int read() {
				toggle = !toggle;
				return toggle ? 200 : 0;
			}
		};
	}

	/**
	 * start of moveForward tests
	 */
	@Test
	public void moveForward_noErrors_CarState() {
		wroom.position = wroom.streetLength-1;
		wroom.moveCar = workingActuator;
		wroom.parked = false;
		assert (wroom.moveForward().getContent() instanceof CarState);
	}
	
	@Test
	public void moveForward_parked_notPosible() {
		wroom.position = wroom.streetLength-1;
		wroom.moveCar = workingActuator;
		wroom.parked = true;
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveForward_noSpace_CarState() {
		wroom.position = wroom.streetLength;
		wroom.moveCar = workingActuator;
		wroom.parked = false;
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveForward_noInit_noInit() {
		wroom.position = wroom.streetLength-1;
		wroom.moveCar = null;
		wroom.parked = false;
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void moveForward_brokenActuator_unexpectedState() {
		wroom.position = wroom.streetLength-1;
		wroom.moveCar = brokenActuator;
		wroom.parked = false;
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}
	
	/**
	 * start of isEmpty tests
	 */
	@Test
	public void isEmpty_noErrors_true() {
		wroom.ultrasound1 = sensor200;
		wroom.ultrasound2 = sensor200;
		assert (wroom.isEmpty().getContent());
	}
	
	@Test
	public void isEmpty_notEmpty_false() {
		wroom.ultrasound1 = sensor0;
		wroom.ultrasound2 = sensor0;
		assert (!wroom.isEmpty().getContent());
	}

	@Test
	public void isEmpty_noInit_noInit() {
		wroom.ultrasound1 = null;
		wroom.ultrasound2 = null;
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	/**
	 * start of moveBackward tests
	 */
	@Test
	public void moveBackward_noError_CarState() {
		wroom.parked = false;
		wroom.moveCar = workingActuator;
		wroom.position = 1;
		assert (wroom.moveBackward().getContent() instanceof CarState);
	}

	@Test
	public void moveBackward_parked_CarState() {
		wroom.parked = true;	
		wroom.moveCar = workingActuator;
		wroom.position = 1;
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveBackward_noSpace_CarState() {
		wroom.parked = false;
		wroom.moveCar = workingActuator;
		wroom.position = 0;
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveBackward_noInit_noInit() {
		wroom.parked = false;
		wroom.moveCar = null;
		wroom.position = 1;
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void moveBackward_brokenActuator_unexpectedState() {
		wroom.position = wroom.streetLength;
		wroom.moveCar = brokenActuator;
		wroom.parked = false;
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}
	
	/**
	 * start of park tests
	 */
	@Test
	public void park_noError_true() {
		wroom.parked = false;
		wroom.parallelPark = workingActuator;
		wroom.parkingSpace = 5;
		assert (wroom.park().getContent());
	}

	@Test
	public void park_noSpace_notPossible() {
		wroom.parked = false;
		wroom.parallelPark = workingActuator;
		wroom.parkingSpace = 4;
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_parked_notPossible() {
		wroom.parked = true;
		wroom.parallelPark = workingActuator;
		wroom.parkingSpace = 5;
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_noInit_noInit() {
		wroom.parked = false;
		wroom.parallelPark = null;
		wroom.parkingSpace = 5;
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void park_brokenActuator_unexpectedState() {
		wroom.parked = false;
		wroom.parallelPark = brokenActuator;
		wroom.parkingSpace = 5;
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	/**
	 * start of unPark tests
	 */
	@Test
	public void unPark_noError_true() {
		wroom.parked = true;
		wroom.moveCar = workingActuator;
		assert (wroom.unPark().getContent());
	}

	@Test
	public void unPark_notParked_true() {
		wroom.parked = false;
		wroom.moveCar = workingActuator;
		assert (wroom.unPark().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void unPark_noInit_noInit() {
		wroom.parked = true;
		wroom.moveCar = null;
		assert (wroom.unPark().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void unPark_actuatorFail_unexpectedState() {
		wroom.parked = true;
		wroom.moveCar = brokenActuator;
		assert (wroom.unPark().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	/**
	 * start of whereIs tests
	 */
	@Test
	public void whereIs_anyState_CarState() {
		assert (wroom.compareTo(wroom.whereIs()) == 0);

		// Assert the opposite to test the compareTo method
		CarState lastState = wroom.whereIs();
		wroom.position = wroom.position - 1;
		assert (wroom.compareTo(lastState) != 0);
	}
	
	
	/******************************************************************************** 
	 * 								Structural testing								*
	 ********************************************************************************/

	/**
	 * start of moveForward tests
	 */
	@Test
	public void moveForward_workingSensors_CarState() {
		wroom.position = wroom.streetLength-1;
		wroom.moveCar = workingActuator;
		wroom.parked = false;
		wroom.ultrasound1 = sensor200;
		wroom.ultrasound2 = sensor200;
		assert (wroom.moveForward().getContent() instanceof CarState);
	}
	
	/**
	 * start of isEmpty tests
	 */
	@Test
	public void isEmpty_sensor2noInit_noInit() {
		wroom.ultrasound1 = sensor200;
		wroom.ultrasound2 = null;
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void isEmpty_stdSensors_notPossible() {
		wroom.ultrasound1 = sensorSTD;
		wroom.ultrasound2 = sensorSTD;
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void isEmpty_Sensors1isStd_true() {
		wroom.ultrasound1 = sensorSTD;
		wroom.ultrasound2 = sensor200;
		assert (wroom.isEmpty().getContent());
	}
	
	@Test
	public void isEmpty_Sensors2isStd_true() {
		wroom.ultrasound1 = sensor200;
		wroom.ultrasound2 = sensorSTD;
		assert (wroom.isEmpty().getContent());
	}
	
	/**
	 * start of moveBackward tests
	 */
	@Test
	public void moveBackward_workingSensor_CarState() {
		wroom.parked = false;
		wroom.moveCar = workingActuator;
		wroom.position = 1;
		wroom.ultrasound1 = sensor200;
		wroom.ultrasound2 = sensor200;
		assert (wroom.moveBackward().getContent() instanceof CarState);
	}
	
	/**
	 * start of StatusWrapper tests
	 */
	@Test
	public void StatusWrapper_covarage_true() {
		StatusWrapper<Boolean> test = new StatusWrapper<>(true,StatusWrapper.OK);
		StatusWrapper<Boolean> test2 = new StatusWrapper<>(StatusWrapper.OK);
		test.setContent(false);
		test2.setStatus(StatusWrapper.NO_INIT);
		assert (!test.getContent());
		assert (test2.getStatus().equals(StatusWrapper.NO_INIT));
	}
	
	/**
	 * start of CarState tests
	 */
	@Test
	public void CarState_covarage_true() {
		CarState test = new CarState();
		test.parked = true;
		assert (test.hashCode() == 1<<31);
	}
}
