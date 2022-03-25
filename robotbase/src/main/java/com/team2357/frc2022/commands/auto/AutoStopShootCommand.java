package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoStopShootCommand extends CommandLoggerBase{
    public AutoStopShootCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().setRPMBottom(0);
        ShooterSubsystem.getInstance().setRPMTop(0);
        ShooterSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
