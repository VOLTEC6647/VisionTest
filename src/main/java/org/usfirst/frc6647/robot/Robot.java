/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.lib6647.loops.LooperRobot;
import org.usfirst.lib6647.oi.JController;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends LooperRobot {
	private double cameraAngle = 30, cameraHeightMeters = .90, targetHeightMeters = 2.20, distance = 0;

	/** Static {@link Robot} instance. */
	private static Robot instance = null;

	/**
	 * Method for getting currently running {@link Robot} instance.
	 * 
	 * @return The current {@link Robot} instance
	 */
	public synchronized static Robot getInstance() {
		return instance;
	}

	/**
	 * Constructor for this implementation of {@link LooperRobot}, should only need
	 * to be created once, by the {@link Main} class.
	 */
	protected Robot() {
		super();

		if (instance == null) // Might not be necessary, but just in case.
			instance = this;

		initJoysticks();
		// registerSubsystems(Chassis::new, Intake::new, ...);
	}

	/**
	 * Run any {@link JController} initialization here.
	 */
	private void initJoysticks() {
		// Create JController object.
		var driver1 = new JController(0);

		System.out.printf("Found: '%s'!\n", driver1.getName());

		if (driver1.getName().equals("Wireless Controller")) {
			driver1.setXY(Hand.kLeft, 0, 1);
			driver1.setXY(Hand.kRight, 2, 5);
		} else if (driver1.getName().equals("Generic   USB  Joystick")) {
			driver1.setXY(Hand.kLeft, 0, 1);
			driver1.setXY(Hand.kRight, 2, 4);
		} else if (driver1.getName().toLowerCase().contains("xbox")
				|| driver1.getName().equals("Controller (Gamepad F310)")) {
			driver1.setXY(Hand.kLeft, 0, 1);
			driver1.setXY(Hand.kLeft, 4, 5);
		}

		// Register each instantiated JController object in the joysticks HashMap.
		registerJoystick(driver1, "driver1");
	}

	@Override
	public void robotPeriodic() {
		super.robotPeriodic();

		var ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
		SmartDashboard.putNumber("ty",
				NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));

		distance = (-2.86473 * Math.log(ty + 21.0891) + 11.4298);
		SmartDashboard.putNumber("Distance Meters", distance);

		cameraAngle = SmartDashboard.getNumber("cameraAngle", cameraAngle);
		cameraHeightMeters = SmartDashboard.getNumber("cameraHeight", cameraHeightMeters);
		targetHeightMeters = SmartDashboard.getNumber("targetHeight", targetHeightMeters);
	}
}
