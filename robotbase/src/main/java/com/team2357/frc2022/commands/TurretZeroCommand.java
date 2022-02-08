package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretZeroCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSub;
    private double rotationsToMake = Constants.TURRET.MOTOR_ROTATIONS_ZERO_INCREMENT;
    private boolean m_isFinished = false;

    public TurretZeroCommand(TurretSubsystem turretSub) {
        m_turretSub = turretSub;
    }

    @Override
    public void execute() {

        m_isFinished = m_turretSub.isOnZero();
        if (!m_isFinished) {
            // TODO: Add code to move turret
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
