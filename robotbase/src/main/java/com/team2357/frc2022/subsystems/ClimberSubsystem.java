package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* * 
 * 
 * @Category climber
 * Climber Subsystem
 * Dynamic arms
 * Static hook latches
 * Climbing winch motors
 */
public class ClimberSubsystem extends ClosedLoopSubsystem {
    private static ClimberSubsystem instance = null;

    public static ClimberSubsystem getInstance() {
        return instance;
    }

    public static class Configuration {
        public double m_climberAxisMaxSpeed = 0;

        public IdleMode m_climberMotorIdleMode = IdleMode.kBrake;

        public int m_climberMotorStallLimitAmps = 0;
        public int m_climberMotorFreeLimitAmps = 0;

        public boolean m_isRightSideInverted = false;

        public int m_climberGrippedAmps = 0;

        // smart motion config

        // Both
        public double m_climberMotorMaxOutput = 0;
        public double m_climberMotorMinOutput = 0;
        public double m_climberMotorAllowedError = 0;

        // Unloaded
        public double m_climberUnloadedMotorP = 0;
        public double m_climberUnloadedMotorI = 0;
        public double m_climberUnloadedMotorD = 0;
        public double m_climberUnloadedMotorIZone = 0;
        public double m_climberUnloadedMotorFF = 0;
        public double m_climberUnloadedMotorMaxRPM = 0;
        public double m_climberUnloadedMotorMaxVel = 0;
        public double m_climberUnloadedMotorMinVel = 0;
        public double m_climberUnloadedMotorMaxAcc = 0;

        // Loaded
        public double m_climberLoadedMotorP = 0;
        public double m_climberLoadedMotorI = 0;
        public double m_climberLoadedMotorD = 0;
        public double m_climberLoadedMotorIZone = 0;
        public double m_climberLoadedMotorFF = 0;
        public double m_climberLoadedMotorMaxRPM = 0;
        public double m_climberLoadedMotorMaxVel = 0;
        public double m_climberLoadedMotorMinVel = 0;
        public double m_climberLoadedMotorMaxAcc = 0;


    }

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;
    private Solenoid m_hookSolenoid;
    private SparkMaxPIDController m_leftPidController;
    private SparkMaxPIDController m_rightPidController;

    private double m_targetRotations;

    public boolean m_isLeftAtTarget;
    public boolean m_isRightAtTarget;

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid,
            Solenoid hookSolenoid) {
        instance = this;
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_hookSolenoid = hookSolenoid;
        m_climberSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void configure(Configuration config) {
        m_config = config;

        m_leftClimberMotor.restoreFactoryDefaults();
        m_rightClimberMotor.restoreFactoryDefaults();

        configureClimberMotor(m_leftClimberMotor);
        configureClimberMotor(m_rightClimberMotor);

        m_leftPidController = m_leftClimberMotor.getPIDController();

        m_rightPidController = m_rightClimberMotor.getPIDController();

        switchToUnloadedClimberPID();

        m_leftClimberMotor.setInverted(!m_config.m_isRightSideInverted);
        m_rightClimberMotor.setInverted(m_config.m_isRightSideInverted);
    }

    private void configureClimberMotor(CANSparkMax motor) {
        motor.setIdleMode(m_config.m_climberMotorIdleMode);
        motor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);
    }

    public void switchToUnloadedClimberPID() {
        configureUnloadedClimberPID(m_leftPidController);
        configureUnloadedClimberPID(m_rightPidController);
    }

    public void switchToLoadedClimberPID() {
        configureLoadedClimberPID(m_leftPidController);
        configureLoadedClimberPID(m_rightPidController);
    }


    private void configureUnloadedClimberPID(SparkMaxPIDController pidController) {
        // set PID coefficients
        pidController.setP(m_config.m_climberUnloadedMotorP);
        pidController.setI(m_config.m_climberUnloadedMotorI);
        pidController.setD(m_config.m_climberUnloadedMotorD);
        pidController.setIZone(m_config.m_climberUnloadedMotorIZone);
        pidController.setFF(m_config.m_climberUnloadedMotorFF);
        pidController.setOutputRange(m_config.m_climberMotorMinOutput, m_config.m_climberMotorMaxOutput);

        // Configure smart motion
        int smartMotionSlot = 0;
        pidController.setSmartMotionMaxVelocity(m_config.m_climberUnloadedMotorMaxVel, smartMotionSlot);
        pidController.setSmartMotionMinOutputVelocity(m_config.m_climberUnloadedMotorMinVel, smartMotionSlot);
        pidController.setSmartMotionMaxAccel(m_config.m_climberUnloadedMotorMaxAcc, smartMotionSlot);
        pidController.setSmartMotionAllowedClosedLoopError(m_config.m_climberMotorAllowedError, smartMotionSlot);
    }

    private void configureLoadedClimberPID(SparkMaxPIDController pidController) {
        pidController.setP(m_config.m_climberLoadedMotorP);
        pidController.setI(m_config.m_climberLoadedMotorI);
        pidController.setD(m_config.m_climberLoadedMotorD);
        pidController.setIZone(m_config.m_climberLoadedMotorIZone);
        pidController.setFF(m_config.m_climberLoadedMotorFF);
        pidController.setOutputRange(m_config.m_climberMotorMinOutput, m_config.m_climberMotorMaxOutput);

        // Configure smart motion
        int smartMotionSlot = 0;
        pidController.setSmartMotionMaxVelocity(m_config.m_climberLoadedMotorMaxVel, smartMotionSlot);
        pidController.setSmartMotionMinOutputVelocity(m_config.m_climberLoadedMotorMinVel, smartMotionSlot);
        pidController.setSmartMotionMaxAccel(m_config.m_climberLoadedMotorMaxAcc, smartMotionSlot);
        pidController.setSmartMotionAllowedClosedLoopError(m_config.m_climberMotorAllowedError, smartMotionSlot);
    }

    /**
     * 
     * @param rotations Setpoint in rotations for the climber motor to go to
     * @return
     */
    public void setClimberRotations(double rotations) {
        setClosedLoopEnabled(true);
        m_targetRotations = rotations;
         m_leftPidController.setReference(m_targetRotations,
         CANSparkMax.ControlType.kSmartMotion);
        m_rightPidController.setReference(m_targetRotations, CANSparkMax.ControlType.kSmartMotion);

        m_isLeftAtTarget = false;
        m_isRightAtTarget = false;
    }

    /**
     * 
     * @return Is the climber at the setpoint set by {@Link setClimberRotations}
     */
    public boolean isClimberAtRotations() {
        return m_isRightAtTarget && m_isLeftAtTarget;
    }

    /**
     * 
     * @return If both motors are stopped at setpoint
     */
    public boolean handleClimberRotations() {
        if(isLeftClimberAtRotations()) {
            m_leftClimberMotor.set(0);
            m_isLeftAtTarget = true;
        }

        if(isRightClimberAtRotations()) {
            m_rightClimberMotor.set(0);
            m_isRightAtTarget = true;
        }

        return m_isRightAtTarget && m_isLeftAtTarget;
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
        return (m_leftClimberMotor.getOutputCurrent() > m_config.m_climberGrippedAmps)
                && (m_rightClimberMotor.getOutputCurrent() > m_config.m_climberGrippedAmps);
    }

    // Method to set climber speed from a joystick
    public void setClimberAxisSpeed(double axisSpeed) {
        setClosedLoopEnabled(false);

        double motorSpeed = (-axisSpeed) * m_config.m_climberAxisMaxSpeed;

        m_leftClimberMotor.set(motorSpeed);
        m_rightClimberMotor.set(motorSpeed);
    }

    // Method to stop the motors
    public void stopClimberMotors() {
        setClosedLoopEnabled(false);
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

    public void setLatch(boolean value) {
        m_hookSolenoid.set(value);
    }

    public boolean isHookOpen() {
        return m_hookSolenoid.get();
    }

    @Override
    public void periodic() {
        if (isClosedLoopEnabled() ) {
            if(isClimberAtRotations()) {
            setClosedLoopEnabled(false);
            }
        }

        SmartDashboard.putNumber("left rotations", m_leftClimberMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("right rotations", m_rightClimberMotor.getEncoder().getPosition());
        //System.out.println("Speed: " + m_rightClimberMotor.getEncoder().getVelocity());
    }
}
