package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.team2357.frc2022.Constants;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

public class ShooterSubsystem extends ClosedLoopSubsystem {
    private WPI_TalonFX m_leftBottomMotor;
    private WPI_TalonFX m_rightBottomMotor;
    private WPI_TalonFX m_topMotor;

    /**
     * 
     * @param leftBottomShooter  The motor on the bottom left
     * @param rightBottomShooter The motor on the bottom right
     * @param topRoller          The motor on the top
     */
    public ShooterSubsystem(WPI_TalonFX leftBottomShooter, WPI_TalonFX rightBottomShooter, WPI_TalonFX topRoller) {
        m_leftBottomMotor = leftBottomShooter;
        m_rightBottomMotor = rightBottomShooter;
        m_topMotor = topRoller;

        // reset motor configs to known state
        m_leftBottomMotor.configFactoryDefault(Constants.TIMEOUT_MS);
        m_rightBottomMotor.configFactoryDefault(Constants.TIMEOUT_MS);

        m_leftBottomMotor.setInverted(true);
        m_rightBottomMotor.follow(m_leftBottomMotor);

        m_leftBottomMotor.configClosedloopRamp(1.0);

        m_leftBottomMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, Constants.TIMEOUT_MS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_leftBottomMotor.setSensorPhase(true);

        // Configure output range
        m_leftBottomMotor.configNominalOutputForward(0, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configPeakOutputForward(Constants.SHOOTER.SHOOTER_MOTOR_PEAK_OUTPUT, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configPeakOutputReverse(0, Constants.TIMEOUT_MS); // don't run the motors in reverse

        m_leftBottomMotor.config_kP(0, Constants.SHOOTER.SHOOTER_P, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kI(0, Constants.SHOOTER.SHOOTER_I, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kD(0, Constants.SHOOTER.SHOOTER_D, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kF(0, Constants.SHOOTER.SHOOTER_F, Constants.TIMEOUT_MS);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute
     */
    public void setClosedLoopRPMBottom(double rpm) {
        double nativeSpeed = rpm * Constants.SHOOTER.FALCON_ENCODER_CPR / Constants.MINUTES_TO_100_MS;
        m_leftBottomMotor.set(ControlMode.Velocity, nativeSpeed);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute
     */
    public void setClosedLoopRPMTop(double rpm) {
        double nativeSpeed = rpm * Constants.SHOOTER.FALCON_ENCODER_CPR / Constants.MINUTES_TO_100_MS;
        m_topMotor.set(ControlMode.Velocity, nativeSpeed);
    }

}
