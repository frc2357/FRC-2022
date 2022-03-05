package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

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

        public int m_climberMissedToleranceRotations = 0;

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
    private Solenoid m_hookSolenoid;
    private boolean m_isLeftClimberMotorValid;
    private boolean m_isRightClimberMotorValid;
    private State m_currentState;

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid,
            Solenoid hookSolenoid) {
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_hookSolenoid = hookSolenoid;
        m_climberSolenoid.set(DoubleSolenoid.Value.kOff);
        m_hookSolenoid.set(false);
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

    public boolean validate(double rotations, int amps) {
        boolean[] validMotorsExtension = validateRotations(rotations);
        boolean[] validMotorsAmps = validateClimberMotorsAmps(amps);

        if (validMotorsExtension[0] && validMotorsAmps[0]) {
            m_isLeftClimberMotorValid = true;
        }
        if (validMotorsExtension[1] && validMotorsAmps[1]) {
            m_isRightClimberMotorValid = true;
        }
        return areMotorsValid();
    }

    public boolean validate(double rotations) {
        boolean[] validMotors = validateRotations(rotations);

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

    public boolean[] validateRotations(double rotations) {
        boolean[] isAtDistance = new boolean[2];

        if (Utility.isWithinTolerance(getLeftClimberRotations(), rotations,
                Constants.CLIMBER.EXTENSION_TOLERANCE_METERS)) {
            isAtDistance[0] = true;
        }
        if (Utility.isWithinTolerance(getRightClimberRotations(), rotations,
                Constants.CLIMBER.EXTENSION_TOLERANCE_METERS)) {
            isAtDistance[1] = true;
        }
        return isAtDistance;
    }

    public boolean checkRightClimberMotorMissed(double targetRotations, int amps) {
        if (getRightClimberRotations() < targetRotations - m_config.m_climberMissedToleranceRotations
                && !checkRightClimberMotorAmps(amps)) {
            return true;
        }
        return false;
    }

    public boolean checkLeftClimberMotorMissed(double targetRotations, int amps) {
        if (getLeftClimberRotations() < targetRotations && !checkRightClimberMotorAmps(amps)) {
            return true;
        }
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

    public double getLeftClimberRotations() {
        return m_leftClimberMotor.getEncoder().getPosition();
    }

    public double getRightClimberRotations() {
        return m_rightClimberMotor.getEncoder().getPosition();
    }

    public void setClimberPivot(DoubleSolenoid.Value value) {
        m_climberSolenoid.set(value);
    }

    public DoubleSolenoid.Value getClimberPivot() {
        return m_climberSolenoid.get();
    }

    public void setHookPivot(boolean value) {
        m_hookSolenoid.set(value);
    }

    public boolean isHookOpen() {
        return m_hookSolenoid.get();
    }

    public State getState() {
        return m_currentState;
    }

    public void setState(State newState) {
        m_currentState = newState;
    }

}
