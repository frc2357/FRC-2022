package com.team2357.frc2022.shuffleboard;

import com.team2357.frc2022.subsystems.IntakeSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class CargoNumberWidget extends ShuffleboardWidget {
    private static NetworkTableEntry m_cargoNumWidget;

    public CargoNumberWidget(String tabTitle) {
        super(tabTitle);

        NetworkTableEntry cellNumWidget = Shuffleboard.getTab(tabTitle)
            .add("Num of Cargo Cells", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
        
        m_cargoNumWidget = cargoNumWidget;
    }

    public void addCargo() {

    }
    public void subCargo() {
    
    }
}
    
    

