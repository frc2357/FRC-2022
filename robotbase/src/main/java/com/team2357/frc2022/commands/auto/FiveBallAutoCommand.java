package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.CargoAdjustCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederPackCommand;
import com.team2357.frc2022.commands.human.FireVisionCommand;
import com.team2357.frc2022.commands.intake.IntakeToFeederCommand;
import com.team2357.frc2022.util.AvailableTrajectories;
import com.team2357.frc2022.util.TrajectoryUtil;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FiveBallAutoCommand extends SequentialCommandGroup {
    public FiveBallAutoCommand() {   
        // Uncomment to test

        // // First ball
        // addCommands(new AutoStartPosShotCommand());
        // addCommands(new WaitCommand(0.5));
        // addCommands(new AutoFeederStartCommand());
        // addCommands(new WaitCommand(0.25));
        // addCommands(new AutoStopShootCommand());
        // addCommands(new AutoFeederStopCommand());

        // // Move and collect cargo

        // // Collect second cargo
        // addCommands(new ParallelDeadlineGroup(new SequentialCommandGroup(AvailableTrajectories.leaveTarmacTrajectory, new WaitCommand(0.25)),
        // new AutoIntakeCargoCommand()));

        // // Collect third cargo
        // addCommands(new ParallelDeadlineGroup(new SequentialCommandGroup(AvailableTrajectories.travelToThirdCargoTrajectory, new WaitCommand(0.25)),
        // new SequentialCommandGroup(new FeederAdvanceCommand(), new FeederExtraAdvanceCommand(), new AutoIntakeCargoCommand(), new FeederPackCommand() )));

        // // Shoot two cargo
        // addCommands(new ParallelCommandGroup(new AutoTurretRotateCommand(250, 0.4),
        //  new SequentialCommandGroup(new FeederAdvanceCommand(), new FeederExtraAdvanceCommand(), new FeederExtraAdvanceCommand())));
        
        // addCommands(new CargoAdjustCommand());
        // addCommands(new ParallelDeadlineGroup(new WaitCommand(5), new FireVisionCommand()));


        // // Last two

        // addCommands(new AutoDriveCommand(600, 0.0, 0.2));

        // // Move to terminal and collect cargo
        //  addCommands(new ParallelDeadlineGroup(
        //          new SequentialCommandGroup(new AutoDriveCommand(1500, 0.4, 0.0), new WaitCommand(2)),
        //          new SequentialCommandGroup(new AutoIntakeCargoCommand(), new FeederAdvanceCommand(),
        //                  new FeederExtraAdvanceCommand(), new AutoIntakeCargoCommand())));

        // Move back to shot position
    //    addCommands(new ParallelCommandGroup(new AutoDriveCommand(1500, -0.4, 0.0),
    //     new SequentialCommandGroup(new FeederAdvanceCommand(), new FeederExtraAdvanceCommand(), new FeederExtraAdvanceCommand())));


    //     // Shoot fourth and fifth cargo
    //     addCommands(new AutoTurretRotateCommand(250, 0.4));
    //     addCommands(new CargoAdjustCommand());
    //     addCommands(new ParallelDeadlineGroup(new WaitCommand(5), new FireVisionCommand()));

        // Cleanup
        // addCommands(new AutoStopShootCommand());
        // addCommands(new AutoFeederStopCommand());
        //addCommands(new AutoStopIntakeCommand());
    }
}
