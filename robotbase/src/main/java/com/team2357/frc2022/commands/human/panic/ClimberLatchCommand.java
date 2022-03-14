package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberLatchCommand extends CommandLoggerBase {
    public ClimberLatchCommand() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem climber = ClimberSubsystem.getInstance();
        climber.setLatch(!climber.isHookOpen());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
