package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberTogglePivotCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;

    public ClimberTogglePivotCommand(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_climbSub.setClimberUpright(!m_climbSub.isClimberUpright()) ;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
