package Testing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import autopark.Actuator;
import autopark.Car;
import autopark.CarState;
import autopark.Sensor;
import autopark.StatusWrapper;
import static org.mockito.Mockito.*;

public class CarTest {
	Car wroom;
	Actuator workingActuator;
	Actuator falseActuator;
	Actuator trueActuator;
	Actuator failOnBackwardActuator;
	Sensor sensor200;
	Sensor sensor0;
	Sensor sensorSTD;
	Sensor workingSensor;
	Sensor sawToothSensor;
	int carPosition;
	int[] street;

	public static boolean toggle = true;

	@Before
	public void setUp() throws Exception {
		wroom = new Car();
		carPosition = 0;
		street = new int[] { // Zeroes => Occupied, Ones => Free
				0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
				0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
				1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, };

		workingActuator = mock(Actuator.class);
		Answer<Boolean> workingActuatorAnswer = new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				carPosition += invocation.getArgumentAt(0, Integer.class);
				return true;
			}
		};
		when(workingActuator.activate()).thenAnswer(workingActuatorAnswer);
		when(workingActuator.activate(anyInt())).thenAnswer(workingActuatorAnswer);
		when(workingActuator.activate(anyInt(), anyInt())).thenAnswer(workingActuatorAnswer);

		failOnBackwardActuator = mock(Actuator.class);
		when(failOnBackwardActuator.activate(anyInt())).thenAnswer(workingActuatorAnswer);
		when(failOnBackwardActuator.activate(anyInt(), anyInt())).then(workingActuatorAnswer);
		when(failOnBackwardActuator.activate(-1)).thenReturn(false);
		failOnBackwardActuator = new Actuator() {
			public boolean activate(int... args) {
				if (args[0] == -1)
					return false;
				carPosition += args[0];
				return true;
			}
		};
		falseActuator = mock(Actuator.class);
		when(falseActuator.activate()).thenReturn(false);

		trueActuator = mock(Actuator.class);
		when(trueActuator.activate()).thenReturn(true);
		when(trueActuator.activate(anyInt())).thenReturn(true);

		workingSensor = mock(Sensor.class);
		when(workingSensor.read()).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				return street[carPosition] * 200;
			}
		});

		sensor200 = mock(Sensor.class);
		when(sensor200.read()).thenReturn(200);

		sensor0 = mock(Sensor.class);
		when(sensor0.read()).thenReturn(0);

		sensorSTD = mock(Sensor.class);
		when(sensorSTD.read()).thenAnswer(new Answer<Integer>() {
			boolean toggle = false;

			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				toggle = !toggle;
				return toggle ? 200 : 0;
			}
		});

		sawToothSensor = mock(Sensor.class);
		when(sawToothSensor.read()).thenAnswer(new Answer<Integer>() {
			int sawToothInt = 0;

			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				sawToothInt = sawToothInt < 5 ? sawToothInt + 1 : 0;
				return sawToothInt * 5; // std([0,5,10,15,20]) -> 7.0710678118654755
			}
		});
	}

	/**
	 * start of moveForward tests
	 */
	@Test
	public void moveForward_noErrors_CarState() {
		wroom.setPosition(wroom.getStreetLength() - 1);
		wroom.setMoveCar(workingActuator);

		wroom.setParked(false);
		assert (wroom.moveForward().getContent() != null);
	}

	@Test
	public void moveForward_parked_notPossible() {
		wroom.setPosition(wroom.getStreetLength() - 1);
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
		wroom.setPosition(wroom.getStreetLength() - 1);

		wroom.setMoveCar(null);
		wroom.setParked(false);
		assert (wroom.moveForward().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void moveForward_brokenActuator_unexpectedState() {
		wroom.setPosition(wroom.getStreetLength() - 1);
		wroom.setMoveCar(falseActuator);
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
		assert (wroom.moveBackward().getContent() != null);
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
		wroom.setMoveCar(falseActuator);
		wroom.setParked(false);
		assert (wroom.moveBackward().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	/**
	 * start of park tests
	 */
	@Test
	public void park_noError_true() {
		wroom.setParked(false);
		wroom.setParallelPark(trueActuator);
		wroom.setMoveCar(trueActuator);
		wroom.setParkingSpace(5);
		wroom.setBestParkSpace(5);
		assert (wroom.park().getContent());
	}

	@Test
	public void park_noSpace_notPossible() {
		wroom.setParked(false);
		wroom.setParallelPark(trueActuator);
		wroom.setMoveCar(trueActuator);
		wroom.setParkingSpace(4);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_parked_notPossible() {
		wroom.setParked(true);
		wroom.setParallelPark(trueActuator);
		wroom.setMoveCar(trueActuator);
		wroom.setParkingSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NOT_POSSIBLE));
	}

	@Test
	public void park_noInit_noInit() {
		wroom.setParked(false);
		wroom.setParallelPark(null);
		wroom.setMoveCar(trueActuator);
		wroom.setParkingSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.NO_INIT));
	}

	@Test
	public void park_brokenActuator_unexpectedState() {
		wroom.setParked(false);
		wroom.setParallelPark(falseActuator);
		wroom.setMoveCar(trueActuator);
		wroom.setParkingSpace(5);
		wroom.setBestParkSpace(5);
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
		wroom.setMoveCar(falseActuator);
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
		wroom.setPosition(wroom.getPosition() - 1);
		assert (wroom.compareTo(lastState) != 0);
	}

	/********************************************************************************
	 * Structural testing *
	 ********************************************************************************/

	/**
	 * start of moveForward tests
	 */
	@Test
	public void moveForward_workingSensors_CarState() {
		wroom.setPosition(wroom.getStreetLength() - 1);
		wroom.setMoveCar(workingActuator);
		wroom.setParked(false);
		wroom.setUltrasound1(sensor200);

		wroom.setUltrasound2(sensor200);
		assert (wroom.moveForward().getContent() != null);
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
		assert (wroom.moveBackward().getContent() != null);
	}

	/**
	 * start of StatusWrapper tests
	 */
	@Test
	public void StatusWrapper_covarage_true() {
		StatusWrapper<Boolean> test = new StatusWrapper<>(true, StatusWrapper.OK);
		StatusWrapper<Boolean> test2 = new StatusWrapper<>(StatusWrapper.OK);
		StatusWrapper<Boolean> test3 = new StatusWrapper<>(StatusWrapper.OK);
		test.setContent(false);
		test2.setStatus(StatusWrapper.NO_INIT);
		test3.setMessage("testing");
		assert (!test.getContent());
		assert (test2.getStatus().equals(StatusWrapper.NO_INIT));
		assert (test3.getMessage() == "testing");
		assert (test.toString() instanceof String);
	}

	/**
	 * start of CarState tests
	 */
	@Test
	public void CarState_covarage_true() {
		CarState test = new CarState();
		test.setParked(true);
		assert (test.hashCode() == 1 << 31);
	}

	@Test
	public void CarState_bestSpace_true() {
		CarState test = new CarState();
		test.setPosition(8);
		test.setParkingSpace(5);
		test.setParkingSpace(0);

		assert (test.getBestParkSpace() == 5);
		assert (test.getBestParkPosition() == 8);
	}

	/**
	 * start of Car tests
	 */
	@Test
	public void Car_setters_true() {
		wroom.setStreetLength(1337);
		wroom.setCarWidth(69);
		wroom.setStdThreshold(420);
		assert (wroom.getStreetLength() == 1337);
		assert (wroom.getCarWidth() == 69);
		assert (wroom.getStdThreshold() == 420);
	}

	@Test
	public void park_brokenActuator_unexpectedState2() {
		wroom.setParked(false);
		wroom.setParallelPark(trueActuator);
		wroom.setMoveCar(falseActuator);
		wroom.setParkingSpace(5);
		wroom.setBestParkSpace(5);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	@Test
	public void Park_moveBackwards_notPossible() {
		wroom.setMoveCar(failOnBackwardActuator);
		wroom.setParallelPark(trueActuator);
		wroom.setUltrasound1(workingSensor);
		wroom.setUltrasound2(workingSensor);
		wroom.setParked(false);
		wroom.setPosition(0);
		assert (wroom.park().getStatus().equals(autopark.StatusWrapper.UNEXPECTED_STATE));
	}

	@Test
	public void Park_moveBackwardsBestPosition_true() {
		wroom.setMoveCar(workingActuator);
		wroom.setParallelPark(trueActuator);
		wroom.setUltrasound1(workingSensor);
		wroom.setUltrasound2(workingSensor);
		wroom.setParked(false);
		wroom.setPosition(0);
		assert (wroom.park().getStatus().equals(StatusWrapper.OK));
		assert (wroom.getBestParkPosition() == 30);
		assert (wroom.getBestParkSpace() == 5);
		assert (wroom.isParked());
	}

	@Test
	public void IsEmpty_STDLimit_true() {
		wroom.setUltrasound1(sawToothSensor);
		wroom.setUltrasound2(sawToothSensor);
		StatusWrapper<Boolean> data = wroom.isEmpty();
		assert (data.getMessage() == "No space");
		assert (data.getStatus().equals(StatusWrapper.NOT_POSSIBLE));
	}
}
