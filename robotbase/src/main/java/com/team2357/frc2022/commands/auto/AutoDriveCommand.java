package com.team2357.frc2022.commands.auto;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class AutoDriveCommand extends CommandLoggerBase {
    double m_timeMillis;
    double m_timeTotal;

    public AutoDriveCommand (double timeMillis) {

        addRequirements(FalconTrajectoryDriveSubsystem.getInstance());
        m_timeMillis = timeMillis;
    }

    @Override
    public void initialize() {
        FalconTrajectoryDriveSubsystem.getInstance().driveVelocity(0.1, 0.0);
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
