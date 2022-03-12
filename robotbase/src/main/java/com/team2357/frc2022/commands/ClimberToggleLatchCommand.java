package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to toggle dynamic hooks open and close
 * 
 * @category Climber
 */
public class ClimberToggleLatchCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;

    public ClimberToggleLatchCommand(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;
    }

    @Override
    public void initialize() {
        m_climbSub
                .setLatch(m_climbSub.isHookOpen() ? false
                        : true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
