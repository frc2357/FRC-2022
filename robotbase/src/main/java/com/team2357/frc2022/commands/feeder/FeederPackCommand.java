package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederPackCommand extends CommandLoggerBase {
    public FeederPackCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    public void initialize() {
        FeederSubsystem.getInstance().pack();
    }

    @Override
    public boolean isFinished() {
        // We've pushed the cargo below the feeder sensor.
        return !SensorSubsystem.getInstance().isCargoInFeeder();
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }
}
