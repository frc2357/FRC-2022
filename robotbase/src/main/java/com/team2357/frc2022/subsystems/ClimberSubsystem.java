package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

/* * 
 * 
 * @Category climber
 */

public class ClimberSubsystem extends ClosedLoopSubsystem {

    public static class Configuration {
        public IdleMode m_climberMotorIdleMode = IdleMode.kBrake;

        public int m_climberMotorStallLimitAmps = 0;
        public int m_climberMotorFreeLimitAmps = 0;

        public boolean m_isRightSideInverted = false;

        public int m_climberGrippedAmps = 0;

        // smart motion config
        public double m_climberMotorP = 0;
        public double m_climberMotorI = 0;
        public double m_climberMotorD = 0;
        public double m_climberMotorIZone = 0;
        public double m_climberMotorFF = 0;
        public double m_climberMotorMaxOutput = 0;
        public double m_climberMotorMinOutput = 0;
        public double m_climberMotorMaxRPM = 0;
        public double m_climberMotorMaxVel = 0;
        public double m_climberMotorMinVel = 0;
        public double m_climberMotorMaxAcc = 0;
        public double m_climberMotorAllowedError = 0;
    }

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;
    private Solenoid m_hookSolenoid;
    private SparkMaxPIDController m_leftPidController;
    private SparkMaxPIDController m_rightPidController;
    
    private double m_targetRotations;

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid,
            Solenoid hookSolenoid) {
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_hookSolenoid = hookSolenoid;
        m_climberSolenoid.set(DoubleSolenoid.Value.kOff);
        m_hookSolenoid.set(false);
    }

    public void configure(Configuration config) {
        m_config = config;

        configureClimberMotor(m_leftClimberMotor);
        configureClimberMotor(m_rightClimberMotor);

        m_leftPidController = m_leftClimberMotor.getPIDController();
        configureClimberPID(m_leftPidController);

        m_rightPidController = m_rightClimberMotor.getPIDController();
        configureClimberPID(m_rightPidController);

        m_leftClimberMotor.setInverted(!m_config.m_isRightSideInverted);
        m_rightClimberMotor.setInverted(m_config.m_isRightSideInverted);
    }

    private void configureClimberMotor(CANSparkMax motor) {
        motor.setIdleMode(m_config.m_climberMotorIdleMode);
        motor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);
    }

    private void configureClimberPID(SparkMaxPIDController pidController) {
        // set PID coefficients
        pidController.setP(m_config.m_climberMotorP);
        pidController.setI(m_config.m_climberMotorI);
        pidController.setD(m_config.m_climberMotorD);
        pidController.setIZone(m_config.m_climberMotorIZone);
        pidController.setFF(m_config.m_climberMotorFF);
        pidController.setOutputRange(m_config.m_climberMotorMinOutput, m_config.m_climberMotorMinOutput);

        // Configure smart motion
        int smartMotionSlot = 0;
        pidController.setSmartMotionMaxVelocity(m_config.m_climberMotorMaxVel, smartMotionSlot);
        pidController.setSmartMotionMinOutputVelocity(m_config.m_climberMotorMinVel, smartMotionSlot);
        pidController.setSmartMotionMaxAccel(m_config.m_climberMotorMaxAcc, smartMotionSlot);
        pidController.setSmartMotionAllowedClosedLoopError(m_config.m_climberMotorAllowedError, smartMotionSlot);
    }

    /**
     * 
     * @param rotations Setpoint in rotations for the climber motor to go to
     * @return
     */
    public void setClimberRotations(double rotations) {
        m_targetRotations = rotations;
        m_leftPidController.setReference(m_targetRotations, CANSparkMax.ControlType.kSmartMotion);
        m_rightPidController.setReference(m_targetRotations, CANSparkMax.ControlType.kSmartMotion);
    }

    /**
     * 
     * @return Is the climber at the setpoint set by {@Link setClimberRotations}
     */
    public boolean isClimberAtRotations() {
        
        return isLeftClimberAtRotations() && isRightClimberAtRotations();
    }

    /**
     * 
     * @return Is the climber at the setpoint set by {@Link setClimberRotations}
     */
    public boolean isLeftClimberAtRotations() {
        double currentMotorRotations = m_leftClimberMotor.getEncoder().getPosition();
        return Utility.isWithinTolerance(currentMotorRotations, m_targetRotations,
                m_config.m_climberMotorAllowedError);
    }

    /**
     * 
     * @return Is the climber at the setpoint set by {@Link setClimberRotations}
     */
    public boolean isRightClimberAtRotations() {
        double currentMotorRotations = m_rightClimberMotor.getEncoder().getPosition();
        return Utility.isWithinTolerance(currentMotorRotations, m_targetRotations,
                m_config.m_climberMotorAllowedError);
    }

    public boolean isClimberGripped() {
        return (m_leftClimberMotor.getOutputCurrent() > m_config.m_climberGrippedAmps) && (m_rightClimberMotor.getOutputCurrent() > m_config.m_climberGrippedAmps);
    }

    // Method to set climber speed
    public void climberMotorSpeed(double speed) {
        m_leftClimberMotor.set(speed);
        m_rightClimberMotor.set(speed);
    }

    // Method to stop the motors
    public void StopClimberMotors() {
        m_leftClimberMotor.set(0);
        m_rightClimberMotor.set(0);
    }

    public void resetEncoders() {
        m_leftClimberMotor.getEncoder().setPosition(0);
        m_rightClimberMotor.getEncoder().setPosition(0);
    }

    public double getLeftClimberRotations() {
        return m_leftClimberMotor.getEncoder().getPosition();
    }

    public double getRightClimberRotations() {
        return m_rightClimberMotor.getEncoder().getPosition();
    }

    public void setClimberUpright(boolean setUpright) {
        m_climberSolenoid.set(setUpright ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public boolean isClimberUpright() {
        return (m_climberSolenoid.get() == DoubleSolenoid.Value.kForward);
    }

    public void setHookPivot(boolean value) {
        m_hookSolenoid.set(value);
    }

    public boolean isHookOpen() {
        return m_hookSolenoid.get();
    }
}
