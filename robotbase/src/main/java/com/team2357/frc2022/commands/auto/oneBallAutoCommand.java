package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.util.AvailableTrajectories;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneBallAutoCommand extends SequentialCommandGroup{
    public OneBallAutoCommand() {
        addCommands(new AutoStartShotCommand());
        addCommands(new AutoDriveCommand(2000));
    }

}
