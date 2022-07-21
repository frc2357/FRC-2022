package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberTogglePivotCommand extends CommandLoggerBase {

    public ClimberTogglePivotCommand() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().setClimberUpright(!ClimberSubsystem.getInstance().isClimberUpright()) ;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
