package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.LimelightWaitForTarget;
import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.feeder.WaitForFeederSensorCommand;
import com.team2357.frc2022.commands.intake.IntakeAdvanceCommand;
import com.team2357.frc2022.commands.shooter.ShooterSetRPMsCommand;
import com.team2357.frc2022.commands.shooter.ShooterVisionShootCommand;
import com.team2357.frc2022.commands.shooter.ShooterWaitForRPMsCommand;
import com.team2357.frc2022.commands.shooter.WaitForVisionShotReady;
import com.team2357.frc2022.commands.turret.TurretTrackCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.LimelightTargetingPipelineCommand;
import com.team2357.lib.subsystems.LimelightSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FireVisionCommand extends SequentialCommandGroup {
    private static final int PRIME_RPMS_LOW = 1000;
    private static final int PRIME_RPMS_HIGH = 2000;

    public FireVisionCommand() {
        addRequirements(ShooterSubsystem.getInstance());
        addRequirements(TurretSubsystem.getInstance());
        addRequirements(FeederSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());

        // Spin up and set the limelight pipeline
        addCommands(new ParallelDeadlineGroup(
            new LimelightTargetingPipelineCommand(true),
            new ShooterSetRPMsCommand(PRIME_RPMS_LOW, PRIME_RPMS_HIGH)
        ));

        // Wait for us to have a target
        addCommands(new ParallelDeadlineGroup(
            new LimelightWaitForTarget(),
            new ShooterSetRPMsCommand(PRIME_RPMS_LOW, PRIME_RPMS_HIGH)
        ));

        // Start turret tracking and shooter RPM tracking until we get to an accurate point
        addCommands(new ParallelDeadlineGroup(
            new WaitForVisionShotReady(),
            new TurretTrackCommand(),
            new ShooterVisionShootCommand()
        ));

        // Wait for a bit to let the shooter RPMs stabilize
        addCommands(new ParallelDeadlineGroup(
            new SequentialCommandGroup(new WaitCommand(1.0), new ShooterWaitForRPMsCommand()),
            new ShooterVisionShootCommand()
        ));

        // Shoot the first ball until it's away
        addCommands(new ParallelDeadlineGroup(
            new WaitForFeederSensorCommand(false),
            new ShooterVisionShootCommand(),
            new FeederShootCommand(),
            new IntakeAdvanceCommand()
        ));

        // Keep the shooter at speed while we advance the intake to get the next ball up to it
        addCommands(new ParallelDeadlineGroup(
            new SequentialCommandGroup(new WaitCommand(1.0), new ShooterWaitForRPMsCommand()),
            new ShooterVisionShootCommand(),
            new IntakeAdvanceCommand()
        ));

        // Shoot the second ball until it's away
        addCommands(new ParallelDeadlineGroup(
            new WaitForFeederSensorCommand(false),
            new ShooterVisionShootCommand(),
            new FeederShootCommand()
        ));
    }

    public void end(boolean interrupted) {
        super.end(interrupted);
        LimelightSubsystem.getInstance().setHumanPipelineActive();
        ShooterSubsystem.getInstance().stop();
        TurretSubsystem.getInstance().stop();
        FeederSubsystem.getInstance().stop();
    }
}
