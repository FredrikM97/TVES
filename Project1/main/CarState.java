package main;

public class CarState {
	public int position = 0;
	public boolean parked = false;
	public int parkingSpace = 0;
	
	public CarState() {}
	
	public CarState(CarState that) {
		this.position = that.position;
		this.parked = that.parked;
		this.parkingSpace = that.parkingSpace;
	}
	
}
