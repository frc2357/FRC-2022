package com.team2357.frc2022.subsystems;

import com.team2357.frc2022.sensors.SensorBooleanState;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SensorSubsystem extends SubsystemBase {
    private SensorBooleanState m_intakeSensor;
    private SensorBooleanState m_feederSensor;

    private boolean m_lastIntakeState;
    private boolean m_lastFeederState;
    private int m_currentCargoCount;
    private int m_cargoAcquired;
    private int m_cargoLaunched;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public SensorSubsystem(SensorBooleanState intakeSensor, SensorBooleanState feederSensor) {
        m_intakeSensor = intakeSensor;
        m_feederSensor = feederSensor;
        m_lastIntakeState = false;
        m_lastFeederState = false;
        m_currentCargoCount = 0;
        m_cargoAcquired = 0;
        m_cargoLaunched = 0;
    }

    @Override
    public void periodic() {
        boolean intakeState = m_intakeSensor.getState();
        boolean feederState = m_feederSensor.getState();

        if (intakeState != m_lastIntakeState) {
            if (intakeState) {
                m_currentCargoCount++;
                m_cargoAcquired++;
            }
            m_lastIntakeState = intakeState;
        }

        if (feederState != m_lastFeederState) {
            if (!feederState) {
                m_currentCargoCount--;
                m_cargoLaunched++;
            }
            m_lastFeederState = feederState;
        }
    }

    public boolean isCargoInFeeder() {
        return m_lastIntakeState;
    }

    public int getCurrentCargoCount() {
        return m_currentCargoCount;
    }

    public int getCargoAcquired() {
        return m_cargoAcquired;
    }

    public int getCargoLaunched() {
        return m_cargoLaunched;
    }
}
