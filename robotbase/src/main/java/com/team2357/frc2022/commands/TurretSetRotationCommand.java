package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretSetRotationCommand extends CommandLoggerBase {
    TurretSubsystem m_turretSub;
    double m_rotation;

    public TurretSetRotationCommand(TurretSubsystem turretSub, double rotation) {
        m_turretSub = turretSub;
        m_rotation = rotation;
        addRequirements(m_turretSub);
    }

    @Override
    public void initialize() {
        m_turretSub.setTurretRotation(m_rotation);
    }

    @Override
    public boolean isFinished() {
        return m_turretSub.isAtTarget();
    }
}
