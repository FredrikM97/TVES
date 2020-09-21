package autopark;

import java.util.ArrayList;
import java.util.Optional;

public class Car extends ACar {

	@Override
	public StatusWrapper<CarState> moveForward() {
		// Pre-conditions
		if (parked) {
			// To satisfy test: moveForward_parked_notPosible
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(position < streetLength)) {
			// To satisfy test: moveForward_noSpace_notPosible
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "End of streetLength");
		}
		if (!(moveCar instanceof Actuator)) {
			// To satisfy test: moveForward_noInit_noInit
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		if (!moveCar.activate()) {
			// To satisfy test: moveForward_brokenActuator_unexpectedState
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		position++;
		// To satisfy test: moveForward_workingSensors_CarState
		parkingSpace = isEmpty().getContent() ? parkingSpace + 1 : 0;

		// To satisfy test: moveForward_noErrors_CarState
		return new StatusWrapper<>(whereIs());
	}

	@Override
	public StatusWrapper<Boolean> isEmpty() {
		// Check pre conditions
		if (!(ultrasound1 instanceof Sensor)) {
			// To satisfy test: isEmpty_noInit_noInit
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT,
					"ultrasound1 is not an instance of Sensor");
		}
		if (!(ultrasound2 instanceof Sensor)) {
			// To satisfy test: isEmpty_sensor2noInit_noInit
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT,
					"ultrasound2 is not an instance of Sensor");
		}

		// Read values
		Optional<Double> read1 = readAndFilter(ultrasound1);
		Optional<Double> read2 = readAndFilter(ultrasound2);

		// Count functional sensors
		int workingSensors = 0;
		workingSensors = read1.isPresent() ? workingSensors + 1 : workingSensors;
		workingSensors = read2.isPresent() ? workingSensors + 1 : workingSensors;
		if (workingSensors == 0) {
			// To satisfy test: isEmpty_stdSensors_notPossible
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "No working sensors");
		}

		// Get values
		double sensorRead = 0.0;
		// To satisfy test: isEmpty_Sensors1isStd_true
		sensorRead = read1.isPresent() ? sensorRead + read1.get() : sensorRead;
		// To satisfy test: isEmpty_Sensors2isStd_true
		sensorRead = read2.isPresent() ? sensorRead + read2.get() : sensorRead;

		// Get mean value (sensors are assumed to be reading the same thing)
		sensorRead = sensorRead / workingSensors;
		if (sensorRead >= carWidth) {
			// To satisfy test: isEmpty_noErrors_true
			return new StatusWrapper<>(true);
		} else {
			// To satisfy test: isEmpty_notEmpty_false
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "No space");
		}
	}

	@Override
	public StatusWrapper<CarState> moveBackward() {
		// Pre-conditions
		if (parked) {
			// To satisfy test: moveBackward_parked_notPossible
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(0 < position)) {
			// To satisfy test: moveBackward_noSpace_notPossible
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Out of streetLength");
		}
		if (!(moveCar instanceof autopark.Actuator)) {
			// To satisfy test: moveBackward_noInit_noInit
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		if (!moveCar.activate()) {
			// To satisfy test: moveBackward_brokenActuator_unexpectedState
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		position--;
		// To satisfy test: moveBackward_workingSensor_CarState
		parkingSpace = isEmpty().getContent() ? parkingSpace - 1 : 0;

		// To satisfy test: moveBackward_noError_CarState
		return new StatusWrapper<>(whereIs());
	}

	@Override
	public StatusWrapper<Boolean> park() {
		// Pre-conditions
		if (parked) {
			// To satisfy test: park_parked_notPossible
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(parallelPark instanceof autopark.Actuator)) {
			// To satisfy test: park_noInit_noInit
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		while (position < streetLength - 5 && parkingSpace < 5) {
			if (!(moveForward().getStatus().equals(StatusWrapper.OK))) {
				break;
			}
			;
		}
		if (parkingSpace >= 5) {
			for (int i = 0; i < 5; i++) {
				moveForward();
			}
			if (!parallelPark.activate()) {
				// To satisfy test: park_brokenActuator_unexpectedState
				return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
			}
			parked = true;
			// To satisfy test: park_noError_true
			return new StatusWrapper<>(true);
		}

		// Post condition
		// To satisfy test: park_noSpace_notPossible
		return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Could not park");
	}

	@Override
	public StatusWrapper<Boolean> unPark() {
		// Pre-conditions
		if (!parked) {
			// To satisfy test: unPark_notParked_true
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(moveCar instanceof autopark.Actuator)) {
			// To satisfy test: unPark_noInit_noInit
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		if (!moveCar.activate()) {
			// To satisfy test: unPark_actuatorFail_unexpectedState
			return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		parked = false;
		// To satisfy test: unPark_noError_true
		return new StatusWrapper<>(true);
	}

	@Override
	public CarState whereIs() {
		// To satisfy test: whereIs_anyState_CarState
		return new CarState(this);
	}

	private Optional<Double> readAndFilter(Sensor sens) {
		// Collect readings
		ArrayList<Integer> reads = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			reads.add(sens.read());
		}

		// Get standard deviation to see stability. If not stable then disregard all
		// readings
		final double mean = reads.stream().reduce(0, Integer::sum) / (double) reads.size();
		final double std = Math
				.sqrt(reads.stream().map(read -> (read - mean) * (read - mean)).reduce(0.0, Double::sum));
		if (std > stdThreshold) {
			return Optional.empty();
		}

		// Filter outliers and return value
		final double filteredMean = reads.stream().filter(read -> mean - 2 * std <= read && read <= mean + 2 * std)
				.reduce(0, Integer::sum) / (double) reads.size();
		return Optional.of(filteredMean);
	}

}
