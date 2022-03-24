package com.team2357.lib.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class FalconTrajectoryDriveSubsystem extends SingleSpeedFalconDriveSubsystem {
    private static FalconTrajectoryDriveSubsystem instance = null;

    public static FalconTrajectoryDriveSubsystem getInstance() {
        return instance;
    }

    public double m_distancePerPulse;

    // The gyro sensor
    private PigeonIMU m_gyro;
    private boolean m_isGyroReversed;

    // Odometry class for tracking robot pose
    private final DifferentialDriveOdometry m_odometry;

    // The left-side drive encoder
    private final Encoder m_leftEncoder;

    // The right-side drive encoder
    private final Encoder m_rightEncoder;

    private Configuration m_config;

    public static class Configuration extends SkidSteerDriveSubsystem.Configuration {
        /**
         * Whether or not the gyro is reversed Value: boolean
         */
        public boolean m_isGyroReversed = false;

        // The deadband of output percentage on the motor controller
        public double m_falconOutputDeadband = 0.001;

        // Turn sensitivity multiplier for velocity control
        public double m_turnSensitivity = 0.0;

        // Velocity PID constants
        public int m_gainsSlot = 0;
        public double m_velF = 0.0;
        public double m_velP = 0.0;
        public double m_velI = 0.0;
        public double m_velD = 0.0;

        public double m_nominalOutput = 0;
        public double m_peakOutput = 1;

        /**
         * This represents the native max velocity for the drive sensor over 100 ms
         * It should follow the following formula
         * maxRpm * encoderCpr / 600
         * 
         */
        public double m_sensorUnitsMaxVelocity = 0;

        public int m_timeoutMs = 0;
    }

    /**
     * 
     * @param leftTalonMaster
     * @param leftTalonSlaves
     * @param rightTalonMaster
     * @param rightTalonSlaves
     * @param leftEncoder
     * @param rightEncoder
     * @param gyro
     * @param encoderDistancePerPulse
     */
    public FalconTrajectoryDriveSubsystem(WPI_TalonFX leftFalconMaster, WPI_TalonFX[] leftFalconSlaves,
            WPI_TalonFX rightFalconMaster, WPI_TalonFX[] rightFalconSlaves, PigeonIMU gyro,
            double encoderDistancePerPulse, int leftEncoderChannelA,
            int leftEncoderChannelB, int rightEncoderChannelA, int rightEncoderChannelB) {
        super(leftFalconMaster, leftFalconSlaves, rightFalconMaster, rightFalconSlaves);
        m_distancePerPulse = encoderDistancePerPulse;

        instance = this;

        m_leftEncoder = new Encoder(
                leftEncoderChannelA,
                leftEncoderChannelB);
        m_rightEncoder = new Encoder(
                rightEncoderChannelA,
                rightEncoderChannelB);
        m_leftEncoder.setDistancePerPulse(encoderDistancePerPulse);
        m_rightEncoder.setDistancePerPulse(encoderDistancePerPulse);

        SupplyCurrentLimitConfiguration currentConfig = new SupplyCurrentLimitConfiguration(true, 10, 10, 0);
        super.m_leftFalconMaster.configSupplyCurrentLimit(currentConfig);
        super.m_rightFalconMaster.configSupplyCurrentLimit(currentConfig);

        for (WPI_TalonFX slave : leftFalconSlaves) {
            slave.follow(m_leftFalconMaster, FollowerType.AuxOutput1);
            slave.configSupplyCurrentLimit(currentConfig);
        }
        for (WPI_TalonFX slave : rightFalconSlaves) {
            slave.follow(m_rightFalconMaster, FollowerType.AuxOutput1);
            slave.configSupplyCurrentLimit(currentConfig);
        }

        resetEncoders();
        m_gyro = gyro;
        m_gyro.configFactoryDefault();
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getDistance(),
                m_rightEncoder.getDistance());
         //System.out.print("Left encoder distance: " + m_leftEncoder.getDistance());
         //System.out.println(" Right encoder distance: " +
        // m_rightEncoder.getDistance());
        // System.out.println(getHeading());
        //System.out.println("Pose: " + getPose().toString());
        // System.out.println(m_leftControllers.get());
        // System.out.println(m_rightControllers.get());

    }

    public void configure(Configuration config) {
        super.configure(config);

        m_config = config;

        m_isGyroReversed = m_config.m_isGyroReversed;
        m_leftEncoder.setReverseDirection(!m_config.m_isRightInverted);
        m_rightEncoder.setReverseDirection(m_config.m_isRightInverted);

        // Velocity PID setup

        /* Config neutral deadband to be the smallest possible */
        super.m_leftFalconMaster.configNeutralDeadband(m_config.m_falconOutputDeadband);

        super.m_rightFalconMaster.configNeutralDeadband(m_config.m_falconOutputDeadband);

        /* Config sensor used for Primary PID [Velocity] */
        super.m_leftFalconMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                m_config.m_gainsSlot, m_config.m_timeoutMs);

        super.m_rightFalconMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                m_config.m_gainsSlot, m_config.m_timeoutMs);

        configFalconPID(super.m_leftFalconMaster);
        configFalconPID(super.m_rightFalconMaster);
    }

    private void configFalconPID(WPI_TalonFX falcon) {
        falcon.configNominalOutputForward(m_config.m_nominalOutput, m_config.m_timeoutMs);
        falcon.configNominalOutputReverse(-m_config.m_nominalOutput, m_config.m_timeoutMs);
        falcon.configPeakOutputForward(m_config.m_peakOutput, m_config.m_timeoutMs);
        falcon.configPeakOutputReverse(-m_config.m_peakOutput, m_config.m_timeoutMs);

        falcon.config_kF(m_config.m_gainsSlot, m_config.m_velF, m_config.m_timeoutMs);
        falcon.config_kP(m_config.m_gainsSlot, m_config.m_velP, m_config.m_timeoutMs);
        falcon.config_kI(m_config.m_gainsSlot, m_config.m_velI, m_config.m_timeoutMs);
        falcon.config_kD(m_config.m_gainsSlot, m_config.m_velD, m_config.m_timeoutMs);
    }

    @Override
    public void driveVelocity(double speed, double turn) {
        double speedSensorUnits = speed * m_config.m_sensorUnitsMaxVelocity;
        double turnSensorUnits = turn * m_config.m_sensorUnitsMaxVelocity;
        double leftSensorUnitsPer100Ms = speedSensorUnits - (turnSensorUnits * m_config.m_turnSensitivity);
        double rightSensorUnitsPer100Ms = speedSensorUnits + (turnSensorUnits * m_config.m_turnSensitivity);
        this.setVelocity(leftSensorUnitsPer100Ms, rightSensorUnitsPer100Ms);
    }

    protected void setVelocity(double leftSensorUnitsPer100Ms, double rightSensorUnitsPer100Ms) {
        m_leftFalconMaster.set(TalonFXControlMode.Velocity, leftSensorUnitsPer100Ms);
        m_rightFalconMaster.set(TalonFXControlMode.Velocity, -rightSensorUnitsPer100Ms);
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    /**
     * Returns the current wheel speeds of the robot.
     *
     * @return The current wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getVelocityLeftEncoder(), getVelocityRightEncoder());
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     *
     * @param leftVolts  the commanded left output
     * @param rightVolts the commanded right output
     */
    public void setTankDriveVolts(double leftVolts, double rightVolts) {
        // negative if motors are inverted.
        super.m_leftControllers.setVoltage(leftVolts);
        super.m_rightControllers.setVoltage(rightVolts);
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        m_leftEncoder.reset();
        m_rightEncoder.reset();
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance() {
        return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
    }

    public double getVelocityLeftEncoder() {
        return m_leftEncoder.getRate();
    }

    public double getVelocityRightEncoder() {
        return m_rightEncoder.getRate();
    }

    /**
     * Gets the left drive encoder.
     *
     * @return the left drive encoder
     */
    public Encoder getLeftEncoder() {
        return m_leftEncoder;
    }

    /**
     * Gets the right drive encoder.
     *
     * @return the right drive encoder
     */
    public Encoder getRightEncoder() {
        return m_rightEncoder;
    }

    /**
     * Zeroes the heading of the robot.
     */
    public void zeroHeading() {
        m_gyro.setYaw(0);
        m_gyro.setAccumZAngle(0);
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from 180 to 180
     */
    public double getHeading() {
        double[] ypr = getYawPitchAndRoll();
        return Math.IEEEremainder(ypr[0], 360) * (m_isGyroReversed ? -1.0 : 1.0);
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        double[] ypr = getYawPitchAndRoll();
        return ypr[1] * (m_isGyroReversed ? -1.0 : 1.0);
    }

    public double[] getYawPitchAndRoll() {
        double[] ypr = new double[3];

        m_gyro.getYawPitchRoll(ypr);

        return ypr;
    }
}