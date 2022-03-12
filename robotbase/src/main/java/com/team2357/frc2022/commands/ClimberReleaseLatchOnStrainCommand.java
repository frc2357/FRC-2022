package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberReleaseLatchOnStrainCommand extends CommandLoggerBase{
    private ClimberSubsystem m_climbSub;
    private boolean m_isFinished;
    private int m_timeToBalanceAmps;

    public ClimberReleaseLatchOnStrainCommand(ClimberSubsystem climbSub, int timeToBalanceAmps) {
        m_climbSub = climbSub;
        m_isFinished = false;
        m_timeToBalanceAmps = timeToBalanceAmps;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_timeToBalanceAmps += System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if(m_climbSub.isClimberGripped() && m_timeToBalanceAmps < System.currentTimeMillis()) {
            m_climbSub.setHookPivot(false);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
