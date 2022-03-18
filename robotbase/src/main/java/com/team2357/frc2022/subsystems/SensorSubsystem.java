package com.team2357.frc2022.subsystems;

import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.frc2022.subsystems.ClimberSubsystem.Configuration;

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
    private int m_currentCargoCount;
    private int m_cargoAcquired;
    private int m_cargoLaunched;

    private int m_intakeDebounceCounter;
    private int m_feederDebounceCounter;

    private long m_nextReadMillis;

    private Configuration m_config;

    public static class Configuration {
        public int m_feederDebounceCountMax = 0;
        public int m_intakeDebounceCountMax = 0;
        public int m_readIncrementMillis;
    }

    /**
     * @param intakeVictor Victor SPX to use to control intake
     */
    public SensorSubsystem(SensorBooleanState intakeSensor, SensorBooleanState feederSensor) {
        instance = this;
        m_intakeSensor = intakeSensor;
        m_feederSensor = feederSensor;
        m_lastIntakeState = false;
        m_lastFeederState = false;
        m_currentCargoCount = 0;
        m_cargoAcquired = 0;
        m_cargoLaunched = 0;

        m_intakeDebounceCounter = 0;
        m_feederDebounceCounter = 0;

        m_nextReadMillis = 0;
    }

    public void configure(Configuration config) {
        m_config = config;
    }

    @Override
    public void periodic() {
        boolean intakeState = m_intakeSensor.getState();
        boolean feederState = m_feederSensor.getState();

        if (System.currentTimeMillis() > m_nextReadMillis) {
            if (intakeState == m_lastIntakeState && m_intakeDebounceCounter > 0) {
                m_intakeDebounceCounter--;
            } else if (intakeState != m_lastIntakeState) {
                m_intakeDebounceCounter++;
            }

            if (m_intakeDebounceCounter >= m_config.m_intakeDebounceCountMax) {
                m_intakeDebounceCounter = 0;

                if (intakeState) {
                    m_currentCargoCount++;
                    m_cargoAcquired++;
                }

                m_lastIntakeState = intakeState;
            }

            if (feederState == m_lastFeederState && m_feederDebounceCounter > 0) {
                m_feederDebounceCounter--;
            } else if (feederState != m_lastFeederState) {
                m_feederDebounceCounter++;
            }

            if (m_feederDebounceCounter >= m_config.m_feederDebounceCountMax) {
                m_feederDebounceCounter = 0;

                if (!feederState) {
                    m_currentCargoCount--;
                    m_cargoLaunched++;
                }

                m_lastFeederState = feederState;
            }

            m_nextReadMillis = System.currentTimeMillis() + m_config.m_readIncrementMillis;
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
