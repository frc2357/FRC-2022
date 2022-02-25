package com.team2357.frc2022.commands;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class FailsafeCommand extends CommandLoggerBase{
    private boolean m_failsafeActive;
    private ClosedLoopSubsystem m_subsystem;
    
    public FailsafeCommand(boolean failsafeActive, ClosedLoopSubsystem subsystem) {
        m_failsafeActive = failsafeActive;
        m_subsystems = subsystems;
    }

    @Override
    public void initialize() {
        super.initialize();

        m_subsystem.setClosedLoopEnabled(m_failsafeActive);

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
