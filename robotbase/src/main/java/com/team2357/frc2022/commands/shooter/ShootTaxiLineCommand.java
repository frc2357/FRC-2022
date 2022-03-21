package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShootTaxiLineCommand extends CommandLoggerBase {
    public ShootTaxiLineCommand() {
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

    @Override
    public void end(boolean interrupted) {
        ShooterSubsystem.getInstance().stop();
    }

}
