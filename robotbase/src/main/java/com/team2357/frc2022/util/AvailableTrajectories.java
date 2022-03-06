package com.team2357.frc2022.util;

import java.util.List;

import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AvailableTrajectories {
    public static SequentialCommandGroup exampleTrajectory = null;

    public static void generateTrajectories(FalconTrajectoryDriveSubsystem driveSub) {
        exampleTrajectory = createExampleTrajectory(driveSub);
    }

    private static SequentialCommandGroup createExampleTrajectory(FalconTrajectoryDriveSubsystem driveSub) {
        return TrajectoryUtil.createTrajectoryPathCommand(driveSub,
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)), false, true);
    }
}