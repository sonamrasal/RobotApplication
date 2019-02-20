package main;

public class Battery {
	public double charge;
	private static final float LOW_BATTERY_THRESHOLD = 15.0F;
	private static final int BATTERY_CONSUMPTION_PER_KG_WEIGHT = 2;
	private static final double FULL_CHARGE = 100.0F;

	public Battery(double charge) {
		this.charge = charge;
	}

	public boolean thresholdCrossed() {
		return batteryDischargedCompletely()
				|| remainingBatteryLessThanThreshold();
	}

	private boolean batteryDischargedCompletely() {
		return charge == 0.0;
	}

	public boolean remainingBatteryLessThanThreshold() {
		return charge < LOW_BATTERY_THRESHOLD;
	}

	public void consumeChargeForCarrying(double weight) {
		double consumptionForCarryingWeight = getConsumptionForCarryingWeight(weight);
		charge -= consumptionForCarryingWeight;
	}

	private double getConsumptionForCarryingWeight(double weight) {
		return weight * BATTERY_CONSUMPTION_PER_KG_WEIGHT;
	}

	public void consumeChargeForWalking(double distanceToWalk, int maxDistanceWithFullCharge) {
		double consumptionForWalking = getConsumptionForWalking(distanceToWalk, maxDistanceWithFullCharge);
		charge = Math.max(0.0, charge - consumptionForWalking);
	}

	private double getConsumptionForWalking(double distanceToWalk, int maxDistanceWithFullCharge) {
		return (distanceToWalk * FULL_CHARGE) / maxDistanceWithFullCharge;
	}
	
	@Override
	public String toString() {
		return String.valueOf(charge);
	}

}
