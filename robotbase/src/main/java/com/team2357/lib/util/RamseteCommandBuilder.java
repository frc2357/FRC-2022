package com.team2357.lib.util;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;

public class RamseteCommandBuilder {
    Trajectory m_trajectory = null;
    Supplier<Pose2d> m_pose = null;
    RamseteController m_ramseteController = null;
    SimpleMotorFeedforward m_feedForward = null;
    DifferentialDriveKinematics m_kinematics = null;
    Supplier<DifferentialDriveWheelSpeeds> m_wheelSpeeds = null;
    PIDController m_leftPIDController = null;
    PIDController m_righController = null;
    BiConsumer<Double, Double> m_outputVolts = null;

    RamseteCommandBuilder(){
        
    }
}
