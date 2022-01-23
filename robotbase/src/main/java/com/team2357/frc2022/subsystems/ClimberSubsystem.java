package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.IFollower;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Subsystem to control climbing
 * 
 * @category climber
 */
public class ClimberSubsystem extends ClosedLoopSubsystem {

    public static class Configuration {
        public IdleMode m_climberMotorIdleMode = IdleMode.kBrake;

        // Speed of motors to extend to reachable bar (low, mid)
        public double m_climbExtendSpeed = 0;

        // Speed of motors to pull onto reachable bar (low, mid)
        public double m_climbReturnSpeed = 0;

        // Speed of motors to extend to unreachable bar (high, traversal)
        public double m_transExtendSpeed = 0;

        // Speed of motors to pull onto unreachable bar (high, traversal)
        public double m_transReturnSpeed = 0;

        public int m_climberMotorStallLimitAmps = 0;
        public int m_climberMotorFreeLimitAmps = 0;
        public boolean m_isRightSideInverted = false;
    }

    private enum State {
        GROUND,
        LOW_RUNG,
        MID_RUNG,
        HIGH_RUNG,
        TRAVERSAL_RUNG
    }

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;
    private boolean m_isLeftClimberMotorValid;
    private boolean m_isRightClimberMotorValid;
    private State m_currentState;

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid) {
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_climberSolenoid.set(Value.kOff);
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
      this.climberMotorSpeed(-1*speed);
    }

    // Method to set climber speed
    public void climberMotorSpeed(double speed) {
        m_leftClimberMotor.set(speed);
        m_rightClimberMotor.set(speed);
    }

    //Method to stop the motors
    public void StopClimberMotors() {
        m_leftClimberMotor.set(0);
        m_rightClimberMotor.set(0);
    }

    public void StopClimberMotorsAmps(int amps) {
        if(checkLeftClimberMotorAmps(amps)) {
            m_leftClimberMotor.set(0);
            m_isRightClimberMotorValid = true;
        }
        if (checkRightClimberMotorAmps(amps)) {
            m_leftClimberMotor.set(0);
            m_isLeftClimberMotorValid = true;
        }
    }

    public boolean checkLeftClimberMotorAmps(int amps) {
        return m_leftClimberMotor.getOutputCurrent() >= amps;
    }

    public boolean checkRightClimberMotorAmps(int amps) {
        return m_rightClimberMotor.getOutputCurrent() >= amps;
    }

    public boolean isValid() {
        return m_isLeftClimberMotorValid && m_isRightClimberMotorValid;
    }

    public void resetValidation() {
        m_isLeftClimberMotorValid = false;
        m_isRightClimberMotorValid = false;
    }

    public double getLeftEncoderDistanceMeters() {
        return 0;
    }

    public double getRightEncoderDistanceMeters() {
        return 0;
    }

    public boolean validateExtensionDistance(double distance) {
        boolean isAtDistance = true;
        if (Utility.isWithinTolerance(getRightEncoderDistanceMeters(), distance, 0)) {
            m_leftClimberMotor.set(0.0);
        } else {
            isAtDistance = false;
        }
        if (Utility.isWithinTolerance(getRightEncoderDistanceMeters(), distance, 0)) {
            m_rightClimberMotor.set(0.0);
        } else {
            isAtDistance = false;
        }
        return isAtDistance;
    }

    public void setPivot(DoubleSolenoid.Value value){
        m_climberSolenoid.set(value);
    }

    public State getState() {
        return m_currentState;
    }

    public void setState(State newState) {
        m_currentState = newState;
    }
}
