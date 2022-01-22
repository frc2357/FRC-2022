package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
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

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;

    ClimberSubsystem(CANSparkMax leftClimberMotor, CANSparkMax rightClimberMotor, DoubleSolenoid climberSolenoid) {
        m_leftClimberMotor = leftClimberMotor;
        m_rightClimberMotor = rightClimberMotor;
        m_climberSolenoid = climberSolenoid;
        m_climberSolenoid.set(Value.kOff);
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

    // Method to extend bars to attach to reachable rung
    public void extendReachable() {
        m_leftClimberMotor.set(m_config.m_climbExtendSpeed);
        m_rightClimberMotor.set(m_config.m_climbExtendSpeed);
    }

    // Method to pull onto reachable rung
    public void returnReachable() {
        m_leftClimberMotor.set(m_config.m_climbReturnSpeed);
        m_rightClimberMotor.set(m_config.m_climbReturnSpeed);
    }
}
