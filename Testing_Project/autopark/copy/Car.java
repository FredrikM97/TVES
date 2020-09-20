package autopark.copy;

import java.util.ArrayList;
import java.util.Optional;

public class Car extends ACar {

	@Override
	public StatusWrapper<CarState> moveForward() {
		// Pre-conditions
		if (parked) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(position < streetLength)) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "End of streetLength");
		}
		if (!(moveCar instanceof Actuator)) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NO_INIT, "No working actuator");
		}
		// Action
		if(!moveCar.activate()) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		position++;
		parkingSpace = isEmpty().getContent() ? parkingSpace + 1 : 0;
		
		
		return new StatusWrapper<>(whereIs());
	}

	@Override
	public StatusWrapper<Boolean> isEmpty() {
		// Check pre conditions
		if (!(ultrasound1 instanceof Sensor)) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT,
					"ultrasound1 is not an instance of Sensor");
		}
		if (!(ultrasound2 instanceof Sensor)) {
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
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "No working sensors");
		}

		// Get values
		double sensorRead = 0.0;
		sensorRead = read1.isPresent() ? sensorRead + read1.get() : sensorRead;
		sensorRead = read2.isPresent() ? sensorRead + read2.get() : sensorRead;

		// Get mean value (sensors are assumed to be reading the same thing)
		sensorRead = sensorRead / workingSensors;
		if (sensorRead >= carWidth) {
			return new StatusWrapper<>(true);
		} else {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "No space");
		}
	}

	@Override
	public StatusWrapper<CarState> moveBackward() {
		// Pre-conditions
		if (parked) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(0 < position)) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NOT_POSSIBLE, "Out of streetLength");
		}
		if (!(moveCar instanceof autopark.Actuator)) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		if(!moveCar.activate()) {
			return new StatusWrapper<>(whereIs(), autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		position--;
		parkingSpace = isEmpty().getContent() ? parkingSpace - 1 : 0;
		
		return new StatusWrapper<>(whereIs());
	}

	@Override
	public StatusWrapper<Boolean> park() {
		// Pre-conditions
		if (parked) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(parallelPark instanceof autopark.Actuator)) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT, "No working actuator");
		}
		if (!(5 <= parkingSpace)) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Not enough parking spaces");
		}
		while (position < streetLength && parkingSpace < 5) {
			moveForward();
		}

		// Action
		if (parkingSpace >= 5) {
			for (int i = 0; i < 5; i++) {
				moveBackward();
			}
			if (!parallelPark.activate()) {
				return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
			}
			parked = true;

		}

		// Post condition
		if (parked) {
			return new StatusWrapper<>(true);
		}
		return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "Car is not parked");

	}

	@Override
	public StatusWrapper<Boolean> unPark() {
		// Pre-conditions
		if (!parked) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NOT_POSSIBLE, "Car is parked");
		}
		if (!(moveCar instanceof autopark.Actuator)) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.NO_INIT, "No working actuator");
		}

		// Action
		if (!moveCar.activate()) {
			return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "No working actuator");
		}
		parked = false;

		// Post condition
		if (!parked) {
			return new StatusWrapper<>(true);
		}
		return new StatusWrapper<>(false, autopark.StatusWrapper.UNEXPECTED_STATE, "Car still parked");
	}

	@Override
	public CarState whereIs() {
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
