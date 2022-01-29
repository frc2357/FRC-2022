package com.team2357.frc2022.commands;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.commands.DriveProportionalCommand;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;
import com.team2357.lib.subsystems.drive.SkidSteerDriveSubsystem;

public class DriveRunMotorCommand extends CommandLoggerBase { 
    SkidSteerDriveSubsystem m_driveSub;
    double m_speed;

    public DriveRunMotorCommand(SkidSteerDriveSubsystem driveSub, double speed) {
        m_driveSub = driveSub;
        m_speed = speed;
        addRequirements(m_driveSub);
    }

    @Override
    public void initialize() {
        m_driveSub.setSpeed(m_speed);
    }

    @Override
    public void end(boolean interrupt) {
        m_driveSub.setSpeed(0);
    }   

    @Override
    public boolean isFinished() {
        return false;
    }
}
