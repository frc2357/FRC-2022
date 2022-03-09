package com.team2357.frc2022.shuffleboard;

import com.team2357.frc2022.sensors.SensorBooleanState;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class CargoCountWidget extends ShuffleboardWidget {
    private final SensorBooleanState m_feederSensor;
    private final SensorBooleanState m_intakeSensor;
    private static NetworkTableEntry m_cargoCountWidget;

    private boolean m_feederStateChange = false;
    private boolean m_intakeStateChange = false;
    private int m_numOfCargo;

    public CargoCountWidget(String tabTitle, SensorBooleanState feederSensor, SensorBooleanState intakeSensor) {
        super(tabTitle);
        m_feederSensor = feederSensor;
        m_intakeSensor = intakeSensor;

        m_cargoCountWidget = Shuffleboard.getTab(tabTitle)
            .add("Num of Cargo Balls", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    }

    public void addCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        m_intakeStateChange = false;
    }

    public void subCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        m_feederStateChange = false;
    }



    public void periodic() {
        if (m_feederStateChange && !m_feederSensor.getState()) {
            subCargo();
        } else if (!m_feederStateChange && m_feederSensor.getState()) {
            m_feederStateChange = true;
        }
        if (m_intakeStateChange) {
            addCargo();
        } else if (!m_intakeStateChange && m_intakeSensor.getState()) {
            m_intakeStateChange = true;
        }
    } 

    public void setNumOfCargo(int numOfCargo) {
        m_numOfCargo = numOfCargo;
    }

    public int getNumOfCargo() {
        return m_numOfCargo;
    }
}
