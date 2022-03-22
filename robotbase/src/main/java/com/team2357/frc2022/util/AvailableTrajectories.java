package com.team2357.frc2022.util;

import java.util.List;

import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AvailableTrajectories {
    public static SequentialCommandGroup exampleTrajectory = null;
    public static SequentialCommandGroup exampleRecordPathTrajectory = null;
    public static SequentialCommandGroup leaveTarmacTrajectory = null;
    public static SequentialCommandGroup threeBallTrajectory = null;

    public static void generateTrajectories() {
        exampleTrajectory = createExampleTrajectory();
        exampleRecordPathTrajectory = createExampleRecordPathTrajectory();
        leaveTarmacTrajectory = createLeaveTarmacTrajectory();
        threeBallTrajectory = createThreeBallTrajectory();
    }

    private static SequentialCommandGroup createExampleTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconTrajectoryDriveSubsystem.getInstance(),
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 0.3), new Translation2d(2, -0.3)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)), false, true);
    }

    private static SequentialCommandGroup createExampleRecordPathTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconTrajectoryDriveSubsystem.getInstance(), List.of(
            new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
            new Pose2d(0.005138387418834883, 2.2083707112308348E-4, Rotation2d.fromDegrees(2.4609375000000004)),
            new Pose2d(0.15288722766480822, 0.0069894771330836535, Rotation2d.fromDegrees(2.7685546875000004)),
            new Pose2d(0.507776523613686, 0.027627015045378393, Rotation2d.fromDegrees(4.21875)),
            new Pose2d(0.8999156245081863, 0.0654740166046273, Rotation2d.fromDegrees(6.723632812500001)),
            new Pose2d(1.2990866064155986, 0.12095506925746803, Rotation2d.fromDegrees(9.052734375000002)),
            new Pose2d(1.5115189031964178, 0.15741951993424935, Rotation2d.fromDegrees(90.107421875))),
            false, true);
    }

    private static SequentialCommandGroup createLeaveTarmacTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconTrajectoryDriveSubsystem.getInstance(),  // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(0.5715, 0)),
        new Pose2d(1.143, 0, new Rotation2d(0)), false, true);
    }

    public static SequentialCommandGroup createThreeBallTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconTrajectoryDriveSubsystem.getInstance(), List.of(
            new Pose2d(0, 0, Rotation2d.fromDegrees(0))),
            false, true);
    }

    /**
     * new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
     * List.of(
     * new Translation2d(0.02727356101100583, 1.8330910716431498E-4),
     * new Translation2d(0.19589856046943674, 0.0017427098382093273),
     * new Translation2d(0.5150563906201355, 0.0059500596370823545),
     * new Translation2d(1.0735812602713914, 0.02063933663666991),
     * new Translation2d(1.6521638218915888, 0.04931202607473192),
     * new Translation2d(2.255182703356049, 0.092197793931433),
     * ),
     * new Pose2d(2.722339254099699, 0.13494852925999057,
     * Rotation2d.fromDegrees(5.317382812499999)),
     */

    /**
     * new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
     * List.of(
     * new Translation2d(0.020213598963017294, 0.0013824500897851128),
     * new Translation2d(0.3033002618649585, 0.021590972656284534),
     * new Translation2d(0.8269769641510211, 0.03231476153794852),
     * new Translation2d(1.3192720157189055, -0.20566057203347884),
     * new Translation2d(1.6744061861184663, -0.44779890286123686),
     * new Translation2d(2.0813219070965494, -0.642113046017016),
     * new Translation2d(2.521225969229714, -0.2785972916256752),
     * new Translation2d(2.7419868924007513, 0.261701249595908),
     * new Translation2d(2.977602712031896, 0.7454956963066651),
     * new Translation2d(3.481803951904155, 1.0178105689068686),
     * new Translation2d(3.999435658178106, 0.8381796113567118),
     * new Translation2d(4.63490028890158, 0.5924014163711115),
     * new Translation2d(5.126439609084067, 0.6201233475354243)),
     * new Pose2d(5.144337947741824, 0.622671972017859,
     * Rotation2d.fromDegrees(0.0)),
     */

    /**
     * List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0))),
     * Loop time of 0.02s overrun
     * new Pose2d(0.005138387418834883, 2.2083707112308348E-4,
     * Rotation2d.fromDegrees(2.4609375000000004))),
     * new Pose2d(0.15288722766480822, 0.0069894771330836535,
     * Rotation2d.fromDegrees(2.7685546875000004))),
     * new Pose2d(0.507776523613686, 0.027627015045378393,
     * Rotation2d.fromDegrees(4.21875))),
     * new Pose2d(0.8999156245081863, 0.0654740166046273,
     * Rotation2d.fromDegrees(6.723632812500001))),
     * new Pose2d(1.2990866064155986, 0.12095506925746803,
     * Rotation2d.fromDegrees(9.052734375000002))),
     * new Pose2d(1.5115189031964178, 0.15741951993424935,
     * Rotation2d.fromDegrees(10.107421875))),
     */
}