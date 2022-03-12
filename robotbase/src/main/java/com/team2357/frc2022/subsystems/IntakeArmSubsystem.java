package com.team2357.frc2022.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The subsystem for the intake.
 * 
 * @category Intake
 * @category Subsystems
 */
public class IntakeArmSubsystem extends SubsystemBase {
    public static class Configuration {
       public int m_deployMilliseconds = 0; 
       public int m_stowMilliseconds = 0; 
    }

    private enum ArmState { Unknown, Deployed, Stowed };

    private Configuration  m_config;
    private DoubleSolenoid m_intakeSolenoid;
    private ArmState m_currentState;
    private ArmState m_desiredState;
    private long m_lastActionMillis;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeArmSubsystem(DoubleSolenoid intakeSolenoid) {
        m_intakeSolenoid = intakeSolenoid;
        m_intakeSolenoid.set(Value.kOff);
        m_currentState = ArmState.Unknown;
        m_desiredState = ArmState.Stowed;
        m_lastActionMillis = 0;
    }

    public void configure(Configuration config) {
        m_config = config;
    }

    public boolean isDeploying() {
        return (m_desiredState == ArmState.Deployed && m_currentState != ArmState.Deployed);
    }

    public boolean isDeployed() {
        return (m_currentState == ArmState.Deployed && m_desiredState == ArmState.Deployed);
    }

    public boolean isStowing() {
        return (m_desiredState == ArmState.Stowed && m_currentState != ArmState.Stowed);
    }

    public boolean isStowed() {
        return (m_currentState == ArmState.Stowed && m_desiredState == ArmState.Stowed);
    }

    public void deploy() {
        m_currentState = ArmState.Unknown;
        m_desiredState = ArmState.Deployed;
        m_lastActionMillis = 0;
    }

    public void stow() {
        m_currentState = ArmState.Unknown;
        m_desiredState = ArmState.Stowed;
        m_lastActionMillis = 0;
    }

    @Override
    public void periodic() {
        if (m_currentState != m_desiredState) {
            if (m_desiredState == ArmState.Deployed) {
                updateDeployed();
            } else if (m_desiredState == ArmState.Stowed) {
                updateStowed();
            }
        }
    }

    private void updateDeployed() {
        long now = System.currentTimeMillis();

        if (m_lastActionMillis == 0) {
            // We haven't activated the solenoid for this yet.
            m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
            m_lastActionMillis = now;
        } else if (now > m_lastActionMillis + m_config.m_deployMilliseconds) {
            // Enough time has passed, shut off the solenoid.
            m_intakeSolenoid.set(DoubleSolenoid.Value.kOff);
            m_currentState = ArmState.Deployed;
            m_lastActionMillis = 0;
        }
    }

    private void updateStowed() {
        long now = System.currentTimeMillis();

        if (m_lastActionMillis == 0) {
            // We haven't activated the solenoid for this yet.
            m_intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
            m_lastActionMillis = now;
        } else if (now > m_lastActionMillis + m_config.m_stowMilliseconds) {
            // Enough time has passed, shut off the solenoid.
            m_intakeSolenoid.set(DoubleSolenoid.Value.kOff);
            m_currentState = ArmState.Stowed;
            m_lastActionMillis = 0;
        }
    }
}
