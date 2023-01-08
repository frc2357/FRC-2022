package com.team2357.frc2022.commands;

import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DriveEnableCoastCommand extends SequentialCommandGroup{
    public DriveEnableCoastCommand() {
        addCommands(new WaitCommand(1));
    }
}
