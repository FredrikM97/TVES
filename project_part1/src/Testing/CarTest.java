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
		wroom.setPosition(wroom.getStreetLength()-1);
		wroom.setMoveCar(workingActuator);
		
		wroom.setParked(false);
		assert (wroom.moveForward().getContent() instanceof CarState);
	}
	
	@Test
	public void moveForward_parked_notPossible() {
		wroom.setPosition(wroom.getStreetLength()-1);
		wroom.setMoveCar(workingActuator);
		wroom.setParked(true);
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveForward_noSpace_notPossible() {
		wroom.setPosition(wroom.getStreetLength());
		wroom.setMoveCar(workingActuator);
		wroom.setParked(false);
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveForward_noInit_noInit() {
		wroom.setPosition(wroom.getStreetLength()-1);
		
		wroom.setMoveCar(null);
		wroom.setParked(false);
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void moveForward_brokenActuator_unexpectedState() {
		wroom.setPosition(wroom.getStreetLength()-1);
		wroom.setMoveCar(brokenActuator);
		wroom.setParked(false);
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}
	
	/**
	 * start of isEmpty tests
	 */
	@Test
	public void isEmpty_noErrors_true() {
		wroom.setUltrasound1(sensor200);
		wroom.setUltrasound2(sensor200);
		assert (wroom.isEmpty().getContent());
	}
	
	@Test
	public void isEmpty_notEmpty_false() {
		wroom.setUltrasound1(sensor0);
		wroom.setUltrasound2(sensor0);
		assert (!wroom.isEmpty().getContent());
	}

	@Test
	public void isEmpty_noInit_noInit() {
		wroom.setUltrasound1(null);
		wroom.setUltrasound2(null);
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	/**
	 * start of moveBackward tests
	 */
	@Test
	public void moveBackward_noError_CarState() {
		wroom.setParked(false);
		wroom.setMoveCar(workingActuator);
		wroom.setPosition(1);
		assert (wroom.moveBackward().getContent() instanceof CarState);
	}

	@Test
	public void moveBackward_parked_notPossible() {
		wroom.setParked(true);
		wroom.setMoveCar(workingActuator);
		wroom.setPosition(1);
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveBackward_noSpace_notPossible() {
		wroom.setParked(false);
		wroom.setMoveCar(workingActuator);
		wroom.setPosition(0);
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void moveBackward_noInit_noInit() {
		wroom.setParked(false);
		wroom.setMoveCar(null);
		wroom.setPosition(1);
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void moveBackward_brokenActuator_unexpectedState() {
		wroom.setPosition(wroom.getStreetLength());
		wroom.setMoveCar(brokenActuator);
		wroom.setParked(false);
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}
	
	/**
	 * start of park tests
	 */
	@Test
	public void park_noError_true() {
		wroom.setParked(false);
		wroom.setParallelPark(workingActuator);
		
		wroom.setParkingSpace(5);
		assert (wroom.park().getContent());
	}

	@Test
	public void park_noSpace_notPossible() {
		wroom.setParked(false);
		wroom.setParallelPark(workingActuator);
		wroom.setParkingSpace(4);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_parked_notPossible() {
		wroom.setParked(true);
		wroom.setParallelPark(workingActuator);
		wroom.setParkingSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_noInit_noInit() {
		wroom.setParked(false);
		wroom.setParallelPark(null);
		wroom.setParkingSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void park_brokenActuator_unexpectedState() {
		wroom.setParked(false);
		wroom.setParallelPark(brokenActuator);
		wroom.setParkingSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	/**
	 * start of unPark tests
	 */
	@Test
	public void unPark_noError_true() {
		wroom.setParked(true);
		wroom.setMoveCar(workingActuator);
		assert (wroom.unPark().getContent());
	}

	@Test
	public void unPark_notParked_true() {
		wroom.setParked(false);
		wroom.setMoveCar(workingActuator);
		assert (wroom.unPark().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void unPark_noInit_noInit() {
		wroom.setParked(true);
		wroom.setMoveCar(null);
		assert (wroom.unPark().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void unPark_actuatorFail_unexpectedState() {
		wroom.setParked(true);
		wroom.setMoveCar(brokenActuator);
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
		wroom.setPosition(wroom.getPosition()-1);
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
		wroom.setPosition(wroom.getStreetLength()-1);
		wroom.setMoveCar(workingActuator);
		wroom.setParked(false);
		wroom.setUltrasound1(sensor200);
		
		wroom.setUltrasound2(sensor200);
		assert (wroom.moveForward().getContent() instanceof CarState);
	}
	
	/**
	 * start of isEmpty tests
	 */
	@Test
	public void isEmpty_sensor2noInit_noInit() {
		wroom.setUltrasound1(sensor200);
		wroom.setUltrasound2(null);
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}
	
	@Test
	public void isEmpty_stdSensors_notPossible() {
		wroom.setUltrasound1(sensorSTD);
		wroom.setUltrasound2(sensorSTD);
		assert (wroom.isEmpty().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}
	
	@Test
	public void isEmpty_Sensors1isStd_true() {
		wroom.setUltrasound1(sensorSTD);
		wroom.setUltrasound2(sensor200);
		assert (wroom.isEmpty().getContent());
	}
	
	@Test
	public void isEmpty_Sensors2isStd_true() {
		wroom.setUltrasound1(sensor200);
		wroom.setUltrasound2(sensorSTD);
		assert (wroom.isEmpty().getContent());
	}
	
	/**
	 * start of moveBackward tests
	 */
	@Test
	public void moveBackward_workingSensor_CarState() {
		wroom.setParked(false);
		wroom.setMoveCar(workingActuator);
		wroom.setPosition(1);
		wroom.setUltrasound1(sensor200);
		wroom.setUltrasound2(sensor200);
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
		test.setParked(true);
		assert (test.hashCode() == 1<<31);
	}
}
