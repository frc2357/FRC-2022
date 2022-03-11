package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberSetPivotCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;
    private boolean m_setUpright;

    ClimberSetPivotCommand(ClimberSubsystem climbSub, boolean setUpright) {
        m_setUpright = setUpright;
        m_climbSub = climbSub;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_climbSub.setClimberUpright(m_setUpright);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
