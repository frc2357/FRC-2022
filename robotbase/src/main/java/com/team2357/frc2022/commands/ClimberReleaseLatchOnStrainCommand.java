package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberReleaseLatchOnStrainCommand extends CommandLoggerBase{
    private boolean m_isFinished;

    public ClimberReleaseLatchOnStrainCommand() {
        m_isFinished = false;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void execute() {
        if(ClimberSubsystem.getInstance().isClimberGripped()) {
            ClimberSubsystem.getInstance().setLatch(false);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
