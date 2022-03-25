package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.auto.shooter.AutoStartPosShotCommand;
import com.team2357.frc2022.commands.auto.shooter.AutoStopShootCommand;
import com.team2357.frc2022.commands.auto.shooter.TaxiStartShotCommand;
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

public class twoBallAutoCommand extends SequentialCommandGroup {

    public twoBallAutoCommand() {


        //First ball
        addCommands(new AutoStartPosShotCommand());
        addCommands(new WaitCommand(2));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());

        // Start intake
        addCommands(new AutoStartIntakeCommand());

        // Move
        addCommands(new AutoDriveCommand(2500));
        addCommands(new AutoStopShootCommand());
        addCommands(new WaitCommand(1));

        // Second ball
        addCommands(new TaxiStartShotCommand());
        addCommands(new WaitCommand(2));
        addCommands(new AutoFeederStartCommand());
        addCommands(new WaitCommand(1));

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new AutoFeederStopCommand());
        addCommands(new AutoStopIntake());




        // Shoot at starting configuration
        // addCommands(new ParallelDeadlineGroup(
        //         new WaitCommand(3),
        //         new ShootAutoStartCommand(),
        //         new SequentialCommandGroup(new WaitCommand(2), new FeederShootCommand())));
        // addCommands(new AutoStopShootCommand());

        // Start intake
        // addCommands(new AutoStartIntakeCommand());

        // // Move
        // addCommands(new AutoDriveCommand(2500));
        // addCommands(new AutoStopShootCommand());
        // addCommands(new WaitCommand(1));

        // // Taxi shot
        // addCommands(new ParallelDeadlineGroup(
        //         new WaitCommand(3),
        //         new ShootTaxiLineCommand(),
        //         new SequentialCommandGroup(new WaitCommand(2), new FeederShootCommand())));

        // Cleanup
        addCommands(new AutoStopShootCommand());
        addCommands(new IntakeStowCommand());

        addRequirements(ShooterSubsystem.getInstance(), FalconTrajectoryDriveSubsystem.getInstance(),
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
