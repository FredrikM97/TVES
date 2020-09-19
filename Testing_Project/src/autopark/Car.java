package autopark;

import java.util.ArrayList;
import java.util.Optional;

public class Car extends ACar {

	@Override
	public StatusWrapper<CarState> moveForward() {
		// TODO Auto-generated method stub
		return new StatusWrapper<>(this);
	}

	@Override
	public StatusWrapper<Boolean> isEmpty() {
		// Check post conditions
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
		// TODO Auto-generated method stub
		return new StatusWrapper<>(this);
	}

	@Override
	public StatusWrapper<Boolean> park() {
		// TODO Auto-generated method stub
		return new StatusWrapper<>(false);
	}

	@Override
	public StatusWrapper<Boolean> unPark() {
		// TODO Auto-generated method stub
		return new StatusWrapper<>(false);
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

		// Get standard deviation to see stability. If not stable then disregard all readings
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
