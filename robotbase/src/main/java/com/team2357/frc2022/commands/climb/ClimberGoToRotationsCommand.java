package com.team2357.frc2022.commands.climb;

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
        //System.out.println("Starting go to rotations");
        System.out.println("Start time: " + (System.currentTimeMillis() * 1000));
    }

    @Override
    public void execute() {
        //System.out.println("Running go to rotations");
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().stopClimberMotors();
        //System.out.println("Ening go to rotations");
        System.out.println("End time: " + (System.currentTimeMillis() * 1000));
    }

    @Override
    public boolean isFinished() {
        return ClimberSubsystem.getInstance().handleClimberRotations();
    }
}
