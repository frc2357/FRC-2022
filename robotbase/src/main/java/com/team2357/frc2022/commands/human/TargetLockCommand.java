package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class TargetLockCommand extends CommandLoggerBase {
    private long m_pipelineSwitchMillis = 0;

    public boolean isWaitingOnPipeline() {
        return m_pipelineSwitchMillis > 0;
    }

    public boolean hasTargetAcquired() {
        return LimelightSubsystem.getInstance().validTargetExists();
    }

    public boolean isTurretFlipping() {
        return TurretSubsystem.getInstance().isRotatingToPosition();
    }

    @Override
    public void initialize() {
        LimelightSubsystem.getInstance().setTargetingPipelineActive();
        m_pipelineSwitchMillis = System.currentTimeMillis() + Constants.LIMELIGHT.m_pipelineSwitchMillis;
    }

    @Override
    public void execute() {
        if (isWaitingOnPipeline()) {
            long now = System.currentTimeMillis();
            if (now > m_pipelineSwitchMillis) {
                m_pipelineSwitchMillis = 0;
                TurretSubsystem.getInstance().trackTarget();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return !isWaitingOnPipeline() && !TurretSubsystem.getInstance().isTracking();
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stop();
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
