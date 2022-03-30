package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class WaitForFeederSensorCommand extends CommandLoggerBase {
    private boolean m_desiredState;

    public WaitForFeederSensorCommand(boolean desiredState) {
        m_desiredState = desiredState;
    }

    @Override
    public boolean isFinished() {
        return SensorSubsystem.getInstance().isCargoInFeeder() == m_desiredState;
    }
}
