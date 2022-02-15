package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private CANSparkMax bottomMotor, topMotor;
    private SparkMaxPIDController topPID, bottomPID;
    private double kP = 0.00005, kI = 0.0, kD = 0.0, kMaxOutput, kMinOutput;
    private double topMotorChange = 1.0; // 1.0 is the same, 2.0 is twice as fast

    public Shooter(int bottomPort, int topPort) {
        bottomMotor = new CANSparkMax(bottomPort, MotorType.kBrushless);
        topMotor = new CANSparkMax(topPort, MotorType.kBrushless);
        bottomMotor.setIdleMode(IdleMode.kCoast);
        topMotor.setIdleMode(IdleMode.kCoast);
        topPID = topMotor.getPIDController();
        bottomPID = bottomMotor.getPIDController();
        topMotor.setInverted(true);
        kMaxOutput = 1;
        kMinOutput = -1;
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("Top Motor Change", topMotorChange);
        topPID.setOutputRange(kMinOutput, kMaxOutput);
        bottomPID.setOutputRange(kMinOutput, kMaxOutput);
        updateNumbers();
    }

    public void stop() {
        topMotor.set(0);
        bottomMotor.set(0);
    }

    public void shoot(double rpm) {
        // double velocity = rpm / (2 * Math.PI * Constants.SHOOTER_WHEEL_RADIUS);
        topPID.setReference(rpm * topMotorChange, ControlType.kVelocity);
        bottomPID.setReference(rpm, ControlType.kVelocity);
    }

    public void updateNumbers() {
        kP = SmartDashboard.getNumber("P Gain", kP);
        kI = SmartDashboard.getNumber("I Gain", kI);
        kD = SmartDashboard.getNumber("D Gain", kD);
        topMotorChange = SmartDashboard.getNumber("Top Motor Change", topMotorChange);
        bottomPID.setP(kP);
        bottomPID.setI(kI);
        bottomPID.setD(kD);
        topPID.setP(kP);
        topPID.setI(kI);
        topPID.setD(kD);
        SmartDashboard.putNumber("speed", bottomMotor.getEncoder().getVelocity());
    }
}