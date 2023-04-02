package com.team2357.frc2022.commands;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.PDHSubsystem;

public class CameraLightCommand extends CommandLoggerBase {
    @Override
    public void initialize() {
        PDHSubsystem.getInstance().setSwitchableChannel(true);
    }

    @Override
    public void end(boolean interrupted) {
        PDHSubsystem.getInstance().setSwitchableChannel(false);
    }
}
