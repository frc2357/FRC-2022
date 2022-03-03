package com.team2357.frc2022.commands;

import java.util.List;

import com.team2357.frc2022.Constants;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

public class ExampleTrajectoryCommand extends CommandLoggerBase{
    FalconTrajectoryDriveSubsystem m_driveSub;

    public ExampleTrajectoryCommand(FalconTrajectoryDriveSubsystem driveSub) {
        m_driveSub = driveSub;
    }

    public Command getRamsete() {
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                Constants.DRIVE.KS_VOLTS,
                Constants.DRIVE.KV_VOLTS_SECONDS_PER_METER,
                Constants.DRIVE.KA_VOLTS_SECONDS_SQUARED_PER_METER),
            Constants.DRIVE.DRIVE_KINEMATICS,
            Constants.DRIVE.MAX_VOLTAGE);

        // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(
            Constants.DRIVE.MAX_SPEED_METERS_PER_SECOND,
            Constants.DRIVE.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Constants.DRIVE.DRIVE_KINEMATICS)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint).setReversed(false);

        // An example trajectory to follow. All units in meters.
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config);

        RamseteCommand ramseteCommand = new RamseteCommand(
            exampleTrajectory,
            m_driveSub::getPose,
            new RamseteController(Constants.DRIVE.RAMSETE_B, Constants.DRIVE.RAMSETE_ZETA),
            new SimpleMotorFeedforward(
                Constants.DRIVE.KS_VOLTS,
                Constants.DRIVE.KV_VOLTS_SECONDS_PER_METER,
                Constants.DRIVE.KA_VOLTS_SECONDS_SQUARED_PER_METER),
            Constants.DRIVE.DRIVE_KINEMATICS,
            m_driveSub::getWheelSpeeds,
            new PIDController(Constants.DRIVE.P_DRIVE_VEL, 0, 0),
            new PIDController(Constants.DRIVE.P_DRIVE_VEL, 0, 0),
            // RamseteCommand passes volts to the callback
            m_driveSub::setTankDriveVolts,
            m_driveSub);

        // Reset odometry to the starting pose of the trajectory.
        m_driveSub.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> m_driveSub.setTankDriveVolts(0, 0));
    }
}
