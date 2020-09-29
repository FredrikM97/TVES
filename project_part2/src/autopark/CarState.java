package autopark;

public class CarState implements Comparable<CarState> {
	private int parkingSpace = 0;
	private boolean parked = false;
	private int position = 0;

	public CarState() {
	}

	public CarState(CarState that) {
		this.parked = that.parked;
		this.parkingSpace = that.parkingSpace;
		this.position = that.position;
	}

	public int getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(int parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public boolean isParked() {
		return parked;
	}

	public void setParked(boolean parked) {
		this.parked = parked;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int p = this.parked ? 1 : 0;
		return (this.position << 15) + (this.parkingSpace << 23) + (p << 31);
	}

	@Override
	public int compareTo(CarState o) {
		return this.hashCode() - o.hashCode();
	}
}
