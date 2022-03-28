package com.team2357.frc2022.commands.auto;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class AutoDriveCommand extends CommandLoggerBase {
    double m_timeMillis;
    double m_timeTotal;
    double m_turn;
    double m_speed;

    public AutoDriveCommand (double timeMillis, double speed, double turn) {

        addRequirements(FalconTrajectoryDriveSubsystem.getInstance());
        m_timeMillis = timeMillis;
        m_turn = turn;
        m_speed = speed;
    }

    @Override
    public void initialize() {
        FalconTrajectoryDriveSubsystem.getInstance().driveVelocity(m_speed, m_turn);
        m_timeTotal = System.currentTimeMillis() + m_timeMillis;
    }

    @Override
    public boolean isFinished() {
        return m_timeTotal < System.currentTimeMillis();
    }

    @Override
    public void end(boolean interrupted) {
        FalconTrajectoryDriveSubsystem.getInstance().driveVelocity(0.0, 0.0);
    }


}
