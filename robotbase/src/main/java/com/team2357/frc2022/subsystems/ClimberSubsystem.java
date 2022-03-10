package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

/* * 
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

    private Configuration m_config;
    private CANSparkMax m_leftClimberMotor;
    private CANSparkMax m_rightClimberMotor;
    private DoubleSolenoid m_climberSolenoid;
    private Solenoid m_hookSolenoid;

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

        m_leftClimberMotor.setInverted(!m_config.m_isRightSideInverted);
        m_leftClimberMotor.setIdleMode(m_config.m_climberMotorIdleMode);
        m_leftClimberMotor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);

        m_rightClimberMotor.setInverted(m_config.m_isRightSideInverted);
        m_rightClimberMotor.setIdleMode(m_config.m_climberMotorIdleMode);
        m_rightClimberMotor.setSmartCurrentLimit(m_config.m_climberMotorStallLimitAmps,
                m_config.m_climberMotorFreeLimitAmps);
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
}
