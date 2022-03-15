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
    private static IntakeRollerSubsystem instance = null;

    public static IntakeRollerSubsystem getInstance() {
        return instance;
    }

    public static class Configuration {
       public double m_rollerTopSpeed = 0; 
       public double m_rollerAxisMaxSpeed = 0;
    }

    private Configuration m_config;
    private VictorSPX m_intakeVictor;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeRollerSubsystem(VictorSPX intakeVictor) {
        instance = this;
        m_intakeVictor = intakeVictor;
    }

    public void configure(Configuration config) {
        m_config = config;
    }

    public void start() {
        // Just set top speed for now, we'll do ramping later.
        m_intakeVictor.set(ControlMode.PercentOutput, m_config.m_rollerTopSpeed);
    }

    public void stop() {
        m_intakeVictor.set(ControlMode.PercentOutput, 0);
    }

    public void setAxisRollerSpeed(double axisSpeed) {
        double motorSpeed = (-axisSpeed) * m_config.m_rollerAxisMaxSpeed;
        m_intakeVictor.set(ControlMode.PercentOutput, motorSpeed);
    }
}
