package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretZeroCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSub;
    private double position = Constants.TURRET.MOTOR_ROTATIONS_ZERO_INCREMENT;
    private boolean m_toggleDirection = true;
    private boolean m_isFinished = false;

    public TurretZeroCommand(TurretSubsystem turretSub) {
        m_turretSub = turretSub;
    }

    @Override
    public void initialize() {
        m_turretSub.resetHeading();
    }

    @Override
    public void execute() {

        m_isFinished = m_turretSub.isOnZero();
        if (!m_isFinished && m_turretSub.atSetPoint()) {

            m_turretSub.setTurretPosition(m_toggleDirection ? position : -1 * position);
            m_toggleDirection = !m_toggleDirection;

            position += Constants.TURRET.MOTOR_ROTATIONS_ZERO_INCREMENT;
            m_toggleDirection = !m_toggleDirection;

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
