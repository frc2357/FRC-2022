package com.team2357.frc2022.commands.auto.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoStartPosShotCommand extends CommandLoggerBase {
    public AutoStartPosShotCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().shootAutoStartPos();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
