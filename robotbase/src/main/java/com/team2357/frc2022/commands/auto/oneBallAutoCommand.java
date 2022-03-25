package com.team2357.frc2022.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneBallAutoCommand extends SequentialCommandGroup{
    public OneBallAutoCommand() {
        addCommands(new AutoStartShotCommand());
        addCommands(new AutoDriveCommand(2000));
    }

}
