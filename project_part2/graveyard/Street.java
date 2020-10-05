package autopark;

import java.util.ArrayList;
import java.util.Collections;

public class Street {
	private ArrayList<Boolean> street;

	public Street() {
		this.generateParkingLots(500, new int[] { 4, 5, 7, 10 });
	}

	public Street(int lengthOfStreet, int[] freeParkingLotsLength) {
		this.generateParkingLots(lengthOfStreet, freeParkingLotsLength);
	}

	public void generateParkingLots(int lengthOfStreet, int[] freeParkingLotsLength) {
		street = new ArrayList<Boolean>();
		for (int i = 0; i < lengthOfStreet; i++)
			street.add(i % 5 == 0);
		Collections.shuffle(street);

		int position;
		for (int i = 0; i < freeParkingLotsLength.length; i++) {
			position = (int) (Math.random() * lengthOfStreet);
			for (int j = 0; j < freeParkingLotsLength[i]; j++)
				street.add(position + j, true);
		}	
	}

	public ArrayList<Boolean> getStreet() {
		return street;
	}

	public void setStreet(ArrayList<Boolean> street) {
		this.street = street;
	}
}
