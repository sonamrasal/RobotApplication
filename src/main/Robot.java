package main;

import main.robotExceptions.LowBatteryException;
import main.robotExceptions.OverWeightException;

public class Robot {

	private static final double MAX_PERMISSIBLE_WEIGHT = 10.0;
	
	private Battery battery;

	public Robot(double charge) {
		this.battery = new Battery(charge);
	}

	public Robot walk(double distance) throws LowBatteryException {
		battery.consumeChargeForWalking(distance);
		if (battery.thresholdCrossed()) {
			throw new LowBatteryException("Less than 15% battery remaining");
		}
		return this;
	}

	public Robot carry(double weight) throws OverWeightException {
		if (weight > MAX_PERMISSIBLE_WEIGHT) {
			throw new OverWeightException("Maximum permissible weight limit exceeded");
		}
		battery.consumeChargeForCarrying(weight);
		return this;
	}

	public String charge() {
		return battery.toString();
	}

}
