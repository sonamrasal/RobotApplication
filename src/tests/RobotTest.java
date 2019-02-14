package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import main.Robot;
import main.robotExceptions.LowBatteryException;
import main.robotExceptions.OverWeightException;

import org.junit.Before;
import org.junit.Test;

public class RobotTest {

	private static final double FULL_CHARGE = 100.0F;
	private Robot robot;

	@Before
	public void setUp() {
		robot = new Robot(FULL_CHARGE);
	}

	@Test
	public void fullyChargedRobotCanWalk5KMBeforeDischarge() throws LowBatteryException {
		assertEquals(0.0, robot.walk(5).charge(), 0.0);
	}

	@Test(expected = LowBatteryException.class)
	public void fullyChargedRobotWalking4Point3KMleavesLessThan15PercentBattery() throws LowBatteryException {
		robot.walk(4.3).charge();
		fail("Exception expected");
	}
	
	@Test
	public void fullyChargedRobotCannotWalkComplete6KM() {
		try {
			robot.walk(6).charge();
			fail("Exception expected");
		} catch (LowBatteryException lbe) {
			assertEquals(0.0, robot.charge(), 0.0);
		}
	}
	
	@Test
	public void fullyChargedRobotWalkingFor3Point5KMConsumes70PercentBattery() {
		try {
			assertEquals(30.0, robot.walk(3.5).charge(), 0.0);
		} catch (LowBatteryException e) {
			fail("30% percent battery still remaining after walking 3.5KM - greater than low battery threshold of 15%");
		}
	}

	@Test
	public void fullyChargedRobotWalksFor4Point5KMConsumes90PercentBatteryAndCrossesLowBatteryThreshold() {
		try {
			robot.walk(4.5);
			fail("Low battery threshold crossed - remaining battery is 10%");
		} catch (LowBatteryException lbe) {
			assertEquals(10.0, robot.charge(), 0.0);
		}
	}

	@Test(expected = OverWeightException.class)
	public void robotCanCarryMax10KGWeight() throws OverWeightException {
		robot.carry(12).charge();
		fail("Exception expected");
	}
	
	@Test
	public void fullyChargedRobotCarrying9KGWeightConsumes18PercentBattery() {
		try {
			assertEquals(82.0, robot.carry(9).charge(), 0.0);
		} catch (OverWeightException e) {
			fail("Assigned weight of 9KG is within permissible limit of 10KG");
		}
	}
	
	@Test
	public void robotWalkingFor2KMCarrying3KGWeightConsumes46PercentBattery() {
		try {
			assertEquals(54.0, robot.walk(2).carry(3).charge(), 0.0);
		} catch (LowBatteryException lbe) {
			fail("Robot consumes 46% battery - 54% still remaining");
		} catch(OverWeightException owe) {
			fail("Assigned weight 0f 3KG is within permissible limit of 10KG");
		}
	}
}
