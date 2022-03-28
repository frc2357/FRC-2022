package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class SensorClearCommand extends CommandLoggerBase {
    public SensorClearCommand() {
        
    }

    @Override
    public void initialize() {
        SensorSubsystem.getInstance().clear();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
