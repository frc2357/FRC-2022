package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberReleaseLatchOnStrainCommand extends CommandLoggerBase{
    private ClimberSubsystem m_climbSub;
    private boolean m_isFinished;

    public ClimberReleaseLatchOnStrainCommand(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;
        m_isFinished = false;
        addRequirements(m_climbSub);
    }

    @Override
    public void execute() {
        if(m_climbSub.isClimberGripped()) {
            m_climbSub.setLatch(false);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
