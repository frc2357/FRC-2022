package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederPackCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoNoTrajectoryThreeBallCommand extends SequentialCommandGroup {

    public AutoNoTrajectoryThreeBallCommand() {
        // First ball
        addCommands(new AutoStartPosShotCommand());
        addCommands(new WaitCommand(0.5));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(0.25));
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());

        // Move and collect cargo

        addCommands(new AutoDriveCommand(500, Constants.DRIVE.AUTO_SPEED, 0.0));
        addCommands(new WaitCommand(0.25));
        addCommands(new AutoDriveCommand(300, -0.2, 0.0));

        // addCommands(new ParallelDeadlineGroup(
        //     // Movement
        //     new SequentialCommandGroup(
        //         new AutoDriveCommand(500, Constants.DRIVE.AUTO_SPEED, 0.0),
        //         new WaitCommand(0.25),
        //         new AutoDriveCommand(300, -0.2, 0.0)),
        //     // Cargo collection
        //     new SequentialCommandGroup(
        //         new AutoIntakeCargoCommand(), 
        //         new FeederAdvanceCommand(),
        //         new FeederExtraAdvanceCommand(), 
        //         new AutoIntakeCargoCommand(), 
        //         new FeederPackCommand())));

        // Second ball
        // addCommands(new TaxiStartShotCommand());
        // addCommands(new WaitCommand(0.75));
        // addCommands(new AutoFeederStartCommand());
        // addCommands(new WaitCommand(0.5));

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());
        addCommands(new AutoStopIntakeCommand());

        addRequirements(ShooterSubsystem.getInstance(), FalconDriveSubsystem.getInstance(),
                IntakeRollerSubsystem.getInstance(), FeederSubsystem.getInstance(), IntakeArmSubsystem.getInstance());
    }
}
