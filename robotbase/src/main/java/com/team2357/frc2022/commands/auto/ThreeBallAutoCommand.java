package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.CargoAdjustCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.human.FireVisionCommand;
import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.intake.IntakeToFeederCommand;
import com.team2357.frc2022.util.AvailableTrajectories;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ThreeBallAutoCommand extends SequentialCommandGroup {
    public ThreeBallAutoCommand() {

        // First ball
        addCommands(new AutoStartPosShotCommand());
        addCommands(new WaitCommand(0.5));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());

        // Move and collect cargo
        
        // Collect second cargo
        addCommands(new ParallelDeadlineGroup(new SequentialCommandGroup(AvailableTrajectories.leaveTarmacTrajectory, new WaitCommand(0.25)),
        new AutoIntakeCargoCommand()));

        // Collect third cargo
        addCommands(new ParallelDeadlineGroup(new SequentialCommandGroup(AvailableTrajectories.travelToThirdCargoTrajectory, new WaitCommand(0.25)),
        new SequentialCommandGroup(new FeederAdvanceCommand(), new FeederExtraAdvanceCommand(), new AutoIntakeCargoCommand() )));

        // Should now have two cargo, adjusting for a maximum of two seconds before shooting
        new ParallelDeadlineGroup(new WaitCommand(30), new SequentialCommandGroup(new CargoAdjustCommand(),
        new CargoAdjustCommand(),
        new CargoAdjustCommand()));

        // Shoot two cargo
        addCommands(new FireVisionCommand());


        // Cleanup
       addCommands(new AutoStopShootCommand());
       addCommands(new AutoFeederStopCommand());
       addCommands(new AutoStopIntakeCommand());
    }

    // Old movement to third cargo
    
    //     // Move back
    //     addCommands(new AutoStartIntakeCommand());
    //     addCommands(new AutoDriveCommand(250, -0.2, 0.0));

    //     // Turn 90 to second cargo
    //     addCommands(new AutoDriveCommand(2100, Constants.DRIVE.AUTO_SPEED, -0.2));

    //     // Move to second cargo
    //     addCommands(new AutoStartIntakeCommand());
    //     addCommands(new AutoDriveCommand(2000, 0.2, 0.0));

    //     // Rotate 90 to shoot
    //    addCommands(new AutoDriveCommand(800, Constants.DRIVE.AUTO_SPEED, 0.2));


}
