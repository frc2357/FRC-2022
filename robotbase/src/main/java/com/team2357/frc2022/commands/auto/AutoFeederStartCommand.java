package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoFeederStartCommand extends CommandLoggerBase {
    public AutoFeederStartCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        FeederSubsystem.getInstance().shoot();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
