package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederAdvanceCommand extends CommandLoggerBase {
    private boolean m_lastInFeeder = false;

    public FeederAdvanceCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        m_lastInFeeder = SensorSubsystem.getInstance().isCargoInFeeder();
    }

    @Override
    public void execute() {
        SensorSubsystem sensors = SensorSubsystem.getInstance();
        boolean inFeeder = sensors.isCargoInFeeder();
        boolean inIndex = sensors.isCargoInIndex();

        if (!inFeeder && inIndex) {
            FeederSubsystem.getInstance().advance();
        } else {
            if (inFeeder && !m_lastInFeeder) {
                new FeederExtraAdvanceCommand().schedule();
            }
            FeederSubsystem.getInstance().stop();
        }

        System.out.println("Executing feeder command");

        m_lastInFeeder = inFeeder;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }
}
