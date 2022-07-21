package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberSetPivotCommand extends CommandLoggerBase {
    private boolean m_setUpright;

    ClimberSetPivotCommand(boolean setUpright) {
        m_setUpright = setUpright;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().setClimberUpright(m_setUpright);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
