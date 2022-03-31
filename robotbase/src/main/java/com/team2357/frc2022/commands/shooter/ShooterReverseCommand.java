package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShooterReverseCommand extends CommandLoggerBase {
    public ShooterReverseCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().reverse();
    }
    
    @Override
    public void end(boolean isInterrupted) {
        ShooterSubsystem.getInstance().stop();
    }
}

    
}
