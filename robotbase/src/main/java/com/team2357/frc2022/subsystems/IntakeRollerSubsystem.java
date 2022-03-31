package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
        public double m_rollerAdvanceSpeed = 0;
        public double m_rollerCollectSpeed = 0;
        public double m_rollerAxisMaxSpeed = 0;

        public int m_rollerContinousAmpLimit = 35;
        public int m_rollerPeakAmpLimit = 50;

        public int m_rollerSpeedUpMillis = 0;

        public boolean invertFollowerIntakeMotor = true;
    }

    private Configuration m_config;
    private WPI_TalonSRX m_masterIntakeTalon;
    private WPI_TalonSRX m_followerIntakeTalon;

    private double m_startupTime;

    /**
     * @param masterIntakeTalon Victor SPX to use to control intake
     */
    public IntakeRollerSubsystem(WPI_TalonSRX masterIntakeTalon, WPI_TalonSRX followerIntakeTalon) {
        instance = this;
        m_masterIntakeTalon = masterIntakeTalon;
        m_followerIntakeTalon = followerIntakeTalon;
    }

    public void configure(Configuration config) {
        m_config = config;

        m_followerIntakeTalon.setInverted(m_config.invertFollowerIntakeMotor);
        m_masterIntakeTalon.setInverted(!m_config.invertFollowerIntakeMotor);

        m_followerIntakeTalon.follow(m_masterIntakeTalon);

        m_masterIntakeTalon.setNeutralMode(NeutralMode.Coast);
        m_masterIntakeTalon.enableCurrentLimit(true);
        m_masterIntakeTalon.configPeakCurrentLimit(m_config.m_rollerPeakAmpLimit);
        m_masterIntakeTalon.configPeakCurrentDuration(0);
        m_masterIntakeTalon.configContinuousCurrentLimit(0);
    }

    public void collect() {
        // Just set top speed for now, we'll do ramping later.
        m_masterIntakeTalon.set(ControlMode.PercentOutput, m_config.m_rollerCollectSpeed);
        m_startupTime = System.currentTimeMillis() + m_config.m_rollerSpeedUpMillis;
    }

    public void advance() {
        m_masterIntakeTalon.set(ControlMode.PercentOutput, m_config.m_rollerAdvanceSpeed);
        m_startupTime = System.currentTimeMillis() + m_config.m_rollerSpeedUpMillis;
    }

    public void stop() {
        m_masterIntakeTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setAxisRollerSpeed(double axisSpeed) {
        double motorSpeed = (-axisSpeed) * m_config.m_rollerAxisMaxSpeed;
        m_masterIntakeTalon.set(ControlMode.PercentOutput, motorSpeed);
        m_startupTime = System.currentTimeMillis() + m_config.m_rollerSpeedUpMillis;

    }

    @Override
    public void periodic() {
        handleStall();
    }

    /**
     * 
     * @return True is stalling
     */
    public boolean handleStall() {
        if ((Math.abs(m_masterIntakeTalon.getStatorCurrent()) > m_config.m_rollerContinousAmpLimit
                && m_startupTime < System.currentTimeMillis())
                || m_masterIntakeTalon.getStatorCurrent() > m_config.m_rollerPeakAmpLimit) {
            stop();
            System.err.println("INTAKE STALLED");
            return true;
        }
        return false;
    }
}
