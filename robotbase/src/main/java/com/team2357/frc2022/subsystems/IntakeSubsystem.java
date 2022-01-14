package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

public class IntakeSubsystem extends ClosedLoopSubsystem {

    private VictorSPX m_intakeVictor;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeSubsystem(VictorSPX intakeVictor) {
        m_intakeVictor = intakeVictor;
    }

    /**
     * @param percentPowerOutput -1 = reverse | 0 = stop | 1 = foward
     */
    public void triggerIntakeRoller(double percentPowerOutput) {
        m_intakeVictor.set(ControlMode.PercentOutput, percentPowerOutput);
    }
}