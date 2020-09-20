package autopark.copy;

public class CarState implements Comparable<CarState>{
	public int parkingSpace = 0;
	public boolean parked = false;
	public int position = 0;
	
	public CarState() {}
	
	public CarState(CarState that) {
		this.parked = that.parked;
		this.parkingSpace = that.parkingSpace;
		this.position = that.position;
	}
	
	@Override
	public int hashCode() {
		final int p = this.parked ? 1 : 0;
		return (this.position<<15) + (this.parkingSpace<<23) + (p<<31);
	}

	@Override
	public int compareTo(CarState o) {
		return this.hashCode() - o.hashCode();
	}
}
