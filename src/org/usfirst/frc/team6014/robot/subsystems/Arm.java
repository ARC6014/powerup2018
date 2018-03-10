/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6014.robot.subsystems;

import org.usfirst.frc.team6014.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * A subsystem for controlling the arm and its components.
 */
public class Arm extends PIDSubsystem {
	SpeedController leftHinge = RobotMap.leftHingeEnum.generate(RobotMap.leftHinge);
	SpeedController rightHinge = RobotMap.rightHingeEnum.generate(RobotMap.rightHinge);
	SpeedControllerGroup hinge;
	
	SpeedController leftHolder = RobotMap.leftHolderEnum.generate(RobotMap.leftHolder);
	SpeedController rightHolder = RobotMap.rightHolderEnum.generate(RobotMap.rightHolder);	
	SpeedControllerGroup holder;
	
	Encoder hingeEncoder = new Encoder(RobotMap.armEncoderA, RobotMap.armEncoderB, false, Encoder.EncodingType.k1X);
	
	final double countsPerRevolution = 546;
	double angularRange = 165;
	double rampMax = 8;
	private double outPID = 0;
	
	public Arm() {
		super(0.12,0,0.02,0.08);
		hingeEncoder.reset();
		rightHinge.setInverted(true);
		rightHolder.setInverted(true);
		hinge = new SpeedControllerGroup(leftHinge,rightHinge);
		holder = new SpeedControllerGroup(leftHolder,rightHolder);
		setAbsoluteTolerance(3);
		getPIDController().setContinuous(false);
	}
	
	@Override
	public void initDefaultCommand() {
	}
	
	@Override
	protected double returnPIDInput() {
		return getCurrentAngle();
	}
	@Override
	protected void usePIDOutput(double output) {
		this.outPID = output;
	}
	public void setAngle(double angle) {
		this.setSetpoint(angle);
		if(angle < getCurrentAngle()) {
			this.getPIDController().setPID(0.14,0,0.15,0.12);
		}
		if(angle > getCurrentAngle()) {
			this.getPIDController().setPID(0.14,0,0.15,0.12);
		}
	}
	public void setRampedAngle(double angle) {
		if(Math.abs(angle-this.getCurrentAngle())>rampMax) {
			this.setSetpoint(this.getCurrentAngle()+Math.signum(angle-this.getCurrentAngle())*rampMax);
		} else {
			this.setSetpoint(angle);
		}
	}
	public void hingePID() {
		
		this.setHingeSpeed(outPID);
	}
	
	public void setHolderSpeed(double speed) {
		holder.set(speed);
	}
	public void setHingeSpeed(double speed) {
		if(overRotated(speed>0)){
			hinge.set(0);
		} else {
			hinge.set(speed);
		}
	}
	
	public void resetHingeEncoder() {
		hingeEncoder.reset();
	}
	
	public double getCurrentAngle() {
		int count = hingeEncoder.get();
		return count*360/countsPerRevolution;
	}
	
	public boolean overRotated(boolean dir){
		return false;
		/*double angle = getCurrentAngle();
		if((angle<=0 && !dir) || (angle>=angularRange && dir)){
			return true;
		}
		return false;*/
	}
}