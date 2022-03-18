package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class FireCommand extends CommandLoggerBase {

    private long m_pipelineSwitchMillis = 0;

    public boolean isWaitingOnPipeline() {
        return m_pipelineSwitchMillis > 0;
    }

    public boolean hasTargetAcquired() {
        return LimelightSubsystem.getInstance().validTargetExists();
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

        if(ShooterSubsystem.getInstance().atTargetSpeed()) {
            FeederSubsystem.getInstance().start();
        } else {
            FeederSubsystem.getInstance().stop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        ShooterSubsystem.getInstance().stop();
        FeederSubsystem.getInstance().stop();
        LimelightSubsystem.getInstance().setHumanPipelineActive();
    }
}
