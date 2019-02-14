package main;

import main.robotExceptions.LowBatteryException;
import main.robotExceptions.OverWeightException;

public class Robot {

	private static final int BATTERY_CONSUMPTION_PER_KG_WEIGHT = 2;
	private static final int MAX_DISTANCE_WITH_FULL_CHARGE = 5;
	private static final float LOW_BATTERY_THRESHOLD = 15.0F;
	private static final double FULL_CHARGE = 100.0F;
	private static final double MAX_PERMISSIBLE_WEIGHT = 10.0;
	private double charge;

	public Robot(double charge) {
		this.charge = charge;
	}

	public Robot walk(double distance) throws LowBatteryException {
		charge -= getBatteryConsumedForWalking(distance);
		if (thresholdCrossed()) {
			charge = Math.max(0.0, charge);
			throw new LowBatteryException("Less than 15% battery remaining");
		}
		return this;
	}

	public boolean thresholdCrossed() {
		return batteryDischargedCompletely() || remainingBatteryLessThanThreshold();
	}

	private boolean batteryDischargedCompletely() {
		return charge < 0.0;
	}

	public boolean remainingBatteryLessThanThreshold() {
		return charge > 0.0 && charge < LOW_BATTERY_THRESHOLD;
	}

	private double getBatteryConsumedForWalking(double distanceToWalk) {
		return (distanceToWalk * FULL_CHARGE) / MAX_DISTANCE_WITH_FULL_CHARGE;
	}

	public Robot carry(double weight) throws OverWeightException {
		if (weight > MAX_PERMISSIBLE_WEIGHT) {
			throw new OverWeightException("Maximum permissible weight limit exceeded");
		}
		charge -= (weight * BATTERY_CONSUMPTION_PER_KG_WEIGHT);
		return this;
	}

	public double charge() {
		return charge;
	}

}
