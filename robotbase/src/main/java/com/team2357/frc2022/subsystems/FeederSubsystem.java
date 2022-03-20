package com.team2357.frc2022.subsystems;

import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class FeederSubsystem extends ClosedLoopSubsystem {
    public static FeederSubsystem instance = null;

    public static FeederSubsystem getInstance() {
        return instance;
    }

    public static class Configuration {
        public double m_feederMotorAxisMaxSpeed = 0;
        public double m_feederMotorRunSpeed = 0;
    }

    private Configuration m_config;
    private WPI_VictorSPX m_feederMotor;

    public FeederSubsystem(WPI_VictorSPX feederMotor) {
        instance = this;
        m_feederMotor = feederMotor;

        addChild("feederMotor", m_feederMotor);
    }

    public void configure(Configuration config) {
        m_config = config;
        m_feederMotor.setInverted(true);
    }

    public void shoot() {
        m_feederMotor.set(ControlMode.PercentOutput, -m_config.m_feederMotorRunSpeed);
    }

    public void stop() {
        m_feederMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setAxisRollerSpeed(double axisSpeed) {
        double motorSpeed = axisSpeed * m_config.m_feederMotorAxisMaxSpeed;
        m_feederMotor.set(ControlMode.PercentOutput, -motorSpeed);
    }
}
