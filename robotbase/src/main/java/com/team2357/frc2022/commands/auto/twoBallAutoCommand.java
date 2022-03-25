package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBallAutoCommand extends SequentialCommandGroup{
    public TwoBallAutoCommand() {
        addCommands(new AutoStartShotCommand());
        addCommands(new ParallelCommandGroup(
                new IntakeDeployCommand(false),
                new AutoDriveCommand(2000)));
        addCommands(new WaitCommand(1));
        addCommands(new AutoTaxiShotCommand());

        addRequirements(ShooterSubsystem.getInstance(), FalconTrajectoryDriveSubsystem.getInstance(), IntakeRollerSubsystem.getInstance(),  FeederSubsystem.getInstance(), IntakeArmSubsystem.getInstance());
    }
}
