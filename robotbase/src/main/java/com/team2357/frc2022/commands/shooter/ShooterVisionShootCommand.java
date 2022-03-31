package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShooterVisionShootCommand extends CommandLoggerBase {
    public ShooterVisionShootCommand() {
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().startVisionShooting();
    }

    @Override
    public void end(boolean interrupted) {
        ShooterSubsystem.getInstance().stop();
    }
}
