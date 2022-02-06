package com.team2357.frc2022;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.team2357.frc2022.util.TrajectoryUtil;

public class TrajectoryTest {
    @Test
    public void testTrajectory() {
        Pose2d startPose = new Pose2d(0,0,new Rotation2d(70));
        Pose2d endPose = new Pose2d(3,0,new Rotation2d(0));

       TrajectoryUtil.generateWaypoints(startPose, endPose);
    }
}
