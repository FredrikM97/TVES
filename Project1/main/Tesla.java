package main;

import java.util.ArrayList;

public class Tesla extends ACar {
	private Sensor sensor;
	
	public Tesla() {}

	
	public CarState MoveForward() {
		if (position < 500) {
			position += 1;
			if (isEmpty()) {
				parkingSpace += 1;
			} else {
				parkingSpace = 0;
			}
		}
		return new CarState(this);
	}

	
	public CarState MoveBackward() {
		if (position > 0) {
			position -= 1;
			if (isEmpty() && parkingSpace > 0) {
				parkingSpace -= 1;
			} else {
				parkingSpace = 0;
			}
		}
		return new CarState(this);
	}

	
	public boolean isEmpty() {
		// Query sensors 5 times
		// Filter noise
		// if distance > threshhold and within boundery -> is Empty equals true
		ArrayList<Integer> sensorData = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			sensorData.add(sensor.read());
		}
		double distance = filterNoise(sensorData);
		
		if(distance > 100 && 0 <= distance && distance <= 200) {
			return true;
		}
		return false;
	}

	
	public void Park() {
		if (!parked) {
			while (parkingSpace < 5 && position < 500) {
				MoveForward();
			}
			if(parkingSpace >= 5) {
				System.out.println("Pre-programmed reverse parallel parking maneuve");
			}
		}
	}

	
	public void UnPark() {
		// TODO more needed?
		if(parked) {
			System.out.println("Unpark");
		}
	}

	
	public CarState WhereIs() {
		return new CarState(this);
	}

	
	public double filterNoise(ArrayList<Integer> sensorData) {
		// TODO should probobly be rewritten
		Integer distance = 0;
		  if(!sensorData.isEmpty()) {
		    for (Integer mark : sensorData) {
		    	distance += mark;
		    }
		    return distance.doubleValue() / sensorData.size();
		  }
		  return distance;	  
	}
}