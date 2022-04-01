package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoTurretRotateCommand extends CommandLoggerBase {
    double m_millis;
    double m_speed;
    double m_timeTotal;

    public AutoTurretRotateCommand(double millis, double speed) {
        m_millis = millis;
        m_speed = speed;
    }

    @Override
    public void initialize() {
        TurretSubsystem.getInstance().setTurretSpeed(m_speed, true, true);
        m_timeTotal = System.currentTimeMillis() + m_millis;
    }

    @Override
    public boolean isFinished() {
        return m_timeTotal < System.currentTimeMillis();
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().setTurretSpeed(0.0, true, true);
    }

}
