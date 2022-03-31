package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederAdvanceCommand extends CommandLoggerBase {
    public FeederAdvanceCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        FeederSubsystem.getInstance().advance();
    }

    @Override
    public boolean isFinished() {
        return SensorSubsystem.getInstance().isCargoInFeeder();
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }
}
