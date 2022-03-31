package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShootLowHubCommand extends CommandLoggerBase {

    public ShootLowHubCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().shootLowHub();
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
