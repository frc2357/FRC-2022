package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team2357.frc2022.sensors.SensorBooleanState;
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
    private SensorBooleanState m_intakeSensor;
    private boolean m_isIntakeDeployed;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public IntakeSubsystem(VictorSPX intakeVictor, DoubleSolenoid intakeSolenoid, SensorBooleanState intakeSensor) {
        m_intakeSolenoid = intakeSolenoid;
        m_intakeSolenoid.set(Value.kOff);
        m_intakeSensor = intakeSensor;
        m_intakeVictor = intakeVictor;
        m_isIntakeDeployed = false;
    }

    /**
     * @param percentPowerOutput -1 = reverse | 0 = stop | 1 = foward
     */
    public void triggerIntakeRoller(double percentPowerOutput) {
        m_intakeVictor.set(ControlMode.PercentOutput, percentPowerOutput);
    }

    public Boolean isIntakeDeployed() {
        return m_isIntakeDeployed;
    }

    /**
     * @param value Value for the solenoid
     */
    public void setPivot(DoubleSolenoid.Value value) {
        m_intakeSolenoid.set(value);
        if (value == DoubleSolenoid.Value.kForward) {
            m_isIntakeDeployed = true;
        } else if (value == DoubleSolenoid.Value.kReverse) {
            m_isIntakeDeployed = false;
        }
    }

    public boolean isCargoInIntake() {
        return m_intakeSensor.getState();
    }
}