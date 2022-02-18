package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretRotateDurationCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSub;
    private boolean m_isFinished = false;
    private long m_expiration = 0;
    private double m_speed = 0;

    public TurretRotateDurationCommand(TurretSubsystem turretSub, double speed, double durationSeconds) {
        m_turretSub = turretSub;
        m_speed = speed;
        m_expiration = System.currentTimeMillis() + (int) (durationSeconds * 1000);
        addRequirements(m_turretSub);

    }

    @Override
    public void initialize() {
        m_turretSub.setTurretSpeed(m_speed);
    }

    @Override
    public void execute() {
        if (System.currentTimeMillis() > m_expiration) {
            m_isFinished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_turretSub.setTurretSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
