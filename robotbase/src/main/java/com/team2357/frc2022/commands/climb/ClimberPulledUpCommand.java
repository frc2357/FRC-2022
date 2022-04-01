package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberPulledUpCommand extends CommandLoggerBase {
    public ClimberPulledUpCommand() {
        
    }

    @Override
    public boolean isFinished() {
       return ClimberSubsystem.getInstance().isClimberPulledUp();
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().stopClimberMotors();
        ClimberSubsystem.getInstance().resetClimberPulledUpStates();
    }
}
