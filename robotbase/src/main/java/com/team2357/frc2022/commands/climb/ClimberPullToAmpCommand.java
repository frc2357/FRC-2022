package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberPullToAmpCommand extends CommandLoggerBase {
    public ClimberPullToAmpCommand() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().retractToAmpLimit();
    }

    @Override
    public boolean isFinished() {
        return ClimberSubsystem.getInstance().isClimberGripped();
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().stopClimberMotors();
    }
}
