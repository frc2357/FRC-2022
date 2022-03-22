package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.util.AvailableTrajectories;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneBallAutoCommand extends SequentialCommandGroup{
    public OneBallAutoCommand() {
        addCommands(new FireCargoAutoCommand());
        addCommands(AvailableTrajectories.leaveTarmacTrajectory);
    }
}
