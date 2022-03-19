package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberReleaseLatchOnStrainCommand extends CommandLoggerBase {
    private boolean m_isFinished;
    private int m_timeToBalanceAmps;

    public ClimberReleaseLatchOnStrainCommand(int timeToBalanceAmps) {
        m_isFinished = false;
        m_timeToBalanceAmps = timeToBalanceAmps;
      //  addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        m_timeToBalanceAmps += System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if (ClimberSubsystem.getInstance().isClimberGripped() && m_timeToBalanceAmps < System.currentTimeMillis()) {
            ClimberSubsystem.getInstance().setLatch(true);
            m_isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
