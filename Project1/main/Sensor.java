package main;

interface ISensor {
	public Integer read();
}

public class Sensor implements ISensor {

	/**
	 * Uses two sensors to calculate the distance to nearest object. 
	 * 
	 * <br>
	 * <b>Pre-condition</b> distance > 0 || distance < 200
	 * 
	 * Test-cases:
	 * @return 
	 */
	public Integer read() {
		return 100;
	}

}
