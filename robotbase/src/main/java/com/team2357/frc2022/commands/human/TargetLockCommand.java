package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.frc2022.subsystems.util.VisionTargetSupplier;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class TargetLockCommand extends CommandLoggerBase {
    private long m_pipelineSwitchMillis = 0;

    public boolean isWaitingOnPipeline() {
        return m_pipelineSwitchMillis > 0;
    }

    public boolean hasTargetAcquired() {
        return LimelightSubsystem.getInstance().getCurrentTarget() != null;
    }

    public boolean isTurretFlipping() {
        return TurretSubsystem.getInstance().isRotatingToPosition();
    }

    @Override
    public void initialize() {
        VisionTargetSupplier targetSupplier = () -> {
            return LimelightSubsystem.getInstance().getCurrentTarget();
        };

        LimelightSubsystem.getInstance().setTargetingPipelineActive();
        m_pipelineSwitchMillis = System.currentTimeMillis() + Constants.LIMELIGHT.m_pipelineSwitchMillis;
        TurretSubsystem.getInstance().trackTarget(targetSupplier);
    }

    @Override
    public void execute() {
        if (isWaitingOnPipeline()) {
            long now = System.currentTimeMillis();
            if (now > m_pipelineSwitchMillis) {
                m_pipelineSwitchMillis = 0;
            }
        } else {
            LimelightSubsystem.getInstance().acquireTarget();
        }
    }

    @Override
    public boolean isFinished() {
        return !isWaitingOnPipeline() && !hasTargetAcquired() && !isTurretFlipping();
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stop();
        LimelightSubsystem.getInstance().clearTarget();
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
