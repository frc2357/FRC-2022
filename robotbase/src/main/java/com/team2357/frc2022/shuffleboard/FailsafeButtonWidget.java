package com.team2357.frc2022.shuffleboard;

import com.team2357.frc2022.commands.FailsafeCommand;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.triggers.ToggleTrigger;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class FailsafeButtonWidget extends ShuffleboardWidget {
    private final NetworkTableEntry m_failsafeButton;
    private ToggleTrigger failsafeTrigger;

    public FailsafeButtonWidget(String tabTitle, String subName, ClosedLoopSubsystem subsystem) {
        super(tabTitle);
        m_failsafeButton = Shuffleboard.getTab(tabTitle)
            .add(subName, false)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();
        
        failsafeTrigger = new ToggleTrigger(m_failsafeButton);
        failsafeTrigger.whenActive(new FailsafeCommand(true, subsystem));
        failsafeTrigger.whenInactive(new FailsafeCommand(false, subsystem));

    }
}