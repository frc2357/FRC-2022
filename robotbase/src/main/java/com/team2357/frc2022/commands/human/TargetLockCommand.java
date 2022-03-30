package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.LimelightWaitForTarget;
import com.team2357.frc2022.commands.turret.TurretTrackCommand;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.LimelightTargetingPipelineCommand;
import com.team2357.lib.subsystems.LimelightSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TargetLockCommand extends SequentialCommandGroup {
    public TargetLockCommand() {
        addRequirements(TurretSubsystem.getInstance());

        // Set the limelight pipeline
        addCommands(new LimelightTargetingPipelineCommand(true));

        // Wait for us to have a target
        addCommands(new LimelightWaitForTarget());

        // Start turret tracking
        addCommands(new TurretTrackCommand());
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        TurretSubsystem.getInstance().stop();
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
