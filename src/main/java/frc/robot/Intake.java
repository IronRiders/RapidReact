package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class Intake {
    public TalonSRX intakeMotor;
    public TalonSRX deploymentMotor;

    public Intake() {
        TalonSRXConfiguration intakeConfig = new TalonSRXConfiguration();
        intakeMotor = new TalonSRX(Constants.INTAKE_BELT_PORT);
        intakeMotor.configAllSettings(intakeConfig);
        intakeMotor.setNeutralMode(NeutralMode.Coast);
        TalonSRXConfiguration deploymentConfig = new TalonSRXConfiguration();
        deploymentConfig.forwardSoftLimitEnable = true;
        deploymentConfig.forwardSoftLimitThreshold = Constants.DEPLOY_FORWARD_LIMIT;
        deploymentConfig.reverseSoftLimitEnable = true;
        deploymentConfig.reverseSoftLimitThreshold = Constants.DEPLOY_REVERSE_LIMIT;
        deploymentConfig.peakCurrentLimit = Constants.DEPLOY_CURRENT_PEAK_LIMIT;
        deploymentConfig.peakCurrentDuration = Constants.DEPLOY_CURRENT_PEAK_TIME;
        deploymentConfig.continuousCurrentLimit = Constants.DEPLOY_CURRENT_CONT_LIMIT;
        deploymentMotor = new TalonSRX(Constants.INTAKE_DEPLOYMENT_PORT);
        deploymentMotor.configAllSettings(deploymentConfig);
        deploymentMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        deploymentMotor.setSensorPhase(false);
    }

    public void startDeployment() {
        deploymentMotor.set(ControlMode.PercentOutput, Constants.DEPLOY_SPEED);
        deploymentMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void finishDeployment() {
        deploymentMotor.set(ControlMode.PercentOutput, 0.0);
        deploymentMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void intakeBall() {
        intakeMotor.set(TalonSRXControlMode.PercentOutput, Constants.INTAKE_SPEED);
    }

    public void spitOutBall() {
        intakeMotor.set(TalonSRXControlMode.PercentOutput, -Constants.INTAKE_SPEED);
    }

    public void stop() {
        intakeMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }
}
