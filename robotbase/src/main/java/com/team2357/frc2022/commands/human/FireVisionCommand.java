package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class FireVisionCommand extends CommandLoggerBase {

    private long m_pipelineSwitchMillis = 0;

    public boolean isWaitingOnPipeline() {
        return m_pipelineSwitchMillis > 0;
    }

    public boolean hasTargetAcquired() {
        return LimelightSubsystem.getInstance().validTargetExists();
    }

    public FireVisionCommand() {
        addRequirements(ShooterSubsystem.getInstance());
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
                ShooterSubsystem.getInstance().startVisionShooting();
            }
        }
        System.err.println("RSHOOTING");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        ShooterSubsystem.getInstance().stop();
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
