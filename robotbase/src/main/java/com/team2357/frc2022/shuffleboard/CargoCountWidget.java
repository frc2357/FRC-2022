package com.team2357.frc2022.shuffleboard;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class CargoCountWidget extends ShuffleboardWidget {
    private final FeederSubsystem m_feederSub;
    private final IntakeSubsystem m_intakeSub;
    private static NetworkTableEntry m_cargoCountWidget;

    private boolean readyToSub = false;
    private boolean readyToAdd = false;
    private int m_numOfCargo;

    public CargoCountWidget(String tabTitle, FeederSubsystem feederSub, IntakeSubsystem intakeSub) {
        super(tabTitle);
        m_feederSub = feederSub;
        m_intakeSub = intakeSub;

        NetworkTableEntry cargoCountWidget = Shuffleboard.getTab(tabTitle)
            .add("Num of Cargo Balls", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

        m_cargoCountWidget = cargoCountWidget;
    }

    public void addCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        readyToAdd = false;
    }

    public void subCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        readyToSub = false;
    }



    public void periodic() {
        if (readyToSub && !m_feederSub.isBallAtFeederWheel()) {
            subCargo();
        } else if (!readyToSub && m_feederSub.isBallAtFeederWheel()) {
            readyToSub = true;
        }
        if (readyToAdd && !m_intakeSub.isBallAtSensor()) {
            addCargo();
        } else if (!readyToAdd && m_intakeSub.isBallAtSensor()) {
            readyToAdd = true;
        }
    } 

    public void setNumOfCargo(int numOfCargo) {
        m_numOfCargo = numOfCargo;
    }

    public int getNumOfCargo() {
        return m_numOfCargo;
    }
}
