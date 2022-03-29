package com.team2357.frc2022.util;

import java.util.List;

import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AvailableTrajectories {
    public static SequentialCommandGroup exampleTrajectory = null;
    public static SequentialCommandGroup exampleRecordPathTrajectory = null;

    public static void generateTrajectories() {
        exampleTrajectory = createExampleTrajectory();
        exampleRecordPathTrajectory = createExampleRecordPathTrajectory();
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
/**
 * List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
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
 */
    /**
     * List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
new Pose2d(0.004063463641163216, 0.024533975318402963, Rotation2d.fromDegrees(80.595703125)),
new Pose2d(0.04287061095982764, 0.2590978690223007, Rotation2d.fromDegrees(80.15625000000001)),
new Pose2d(0.20969684536835315, 0.515388460581951, Rotation2d.fromDegrees(27.55371093750001)),
new Pose2d(0.5613972977805828, 0.6986943684283483, Rotation2d.fromDegrees(27.246093750000007)),
new Pose2d(0.9919684974162437, 1.0059468966705691, Rotation2d.fromDegrees(62.8857421875)),
new Pose2d(0.7274470585650253, 1.4033742063733234, Rotation2d.fromDegrees(145.6787109375)),
new Pose2d(0.13358964613420066, 1.8047941077896044, Rotation2d.fromDegrees(144.4482421875)),
new Pose2d(-0.23374483040675678, 2.3415553463235357, Rotation2d.fromDegrees(94.83398437500001)),
new Pose2d(-0.10053744639511768, 2.916711682050459, Rotation2d.fromDegrees(75.0146484375)),
     */


     /**
      * new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
            new Pose2d(0.005138387418834883, 2.2083707112308348E-4, Rotation2d.fromDegrees(2.4609375000000004)),
            new Pose2d(0.15288722766480822, 0.0069894771330836535, Rotation2d.fromDegrees(2.7685546875000004)),
            new Pose2d(0.507776523613686, 0.027627015045378393, Rotation2d.fromDegrees(4.21875)),
            new Pose2d(0.8999156245081863, 0.0654740166046273, Rotation2d.fromDegrees(6.723632812500001)),
            new Pose2d(1.2990866064155986, 0.12095506925746803, Rotation2d.fromDegrees(9.052734375000002)),
            new Pose2d(1.5115189031964178, 0.15741951993424935, Rotation2d.fromDegrees(90.107421875))),
            
      */

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