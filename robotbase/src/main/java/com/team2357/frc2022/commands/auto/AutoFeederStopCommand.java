package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoFeederStopCommand extends CommandLoggerBase{
    public AutoFeederStopCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        FeederSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
