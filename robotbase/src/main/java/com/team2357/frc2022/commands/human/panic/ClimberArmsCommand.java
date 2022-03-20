package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberArmsCommand extends CommandLoggerBase {
    public ClimberArmsCommand() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem climber = ClimberSubsystem.getInstance();
        climber.setClimberUpright(!climber.isClimberUpright());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
