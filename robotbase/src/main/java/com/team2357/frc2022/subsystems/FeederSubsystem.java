package com.team2357.frc2022.subsystems;

import com.team2357.frc2022.Constants;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class FeederSubsystem extends ClosedLoopSubsystem {
    private WPI_TalonSRX m_feederMotor;

    public FeederSubsystem(WPI_TalonSRX talonSRX) {
        m_feederMotor = talonSRX;
        m_feederMotor.setInverted(true); // Does this still need to be inverted?


        addChild("feederMotor", m_feederMotor);
    }

    public void runFeedermotor(double speed) {
        m_feederMotor.set(ControlMode.PercentOutput, speed);
    }

    // Sensor state will return false when an object is too close. Function will
    // flip that to true for reability
    public boolean isBallAtFeederWheel() {
        boolean isSensorBlocked = false;
        return isSensorBlocked;
    }

}
