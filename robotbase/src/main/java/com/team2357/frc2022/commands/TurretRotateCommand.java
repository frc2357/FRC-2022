package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretRotateCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSub;
    private double m_speed;

    /**
     * 
     * @param turretSub The {@Link TurretSubsystem}
     * @param speed     Speed between -1.0 and 1.0
     */
    public TurretRotateCommand(TurretSubsystem turretSub, double speed) {
        m_turretSub = turretSub;
        m_speed = speed;

        addRequirements(turretSub);
    }

    @Override
    public void initialize() {
        m_turretSub.setTurretSpeed(m_speed, true);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_turretSub.setTurretSpeed(0, true);
    }
}
