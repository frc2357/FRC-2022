package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.util.RobotMath;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends ClosedLoopSubsystem {
    private static ShooterSubsystem instance = null;

    public static ShooterSubsystem getInstance() {
        return instance;
    }

    // {low goal, 1000, 2000}
    // {degrees, bottom shooter rpm, top shooter rpm}
    private static final double[][] degreesToRPMsCurve = {
            {45, 2000, 3500},
            { 22.5, 1750, 3500 }, // Closest
            { -6.7, 3100, 10275 }, // Furthest
    };

    private WPI_TalonFX m_leftBottomMotor;
    private WPI_TalonFX m_rightBottomMotor;
    private WPI_TalonFX m_topMotor;

    private double m_bottomTargetRPMs = Double.NaN;
    private double m_topTargetRPMs = Double.NaN;

    private static final double m_minutesTo100MS = 600;

    private Configuration m_config;

    public static class Configuration {
        public int m_encoder_cpr = 0;

        public double m_shooterMotorPeakOutput = 1.0;
        public double m_bottomShooterGearingRatio = 0;
        public double m_topShooterGearingRatio = 0;
        public int m_timeoutMS = 0;

        public double m_shooterAllowedErrorRPM = 0;
        public int m_PIDSlot = 0;

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

        setClosedLoopEnabled(false);
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

        m_leftBottomMotor.configClosedloopRamp(0.25);

        m_leftBottomMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, m_config.m_timeoutMS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_leftBottomMotor.setSensorPhase(true);

        // Configure output range
        m_leftBottomMotor.configNominalOutputForward(0, m_config.m_timeoutMS);
        m_leftBottomMotor.configNominalOutputReverse(0, m_config.m_timeoutMS);
        m_leftBottomMotor.configPeakOutputForward(m_config.m_shooterMotorPeakOutput, m_config.m_timeoutMS);
        m_leftBottomMotor.configPeakOutputReverse(0, m_config.m_timeoutMS); // don't run the motors in reverse

        m_leftBottomMotor.config_kP(m_config.m_PIDSlot, m_config.m_bottomShooterP, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kI(m_config.m_PIDSlot, m_config.m_bottomShooterI, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kD(m_config.m_PIDSlot, m_config.m_bottomShooterD, m_config.m_timeoutMS);
        m_leftBottomMotor.config_kF(m_config.m_PIDSlot, m_config.m_bottomShooterF, m_config.m_timeoutMS);

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

    @Override
    public void setClosedLoopEnabled(boolean closedLoopEnabled) {
        super.setClosedLoopEnabled(closedLoopEnabled);

        if (!closedLoopEnabled) {
            m_bottomTargetRPMs = Double.NaN;
            m_topTargetRPMs = Double.NaN;
        }
    }


    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute of the shooter wheels
     */
    public void setRPMBottom(double rpm) {
        m_bottomTargetRPMs = rpm;
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
        m_topTargetRPMs = rpm;
        rpm /= m_config.m_topShooterGearingRatio;
        double nativeSpeed = rpm * m_config.m_encoder_cpr / m_minutesTo100MS;
        m_topMotor.set(ControlMode.Velocity, nativeSpeed);
    }

    public void stop() {
        m_leftBottomMotor.set(ControlMode.PercentOutput, 0.0);
        m_topMotor.set(ControlMode.PercentOutput, 0.0);
        setClosedLoopEnabled(false);
    }

    public void setShooterMotorsAxisSpeed(double axisSpeed) {
        double bottomSpeed = -axisSpeed / 2;
        double topSpeed = -axisSpeed;

        m_leftBottomMotor.set(bottomSpeed);
        m_topMotor.set(topSpeed);
        setClosedLoopEnabled(false);
    }

    public boolean hasTarget() {
        return LimelightSubsystem.getInstance().validTargetExists();
    }

    public boolean atTargetSpeed() {
        return Utility.isWithinTolerance(getBottomShooterRPMs(), m_bottomTargetRPMs, m_config.m_shooterAllowedErrorRPM) && Utility.isWithinTolerance(getTopShooterRPMs(), m_topTargetRPMs, m_config.m_shooterAllowedErrorRPM);
    }

    @Override
    public void periodic() {
        if (isClosedLoopEnabled()) {
            shootVisionPeriodic();
        }

        SmartDashboard.putNumber("bottom", getBottomShooterRPMs());
        SmartDashboard.putNumber("top", getTopShooterRPMs());
    }

    public boolean isVisionShooting() {
        return !Double.isNaN(m_bottomTargetRPMs);
    }

    public void startVisionShooting() {
        setClosedLoopEnabled(true);
    }

    private void shootVisionPeriodic() {
        if (hasTarget()) {
            setShootVisionRPMs(LimelightSubsystem.getInstance().getTY());
        } else {
            System.out.println("----- No vision target -----");
        }
    }

    private void setShootVisionRPMs(double yAngle) {
        int curveSegmentIndex = RobotMath.getCurveSegmentIndex(degreesToRPMsCurve, yAngle);
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

        double bottomRpms = RobotMath.lineralyInterpolate(highBottomRPMs, lowBottomRPMs, highAngle, lowAngle, yAngle);

        double topRpms = RobotMath.lineralyInterpolate(highTopRPMs, lowTopRPMs, highAngle, lowAngle, yAngle);

        if (Double.isNaN(bottomRpms) || Double.isNaN(topRpms)) {
            System.err.println("----- Invalid shooter rpms -----");
            return;
        }

        setRPMBottom(bottomRpms);
        setRPMTop(topRpms);
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
        return getTopMotorSpeedRPMs() * m_config.m_topShooterGearingRatio;

    }
}
