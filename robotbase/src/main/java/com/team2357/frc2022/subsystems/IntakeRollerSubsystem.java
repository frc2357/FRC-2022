package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

/**
 * The subsystem for the intake roller.
 * 
 * @category Intake
 * @category Subsystems
 */
public class IntakeRollerSubsystem extends ClosedLoopSubsystem {
    public static class Configuration {
       public double m_rollerTopSpeed = 0; 
    }

    private Configuration m_config;
    private VictorSPX m_intakeVictor;
    private boolean m_isRunning;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeRollerSubsystem(VictorSPX intakeVictor) {
        m_intakeVictor = intakeVictor;
    }

    public void configure(Configuration config) {
        m_config = config;
    }

    public boolean isRunning() {
        return m_isRunning;
    }

    public void start() {
        // Just set top speed for now, we'll do ramping later.
        m_intakeVictor.set(ControlMode.PercentOutput, m_config.m_rollerTopSpeed);
        m_isRunning = true;
    }

    public void reverse() {
        m_intakeVictor.set(ControlMode.PercentOutput, -m_config.m_rollerTopSpeed);
        m_isRunning = true;
    }

    public void stop() {
        m_intakeVictor.set(ControlMode.PercentOutput, 0);
        m_isRunning = false;
    }
}
