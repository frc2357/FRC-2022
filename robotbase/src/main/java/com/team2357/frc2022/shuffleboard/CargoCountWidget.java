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

    private boolean m_readyToSub = false;
    private boolean m_readyToAdd = false;
    private int m_numOfCargo;

    public CargoCountWidget(String tabTitle, FeederSubsystem feederSub, IntakeSubsystem intakeSub) {
        super(tabTitle);
        m_feederSub = feederSub;
        m_intakeSub = intakeSub;

        NetworkTableEntry m_cargoCountWidget = Shuffleboard.getTab(tabTitle)
            .add("Num of Cargo Balls", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    }

    public void addCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        m_readyToAdd = false;
    }

    public void subCargo() {
        int numOfCargo = getNumOfCargo();
        setNumOfCargo(++numOfCargo);
        m_cargoCountWidget.setNumber(numOfCargo);
        m_readyToSub = false;
    }



    public void periodic() {
        if (m_readyToSub && !m_feederSub.isCargoAtFeederWheel()) {
            subCargo();
        } else if (!m_readyToSub && m_feederSub.isCargoAtFeederWheel()) {
            m_readyToSub = true;
        }
        if (m_readyToAdd && !m_intakeSub.isCargoInIntake()) {
            addCargo();
        } else if (!m_readyToAdd && m_intakeSub.isCargoInIntake()) {
            m_readyToAdd = true;
        }
    } 

    public void setNumOfCargo(int numOfCargo) {
        m_numOfCargo = numOfCargo;
    }

    public int getNumOfCargo() {
        return m_numOfCargo;
    }
}
