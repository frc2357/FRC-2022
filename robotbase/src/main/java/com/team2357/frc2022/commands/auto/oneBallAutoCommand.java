package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.intake.IntakeStowCommand;
import com.team2357.frc2022.commands.shooter.ShootAutoStartCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class oneBallAutoCommand extends SequentialCommandGroup{
    public oneBallAutoCommand() {
        //First ball
        addCommands(new AutoStartPosShotCommand());
        addCommands(new WaitCommand(0.5));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());

        // Move
        addCommands(new AutoDriveCommand(2000, 0.0));
        addCommands(new AutoStopShootCommand());
        addCommands(new WaitCommand(1));
    }

}
