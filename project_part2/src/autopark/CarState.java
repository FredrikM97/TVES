package autopark;

public class CarState implements Comparable<CarState> {
	private int parkingSpace = 0;
	private boolean parked = false;
	private int position = 0;
	private int bestParkPosition = 0;
	private int bestParkSpace = 0;

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
		return (this.getPosition() << 15) + (this.getParkingSpace() << 23) + (p << 31);
	}

	@Override
	public int compareTo(CarState o) {
		return this.hashCode() - o.hashCode();
	}

	public void setParkingSpace(int parkingSpace) {
		if(parkingSpace == 0) {
			if(this.getParkingSpace() >= 5)
				if(this.getBestParkSpace() >= this.getParkingSpace() ||  this.getBestParkSpace() == 0) {
					this.setBestParkSpace(this.getParkingSpace());
					this.setBestParkPosition(this.getPosition());
				}
		}
		this.parkingSpace = parkingSpace;
	}

	public int getBestParkPosition() {
		return bestParkPosition;
	}

	public void setBestParkPosition(int bestParkPosition) {
		this.bestParkPosition = bestParkPosition;
	}

	public int getBestParkSpace() {
		return bestParkSpace;
	}

	public void setBestParkSpace(int bestParkSpace) {
		this.bestParkSpace = bestParkSpace;
	}
	
}
