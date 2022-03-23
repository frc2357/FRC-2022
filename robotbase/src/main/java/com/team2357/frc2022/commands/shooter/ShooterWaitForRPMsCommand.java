package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShooterWaitForRPMsCommand extends CommandLoggerBase {
    public boolean isFinished() {
        return ShooterSubsystem.getInstance().isAtTargetSpeed();
    }
}
