package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberGoToRotationsCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;
    private double m_rotations;

    public ClimberGoToRotationsCommand(ClimberSubsystem climbSub, double rotations) {
        climbSub = m_climbSub;
        m_rotations = rotations;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_climbSub.setClimberRotations(m_rotations);
    }

    @Override
    public void end(boolean interrupted) {
        m_climbSub.StopClimberMotors();
    }

    @Override
    public boolean isFinished() {
        return m_climbSub.isClimberAtRotations();
    }
}
