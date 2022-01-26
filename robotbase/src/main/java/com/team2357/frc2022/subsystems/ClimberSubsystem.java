package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.util.RobotMath;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Subsystem to control climbing
 * 
 * Designed to rely on two validation checks to increase the progress of the
 * climber's state
 * The state of the climber helps regulate if a certain command along can be
 * ran. The states should only be changed linearly.
 * The validate method should only be used one command per climb
 * sequence
 * the method handleState increments the state, and handles reseting validation
 * set during each sequence.
 * 
 * The climb process for a successful Traversal climb:
 * 
 * Initial State: GROUND
 * Controller Input: Run ClimberExtendMidCommandGroup
 * Run handleState at end of sequence
 * New State: EXTEND_MID_RUNG
 * Controller Input: Run ClimberClimbMidCommandGroup
 * Run handleState at end of sequence
 * New State: ON_MID_RUNG
 * Controller Input: Run ClimberExtendHighCommandGroup
 * Run handleState at end of sequence
 * New State: EXTEND_HIGH_RUNG
 * If ClimberExtendHighCommandGroup successful: run ClimberClimbHighCommandGroup
 * Run handleState at end of sequence
 * New State: ON_HIGH_RUNG
 * Controller Input: Run ClimberExtendTraversalCommandGroup
 * Run handleState at end of sequence
 * New State: EXTEND_TRAVERSAL_RUNG
 * If ClimberExtendTraversalCommandGroup successful: run
 * ClimberClimbTraversalCommandGroup
 * Run handleState at end of sequence
 * New State: ON_TRAVERSAL_RUNG
 * 
 * 
 * @category climber
 */

public class ClimberSubsystem extends ClosedLoopSubsystem {

    public static class Configuration {
        public IdleMode m_climberMotorIdleMode = IdleMode.kBrake;

        public int m_climberMotorStallLimitAmps = 0;
        public int m_climberMotorFreeLimitAmps = 0;
        public boolean m_isRightSideInverted = false;
    }

    public enum State {
        GROUND,
        EXTEND_LOW_RUNG,
        ON_LOW_RUNG,
        EXTEND_MID_RUNG,
        ON_MID_RUNG,
        EXTEND_HIGH_RUNG,
        ON_HIGH_RUNG,
        EXTEND_TRAVERSAL_RUNG,
        TRAVERSAL_RUNG
    }

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;
    private boolean m_isLeftClimberMotorValid;
    private boolean m_isRightClimberMotorValid;
    private State m_currentState;

    // Curve of the diameter of the climber spool {rotations, diameter in meters}
    // Currently plan on rotations being encoder rotations
    private double m_spoolCurveMeters[][] = {
            { 0, 0.019 }, // No line on spool
            { 1, 0.0381 } }; // Max line on spool

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid) {
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_climberSolenoid.set(DoubleSolenoid.Value.kOff);
        m_isLeftClimberMotorValid = false;
        m_isRightClimberMotorValid = false;
        m_currentState = State.GROUND;
    }

    public void configure(Configuration config) {
        m_config = config;

        m_leftClimberMotor.setInverted(!m_config.m_isRightSideInverted);
        m_leftClimberMotor.setIdleMode(m_config.m_climberMotorIdleMode);
        m_leftClimberMotor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);

        m_rightClimberMotor.setInverted(m_config.m_isRightSideInverted);
        m_rightClimberMotor.setIdleMode(m_config.m_climberMotorIdleMode);
        m_rightClimberMotor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);
    }

    // Method to extend climber arms
    public void extendClimber(double speed) {
        this.climberMotorSpeed(speed);
    }

    // Method to return climber arms
    public void returnClimber(double speed) {
        this.climberMotorSpeed(-1 * speed);
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

    public boolean areMotorsValid() {
        return m_isLeftClimberMotorValid && m_isRightClimberMotorValid;
    }

    public boolean validate(double distance, int amps) {
        boolean[] validMotorsExtension = validateExtensionDistanceMeters(distance);
        boolean[] validMotorsAmps = validateClimberMotorsAmps(amps);

        if (validMotorsExtension[0] && validMotorsAmps[0]) {
            m_isLeftClimberMotorValid = true;
        }
        if (validMotorsExtension[1] && validMotorsAmps[1]) {
            m_isRightClimberMotorValid = true;
        }
        return areMotorsValid();
    }

    public boolean validate(double distance) {
        boolean[] validMotors = validateExtensionDistanceMeters(distance);

        if (validMotors[0]) {
            m_leftClimberMotor.set(0.0);
            m_isLeftClimberMotorValid = true;
        }
        if (validMotors[1]) {
            m_rightClimberMotor.set(0.0);
            m_isRightClimberMotorValid = true;
        }
        return areMotorsValid();
    }

    public boolean[] validateClimberMotorsAmps(int amps) {
        boolean[] isAtAmps = new boolean[2];
        if (checkLeftClimberMotorAmps(amps)) {
            m_leftClimberMotor.set(0);
            isAtAmps[0] = true;
        }
        if (checkRightClimberMotorAmps(amps)) {
            m_leftClimberMotor.set(0);
            isAtAmps[1] = true;
        }
        return isAtAmps;
    }

    public boolean[] validateExtensionDistanceMeters(double distance) {
        boolean[] isAtDistance = new boolean[2];

        if (Utility.isWithinTolerance(getRightClimberExtension(), distance,
                Constants.CLIMBER.EXTENSION_TOLERANCE_METERS)) {
            isAtDistance[0] = true;
        }
        if (Utility.isWithinTolerance(getRightClimberExtension(), distance,
                Constants.CLIMBER.EXTENSION_TOLERANCE_METERS)) {
            isAtDistance[1] = true;
        }
        return isAtDistance;
    }

    public boolean checkClimberMissedMeters(double distance, int amps) {
      return false;
    }

    public void resetValidation() {
        m_isLeftClimberMotorValid = false;
        m_isRightClimberMotorValid = false;
    }

    public boolean checkLeftClimberMotorAmps(int amps) {
        return m_leftClimberMotor.getOutputCurrent() >= amps;
    }

    public boolean checkRightClimberMotorAmps(int amps) {
        return m_rightClimberMotor.getOutputCurrent() >= amps;
    }

    public boolean handleState(State newState) {
        if (areMotorsValid() && (newState.ordinal() == (m_currentState.ordinal() + 1))) {
            m_currentState = newState;
            resetValidation();
            return true;
        } else {
            return false;
        }
    }

    public void resetEncoders() {
        m_leftClimberMotor.getEncoder().setPosition(0);
        m_rightClimberMotor.getEncoder().setPosition(0);
    }

    private double rotationsToMeters(double rotations) {
        return rotations * Constants.CLIMBER.CLIMBER_GEAR_RATIO
                * (RobotMath.interpolateCurve(m_spoolCurveMeters, rotations) * Math.PI);
    }

    public double getLeftClimberExtension() {
        return this.rotationsToMeters(m_leftClimberMotor.getEncoder().getPosition());
    }

    public double getRightClimberExtension() {
        return this.rotationsToMeters(m_rightClimberMotor.getEncoder().getPosition());
    }

    public void setPivot(DoubleSolenoid.Value value) {
        m_climberSolenoid.set(value);
    }

    public State getState() {
        return m_currentState;
    }

    public void setState(State newState) {
        m_currentState = newState;
    }
}
