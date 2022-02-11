package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The subsystem for the intake.
 * 
 * @category Intake
 * @category Subsystems
 */
public class IntakeSubsystem extends ClosedLoopSubsystem {
    public DoubleSolenoid m_intakeSolenoid;
    private VictorSPX m_intakeVictor;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeSubsystem(VictorSPX intakeVictor, DoubleSolenoid intakeSolenoid) {
        m_intakeSolenoid = intakeSolenoid;
        m_intakeSolenoid.set(Value.kOff);

        m_intakeVictor = intakeVictor;
    }

    /**
     * @param percentPowerOutput -1 = reverse | 0 = stop | 1 = foward
     */
    public void triggerIntakeRoller(double percentPowerOutput) {
        m_intakeVictor.set(ControlMode.PercentOutput, percentPowerOutput);
    }

    public DoubleSolenoid.Value getPivot() {
        return m_intakeSolenoid.get();
    }

    /**
     * @param value Value for the solenoid
     */
    public void setPivot(DoubleSolenoid.Value value) {
        m_intakeSolenoid.set(value);
    }
}