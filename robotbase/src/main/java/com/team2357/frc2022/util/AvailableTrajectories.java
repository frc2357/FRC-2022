package com.team2357.frc2022.util;

import java.util.List;

import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AvailableTrajectories {
    public static SequentialCommandGroup exampleTrajectory = null;
    public static SequentialCommandGroup exampleRecordPathTrajectory = null;
    public static SequentialCommandGroup leaveTarmacTrajectory = null;
    public static SequentialCommandGroup travelToThirdCargoTrajectory = null;

    public static void generateTrajectories() {
        exampleTrajectory = createExampleTrajectory();
        exampleRecordPathTrajectory = createExampleRecordPathTrajectory();
        leaveTarmacTrajectory = createLeaveTarmacTrajectory();
        travelToThirdCargoTrajectory = createTravelToThirdCargoTrajectory();
    }

    private static SequentialCommandGroup createExampleTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(),
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 0.3), new Translation2d(2, -0.3)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)), false, true);
    }

    private static SequentialCommandGroup createExampleRecordPathTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(),List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
        new Pose2d(0.012195453405645025, -1.8901838807378317E-6, Rotation2d.fromDegrees(0.0)),
        new Pose2d(0.173697545532843, -2.0990006162902373E-5, Rotation2d.fromDegrees(0.0)),
        new Pose2d(0.5976884276133373, -0.06595781469014239, Rotation2d.fromDegrees(-30.849609375)),
        new Pose2d(0.9299569878347765, -0.3704943275575454, Rotation2d.fromDegrees(-44.208984375)),
        new Pose2d(1.3588293482304472, -0.7731543346116618, Rotation2d.fromDegrees(-31.025390625)),
        new Pose2d(1.7672559181468923, -0.5581337045202731, Rotation2d.fromDegrees(81.12304687500001)),
        new Pose2d(1.8516981992006087, 0.04452195822728959, Rotation2d.fromDegrees(81.9580078125)),
        new Pose2d(1.9765518341234347, 0.6303516511838099, Rotation2d.fromDegrees(62.8857421875)),
        new Pose2d(2.4403234462286174, 0.8288020947695366, Rotation2d.fromDegrees(-27.158203125000004)),
        new Pose2d(2.919374962567724, 0.4728429080958283, Rotation2d.fromDegrees(-36.1669921875)),
        new Pose2d(3.431510890857734, 0.2802894687801234, Rotation2d.fromDegrees(-0.9667968750000001)),
        new Pose2d(3.451430118783809, 0.2801714314001176, Rotation2d.fromDegrees(0.0))),
                   false, true);
    }

    private static SequentialCommandGroup createLeaveTarmacTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(), 
        List.of(new Pose2d(0,0,Rotation2d.fromDegrees(0))), false, true);
    }

    private static SequentialCommandGroup createTravelToThirdCargoTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(), 
        List.of(new Pose2d(0,0,Rotation2d.fromDegrees(0))), false, true);
    }
}