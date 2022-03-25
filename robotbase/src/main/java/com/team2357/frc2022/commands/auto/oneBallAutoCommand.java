package com.team2357.frc2022.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class oneBallAutoCommand extends SequentialCommandGroup{
    public oneBallAutoCommand() {
        addCommands(new AutoStartShotCommand());
        addCommands(new AutoDriveCommand(2000));
    }

}
