package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberGoToRotationsCommand extends CommandLoggerBase {
    private double m_rotations;

    public ClimberGoToRotationsCommand(double rotations) {
        m_rotations = rotations;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().setClimberRotations(m_rotations);
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().stopClimberMotors();
    }

    @Override
    public boolean isFinished() {
        return ClimberSubsystem.getInstance().isClimberAtRotations();
    }
}
