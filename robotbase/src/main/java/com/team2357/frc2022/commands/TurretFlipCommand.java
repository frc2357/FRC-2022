package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretFlipCommand extends CommandLoggerBase {
    public TurretFlipCommand (TurretSubsystem turretSub) {
        new TurretSetRotationCommand(turretSub, turretSub.calculateTurretFlipRotations()).schedule();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
