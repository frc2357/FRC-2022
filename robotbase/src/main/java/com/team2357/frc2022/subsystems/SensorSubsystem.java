package com.team2357.frc2022.subsystems;

import com.team2357.frc2022.sensors.SensorBooleanState;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SensorSubsystem extends SubsystemBase {
    private static SensorSubsystem instance = null;

    public static SensorSubsystem getInstance() {
        return instance;
    }

    private SensorBooleanState m_intakeSensor;
    private SensorBooleanState m_feederSensor;

    private boolean m_lastIntakeState;
    private boolean m_lastFeederState;
    private boolean m_cargoInIndex;
    private int m_cargoAcquired;
    private int m_cargoLaunched;

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public SensorSubsystem(SensorBooleanState intakeSensor, SensorBooleanState feederSensor) {
        instance = this;
        m_intakeSensor = intakeSensor;
        m_feederSensor = feederSensor;
        m_lastIntakeState = false;
        m_lastFeederState = false;
        m_cargoInIndex = false;
        m_cargoAcquired = 0;
        m_cargoLaunched = 0;
    }

    @Override
    public void periodic() {
        boolean intakeState = m_intakeSensor.getState();
        boolean feederState = m_feederSensor.getState();

        if (intakeState != m_lastIntakeState) {
            if (intakeState) {
                m_cargoInIndex = true;
                m_cargoAcquired++;
            }
            m_lastIntakeState = intakeState;
        }

        if (feederState != m_lastFeederState) {
            if (feederState) {
                m_cargoInIndex = false;
            }
            if (!feederState) {
                m_cargoLaunched++;
            }
            m_lastFeederState = feederState;
        }
    }

    public boolean isCargoInIndex() {
        return m_cargoInIndex;
    }

    public boolean isCargoInFeeder() {
        return m_lastFeederState;
    }

    public int getCargoAcquired() {
        return m_cargoAcquired;
    }

    public int getCargoLaunched() {
        return m_cargoLaunched;
    }
}
