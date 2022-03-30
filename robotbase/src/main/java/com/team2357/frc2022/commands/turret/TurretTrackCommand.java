package com.team2357.frc2022.commands.turret;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretTrackCommand extends CommandLoggerBase {
    public TurretTrackCommand() {
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        TurretSubsystem.getInstance().trackTarget();
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stop();
    }
}
