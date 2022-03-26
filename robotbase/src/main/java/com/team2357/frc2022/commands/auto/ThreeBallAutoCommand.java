package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;

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

        // Start intake
        addCommands(new AutoStartIntakeCommand());

        // Move
        addCommands(new AutoDriveCommand(2500, Constants.DRIVE.AUTO_SPEED, 0.0));
        addCommands(new AutoStopShootCommand());
        addCommands(new WaitCommand(1));

        // Second ball
        addCommands(new TaxiStartShotCommand());
        addCommands(new WaitCommand(0.75));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));
        addCommands(new AutoFeederStopCommand());

        // Move back
        addCommands(new AutoStartIntakeCommand());
        addCommands(new AutoDriveCommand(250, -0.2, 0.0));

        // Turn 90 to second cargo
        addCommands(new AutoDriveCommand(2100, Constants.DRIVE.AUTO_SPEED, -0.2));

        // Move to second cargo
        addCommands(new AutoStartIntakeCommand());
        addCommands(new AutoDriveCommand(2000, 0.2, 0.0));

        // Rotate 90 to shoot
       addCommands(new AutoDriveCommand(800, Constants.DRIVE.AUTO_SPEED, 0.2));

        // Third Ball
        addCommands(new TaxiStartShotCommand());
        addCommands(new WaitCommand(0.75));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());
    }
}
