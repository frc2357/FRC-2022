package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.team2357.frc2022.subsystems.util.VisionTargetSupplier;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;
import com.team2357.lib.util.RobotMath;

public class ShooterSubsystem extends ClosedLoopSubsystem {
    private static ShooterSubsystem instance = null;

    public static ShooterSubsystem getInstance() {
        return instance;
    }

    // {degrees, bottom shooter rpm, top shooter rpm}
    private static final double[][] degreesToRPMsCurve = {
            { 0, 0, 0 }, // Closest
            { 0, 0, 0 }, // Furthest
    };

    private WPI_TalonFX m_leftBottomMotor;
    private WPI_TalonFX m_rightBottomMotor;
    private WPI_TalonFX m_topMotor;

    private VisionTargetSupplier m_targetSupplier;
    private VisionTarget m_currentTarget;

    private static final double m_minutesTo100MS = 600;

    private Configuration m_config;

    public static class Configuration {
        public int m_encoder_cpr = 0;

        public double m_shooterMotorPeakOutput = 1.0;
        public double m_bottomShooterGearingRatio = 0;
        public double m_topShooterGearingRatio = 0;
        public int m_timeoutMS = 0;

        // bottom shooter motors
        public double m_bottomShooterP = 0;
        public double m_bottomShooterI = 0;
        public double m_bottomShooterD = 0;
        public double m_bottomShooterF = 0;

        // top shooter motor
        public double m_topShooterP = 0;
        public double m_topShooterI = 0;
        public double m_topShooterD = 0;
        public double m_topShooterF = 0;
    }

    /**
     * 
     * @param leftBottomShooter  The motor on the bottom left
     * @param rightBottomShooter The motor on the bottom right
     * @param topMotor           The motor on the top
     */
    public ShooterSubsystem(WPI_TalonFX leftBottomShooter, WPI_TalonFX rightBottomShooter, WPI_TalonFX topMotor) {
        instance = this;
        m_leftBottomMotor = leftBottomShooter;
        m_rightBottomMotor = rightBottomShooter;
        m_topMotor = topMotor;

    }

    public void configure(Configuration config) {

        m_config = config;

        // reset motor configs to known state
        m_leftBottomMotor.configFactoryDefault(m_config.m_timeoutMS);
        m_rightBottomMotor.configFactoryDefault(m_config.m_timeoutMS);
        m_topMotor.configFactoryDefault(m_config.m_timeoutMS);

        // Bottom motor config
        m_rightBottomMotor.setInverted(true);
        m_rightBottomMotor.follow(m_leftBottomMotor);

        m_leftBottomMotor.configClosedloopRamp(1.0);

        m_leftBottomMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, m_config.m_timeoutMS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_leftBottomMotor.setSensorPhase(true);

        // Configure output range
        m_leftBottomMotor.configNominalOutputForward(0, m_config.m_timeoutMS);
        m_leftBottomMotor.configNominalOutputReverse(0, m_config.m_timeoutMS);
        m_leftBottomMotor.configPeakOutputForward(m_config.m_shooterMotorPeakOutput, m_config.m_timeoutMS);
        m_leftBottomMotor.configPeakOutputReverse(0, m_config.m_timeoutMS); // don't run the motors in reverse

        m_leftBottomMotor.config_kP(0, m_config.m_bottomShooterP, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kI(0, m_config.m_bottomShooterI, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kD(0, m_config.m_bottomShooterD, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kF(0, m_config.m_bottomShooterF, m_config.m_timeoutMS);

        // Top motor config
        m_topMotor.setInverted(false);
        m_topMotor.configClosedloopRamp(1.0);

        m_topMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, m_config.m_timeoutMS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_topMotor.setSensorPhase(true);

        // Configure output range
        m_topMotor.configNominalOutputForward(0, m_config.m_timeoutMS);
        m_topMotor.configNominalOutputReverse(0, m_config.m_timeoutMS);
        m_topMotor.configPeakOutputForward(m_config.m_shooterMotorPeakOutput, m_config.m_timeoutMS);
        m_topMotor.configPeakOutputReverse(0, m_config.m_timeoutMS); // don't run the motors in reverse

        m_topMotor.config_kP(0, m_config.m_topShooterP, m_config.m_timeoutMS);
        m_topMotor.config_kI(0, m_config.m_topShooterI, m_config.m_timeoutMS);
        m_topMotor.config_kD(0, m_config.m_topShooterD, m_config.m_timeoutMS);
        m_topMotor.config_kF(0, m_config.m_topShooterF, m_config.m_timeoutMS);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute of the shooter wheels
     */
    public void setRPMBottom(double rpm) {
        rpm /= m_config.m_bottomShooterGearingRatio;
        double nativeSpeed = rpm * m_config.m_encoder_cpr / m_minutesTo100MS;
        m_leftBottomMotor.set(ControlMode.Velocity, nativeSpeed);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute of the shooter wheels
     */
    public void setRPMTop(double rpm) {
        rpm /= m_config.m_topShooterGearingRatio;
        double nativeSpeed = rpm * m_config.m_encoder_cpr / m_minutesTo100MS;
        m_topMotor.set(ControlMode.Velocity, nativeSpeed);
    }

    public void setShooterMotorsAxisSpeed(double axisSpeed) {
        double bottomSpeed = -axisSpeed / 2;
        double topSpeed = -axisSpeed;

        m_leftBottomMotor.set(bottomSpeed);
        m_topMotor.set(topSpeed);
    }

    public boolean hasTarget() {
        return m_currentTarget != null;
    }

    @Override
    public void periodic() {
        if (m_targetSupplier != null && isClosedLoopEnabled()) {
            visionTargetPeriodic();
        }
    }

    private void visionTargetPeriodic() {
        m_currentTarget = m_targetSupplier.getAsVisionTarget();

        if (m_currentTarget != null) {
            setClosedLoopRPMs();

        }
    }

    private void setClosedLoopRPMs() {
        int curveSegmentIndex = RobotMath.getCurveSegmentIndex(degreesToRPMsCurve, m_currentTarget.getY());
        if (curveSegmentIndex == -1) {
            System.err.println("----- Curve segment index out of bounds -----");
            return;
        }

        double[] pointA = degreesToRPMsCurve[curveSegmentIndex];
        double[] pointB = degreesToRPMsCurve[curveSegmentIndex + 1];

        double highAngle = pointA[0];
        double lowAngle = pointB[0];
        double highBottomRPMs = pointB[1];
        double lowBottomRPMs = pointB[1];
        double highTopRPMs = pointA[1];
        double lowTopRPMs = pointB[1];

        double bottomRpms = RobotMath.lineralyInterpolate(highAngle, lowAngle, highBottomRPMs, lowBottomRPMs,
                m_currentTarget.getY());

        double topRpms = RobotMath.lineralyInterpolate(highAngle, lowAngle, highTopRPMs, lowTopRPMs,
                m_currentTarget.getY());

        if (bottomRpms == Double.NaN || topRpms == Double.NaN) {
            System.err.println("----- Invalid shooter rpms -----");
        }

        setRPMBottom(bottomRpms);
        setRPMTop(topRpms);
    }

    public void setVisionTargetSupplier(VisionTargetSupplier targetSupplier) {
        m_targetSupplier = targetSupplier;
    }

    /**
     * 
     * @return Bottom motor speed in rpms
     */
    public double getBottomMotorSpeedRPMs() {
        return m_leftBottomMotor.getSelectedSensorVelocity() * m_minutesTo100MS / m_config.m_encoder_cpr;
    }

    /**
     * 
     * @return Top motor speed in rpms
     */
    public double getTopMotorSpeedRPMs() {
        return m_topMotor.getSelectedSensorVelocity() * m_minutesTo100MS / m_config.m_encoder_cpr;
    }

    /**
     * 
     * @return bottom Shooter rpms
     */
    public double getBottomShooterRPMs() {
        return getBottomMotorSpeedRPMs() * m_config.m_bottomShooterGearingRatio;
    }

    /**
     * 
     * @return top Shooter rpms
     */
    public double getTopShooterRPMs() {
        return getTopMotorSpeedRPMs() * m_config.m_bottomShooterGearingRatio;

    }
}
