package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.CargoAdjustCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.intake.IntakeToFeederCommand;
import com.team2357.frc2022.util.AvailableTrajectories;
import com.team2357.frc2022.util.TrajectoryUtil;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FiveBallAutoCommand extends SequentialCommandGroup {
    public FiveBallAutoCommand() {
        // First, test trajectory recordings

        addCommands(AvailableTrajectories.leaveTarmacTrajectory);
        addCommands(AvailableTrajectories.travelToThirdCargoTrajectory);
        addCommands(AvailableTrajectories.travelToTerminalTrajectory);
        addCommands(AvailableTrajectories.travelToShotPositionTrajectory);


        // Three ball
        // // First ball
        // addCommands(new AutoStartPosShotCommand());
        // addCommands(new WaitCommand(0.5));
        // addCommands(new AutoFeederStartCommand());
        // addCommands(new WaitCommand(1));
        // addCommands(new AutoStopShootCommand());
        // addCommands(new AutoFeederStopCommand());

        // // Collect second cargo
        // addCommands(new ParallelDeadlineGroup(new
        // SequentialCommandGroup(AvailableTrajectories.leaveTarmacTrajectory, new
        // WaitCommand(0.25)),
        // new AutoIntakeCargoCommand()));

        // // Collect third cargo
        // addCommands(new ParallelDeadlineGroup(new
        // SequentialCommandGroup(AvailableTrajectories.travelToThirdCargoTrajectory,
        // new WaitCommand(0.25)),
        // new SequentialCommandGroup(new FeederAdvanceCommand(), new
        // FeederExtraAdvanceCommand(), new AutoIntakeCargoCommand() )));

        // // Should now have two cargo, adjusting for a maximum of two seconds before
        // shooting
        // new ParallelDeadlineGroup(new WaitCommand(30), new SequentialCommandGroup(new
        // CargoAdjustCommand(),
        // new CargoAdjustCommand(),
        // new CargoAdjustCommand()));

        // // Shoot second cargo
        // addCommands(new TaxiStartShotCommand());
        // addCommands(new WaitCommand(0.75));
        // addCommands(new AutoFeederStartCommand());
        // addCommands(new WaitCommand(0.5));
        // addCommands(new AutoFeederStopCommand());

        // // Shoot third third
        // addCommands(new WaitCommand(0.5));
        // new ParallelDeadlineGroup(new WaitCommand(1), new IntakeToFeederCommand());
        // addCommands(new AutoFeederStartCommand());
        // addCommands(new WaitCommand(0.5));

        // Last two

    //     // Move to terminal and collect cargo
    //     addCommands(new ParallelDeadlineGroup(
    //             new SequentialCommandGroup(AvailableTrajectories.travelToTerminalTrajectory, new WaitCommand(0.25)),
    //             new SequentialCommandGroup(new AutoIntakeCargoCommand(), new FeederAdvanceCommand(),
    //                     new FeederExtraAdvanceCommand(), new AutoIntakeCargoCommand())));

    //     // Move back to shot position
    //    new ParallelDeadlineGroup(AvailableTrajectories.travelToShotPositionTrajectory, new SequentialCommandGroup(new
    //     CargoAdjustCommand(),
    //     new CargoAdjustCommand(),
    //     new CargoAdjustCommand()));


        // Shoot last two

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());
        //addCommands(new AutoStopIntakeCommand());
    }
}
