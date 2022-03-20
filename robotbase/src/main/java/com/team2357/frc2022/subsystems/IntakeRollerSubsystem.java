package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The subsystem for the intake roller.
 * 
 * @category Intake
 * @category Subsystems
 */
public class IntakeRollerSubsystem extends ClosedLoopSubsystem {
    private static IntakeRollerSubsystem instance = null;
    private static int instances = 0;

    public static IntakeRollerSubsystem getInstance() {
        return instance;
    }

    public static class Configuration {
        public double m_rollerTopSpeed = 0;
        public double m_rollerAxisMaxSpeed = 0;

        public double m_rollerContinousAmpLimit = 35;
        public double m_rollerPeakAmpLimit = 50;

        public double m_rollerSpeedUpMillis = 2000;
    }

    private Configuration m_config;
    private WPI_TalonSRX m_intakeTalon;

    private double m_startupTime;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeRollerSubsystem(WPI_TalonSRX intakeVictor) {
        instance = this;
        instances++;
        m_intakeTalon = intakeVictor;
    }

    public void configure(Configuration config) {
        m_config = config;

        m_intakeTalon.setNeutralMode(NeutralMode.Coast);
        m_intakeTalon.enableCurrentLimit(true);
        m_intakeTalon.configPeakCurrentLimit(60);
        m_intakeTalon.configPeakCurrentDuration(0);
        m_intakeTalon.configContinuousCurrentLimit(0);
    }

    public void start() {
        // Just set top speed for now, we'll do ramping later.
        m_intakeTalon.set(ControlMode.PercentOutput, m_config.m_rollerTopSpeed);
        m_startupTime = System.currentTimeMillis() + m_config.m_rollerSpeedUpMillis;
    }

    public void stop() {
        m_intakeTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setAxisRollerSpeed(double axisSpeed) {
        double motorSpeed = (-axisSpeed) * m_config.m_rollerAxisMaxSpeed;
        m_intakeTalon.set(ControlMode.PercentOutput, motorSpeed);
        m_startupTime = 0;

    }

    @Override
    public void periodic() {
        handleStall();

        SmartDashboard.putNumber("amps", m_intakeTalon.getStatorCurrent());
        SmartDashboard.putNumber("Instance", instances);
}

    /**
     * 
     * @return True is stalling
     */
    public boolean handleStall() {
        if ((Math.abs(m_intakeTalon.getStatorCurrent()) > m_config.m_rollerContinousAmpLimit
                && m_startupTime < System.currentTimeMillis())
                || m_intakeTalon.getStatorCurrent() > m_config.m_rollerPeakAmpLimit) {
            stop();
            return true;
        }
        return false;
    }
}
