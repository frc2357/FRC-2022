package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBallAutoCommand extends SequentialCommandGroup {

    public TwoBallAutoCommand() {
        
        //First ball
        addCommands(new AutoStartPosShotCommand());
        addCommands(new WaitCommand(0.5));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(0.25));
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());

        // Start intake
        addCommands(new AutoStartIntakeCommand());

        // Move
        addCommands(new AutoDriveCommand(2500, 0.1, 0.0));
        addCommands(new WaitCommand(0.25));

        // Second ball
        addCommands(new TaxiStartShotCommand());
        addCommands(new WaitCommand(0.75));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(0.5));

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());
        addCommands(new AutoStopIntakeCommand());

        addRequirements(ShooterSubsystem.getInstance(), FalconDriveSubsystem.getInstance(),
                IntakeRollerSubsystem.getInstance(), FeederSubsystem.getInstance(), IntakeArmSubsystem.getInstance());
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        ShooterSubsystem.getInstance().stop();
        IntakeRollerSubsystem.getInstance().stop();
        FeederSubsystem.getInstance().stop();
    }
}
