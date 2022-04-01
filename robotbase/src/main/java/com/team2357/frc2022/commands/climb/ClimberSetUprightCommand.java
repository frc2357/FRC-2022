package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberSetUprightCommand extends CommandLoggerBase {
    private boolean m_setUpright;

    ClimberSetUprightCommand(boolean setUpright) {
        m_setUpright = setUpright;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().setClimberUpright(!m_setUpright);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
