package com.team2357.frc2022.commands.auto.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TaxiStartShotCommand extends CommandLoggerBase {
    public TaxiStartShotCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().shootTaxiLine();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
