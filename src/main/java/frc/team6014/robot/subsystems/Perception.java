/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team6014.robot.subsystems;

import frc.team6014.robot.Robot;
import frc.team6014.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;

/**
 * A subsystem for sensors and vision processing.
 */
public class Perception extends Subsystem {
	
	private final int leftEncoderCPR = 2048;
	private final int rightEncoderCPR = 2048;
	private final double wheelDiam = 0.1524;//In meters, actually 6 inches
	
	/*final AnalogInput ultra1 = new AnalogInput(RobotMap.ultra1);
	final AnalogInput ultra2 = new AnalogInput(RobotMap.ultra2);*/
	//final Encoder rightEncoder = new Encoder(RobotMap.rightEncoderA, RobotMap.rightEncoderB, true, Encoder.EncodingType.k4X);
	//final Encoder leftEncoder = new Encoder(RobotMap.leftEncoderA, RobotMap.leftEncoderB, true, Encoder.EncodingType.k4X);
	
	public Perception() {
		
	}
	
	@Override
	public void initDefaultCommand() {
	}

	/*public double getUltraDistance(int num) {
		double voltage;
		if(num==1) {
			voltage = ultra1.getVoltage();
		} else if(num==2) {
			voltage = ultra2.getVoltage();
		} else {
			voltage=0;
			System.out.println("Invalid ultrasonic sensor ID");
		}
        double mms = (voltage / 5.0) * 5000.0;
        return mms / 10.0;
    }*/
	public double getLeftEncoderRev() {
		return Robot.drive.getLeftEncoderPos()/(double)(leftEncoderCPR*4);
	}
	public double getRightEncoderRev() {
		return Robot.drive.getRightEncoderPos()/(double)(rightEncoderCPR*4);
	}
	public double getLeftDistance() {
		return getLeftEncoderRev()*wheelDiam*Math.PI;
	}
	public double getRightDistance() {
		return getRightEncoderRev()*wheelDiam*Math.PI;
	}
}
