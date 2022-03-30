package com.team2357.frc2022.commands.turret;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class WaitForTurretAccuracyCommand extends CommandLoggerBase {
    @Override
    public boolean isFinished() {
        return TurretSubsystem.getInstance().isAtTarget();
    }
}
