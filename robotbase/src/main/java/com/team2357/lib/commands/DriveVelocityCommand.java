package com.team2357.lib.commands;

import com.team2357.lib.controllers.DriverControls;
import com.team2357.lib.subsystems.drive.SkidSteerDriveSubsystem;

public class DriveVelocityCommand extends CommandLoggerBase {
    private SkidSteerDriveSubsystem m_driveSub;
    private DriverControls m_driverController;

    public DriveVelocityCommand(SkidSteerDriveSubsystem driveSub, DriverControls driverController) {
        m_driveSub = driveSub;
        m_driverController = driverController;
        addRequirements(driveSub);
    }

    @Override
    public void execute() {
        m_driveSub.driveVelocity(m_driverController.getSpeed(), m_driverController.getTurn());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
