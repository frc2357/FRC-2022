package com.team2357.frc2022.subsystems;

import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class FeederSubsystem extends ClosedLoopSubsystem {
    public static FeederSubsystem instance = null;

    public static FeederSubsystem getInstance() {
        return instance;
    }

    public static class Configuration {
        public double m_feederMotorAxisMaxSpeed = 0;
        public double m_feederMotorAdvanceSpeed = 0;
        public double m_feederMotorShootSpeed = 0;
        public double m_feederMotorPackSpeed = 0;
    }

    private Configuration m_config;
    private WPI_VictorSPX m_feederMotor;
    private boolean m_isPacking;

    public FeederSubsystem(WPI_VictorSPX feederMotor) {
        instance = this;
        m_feederMotor = feederMotor;
        m_isPacking = false;

        addChild("feederMotor", m_feederMotor);
    }

    public void configure(Configuration config) {
        m_config = config;
        m_feederMotor.setInverted(true);
        m_feederMotor.setNeutralMode(NeutralMode.Brake);
    }

    public boolean isPacking() {
        return m_isPacking;
    }

    public void advance() {
        m_feederMotor.set(ControlMode.PercentOutput, m_config.m_feederMotorAdvanceSpeed);
        m_isPacking = false;
    }

    public void shoot() {
        m_feederMotor.set(ControlMode.PercentOutput, m_config.m_feederMotorShootSpeed);
        m_isPacking = false;
    }

    public void pack() {
        m_feederMotor.set(ControlMode.PercentOutput, m_config.m_feederMotorPackSpeed);
        m_isPacking = true;
    }

    public void stop() {
        m_feederMotor.set(ControlMode.PercentOutput, 0);
        m_isPacking = false;
    }

    public void setAxisRollerSpeed(double axisSpeed) {
        double motorSpeed = axisSpeed * m_config.m_feederMotorAxisMaxSpeed;
        m_feederMotor.set(ControlMode.PercentOutput, -motorSpeed);
    }
}
