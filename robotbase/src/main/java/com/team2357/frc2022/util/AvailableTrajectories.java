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
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(),
                List.of(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
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
                List.of(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                        new Pose2d(0.017513853118839913, -4.738528291391344E-5,
                                Rotation2d.fromDegrees(-0.13183593750000003)),
                        new Pose2d(0.3417937931856386, 0.003688144413647491, Rotation2d.fromDegrees(1.494140625)),
                        new Pose2d(0.8130550100571042, 0.01402617100189901, Rotation2d.fromDegrees(1.0546875000000002)),
                        new Pose2d(0.9625838482837916, 0.016549502356075444,
                                Rotation2d.fromDegrees(0.30761718749999994))),
                false, true);
    }

    private static SequentialCommandGroup createTravelToThirdCargoTrajectory() {
        return new SequentialCommandGroup(createThreeBallTravelBackTrajectory(), createThreeBallAutoTravelToThirdTrajectory());
    }

    private static SequentialCommandGroup createThreeBallTravelBackTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(), List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
        new Pose2d(-0.319070674890391, 0.01856578744447176, Rotation2d.fromDegrees(-8.0419921875)),
        new Pose2d(-1.3102390255302991, 0.5075593681858235, Rotation2d.fromDegrees(-48.03222656249999))),
        true, true);
    }

    private static SequentialCommandGroup createThreeBallAutoTravelToThirdTrajectory() {
        return TrajectoryUtil.createTrajectoryPathCommand(FalconDriveSubsystem.getInstance(), List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
        new Pose2d(0.2650097089203349, -0.029321698868923312, Rotation2d.fromDegrees(-12.12890625)),
        new Pose2d(1.1462756519915793, -0.47026944957780487, Rotation2d.fromDegrees(-42.0556640625)),
        new Pose2d(1.6972924521141421, -1.2178663849396316, Rotation2d.fromDegrees(-62.666015625000014)),
        new Pose2d(1.8826092475307807, -1.6361663170719003, Rotation2d.fromDegrees(-67.63183593750001))),
        false, true);
    }

    /**
     * List.of( new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
new Pose2d(0.018174461220810654, -2.516541904604855E-4, Rotation2d.fromDegrees(-1.0986328125)),
new Pose2d(0.2650097089203349, -0.029321698868923312, Rotation2d.fromDegrees(-12.12890625)),
new Pose2d(0.7012095292668968, -0.17672744876278051, Rotation2d.fromDegrees(-24.78515625)),
new Pose2d(1.1462756519915793, -0.47026944957780487, Rotation2d.fromDegrees(-42.0556640625)),
new Pose2d(1.4802802466173917, -0.8493253871728673, Rotation2d.fromDegrees(-55.107421875)),
new Pose2d(1.6972924521141421, -1.2178663849396316, Rotation2d.fromDegrees(-62.666015625000014)),
new Pose2d(1.8256797791665182, -1.499193799012016, Rotation2d.fromDegrees(-67.1923828125)),
new Pose2d(1.8826092475307807, -1.6361663170719003, Rotation2d.fromDegrees(-67.63183593750001))),
     */
   
}