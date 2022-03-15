package com.team2357.frc2022.commands.human;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class TargetLockCommand extends CommandLoggerBase {
    @Override
    public void initialize() {
        System.out.println("TargetLockCommand init");
        LimelightSubsystem.getInstance().setTargetingPipelineActive();
    }

    @Override
    public void execute() {
        LimelightSubsystem limelight = LimelightSubsystem.getInstance();
        if (limelight.validTargetExists()) {
            System.out.println("(" + limelight.getTX() + "," + limelight.getTY() + ")");
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("TargetLockCommand end");
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
