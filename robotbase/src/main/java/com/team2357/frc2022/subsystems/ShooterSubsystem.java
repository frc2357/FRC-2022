package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.util.VisionTargetSupplier;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;
import com.team2357.lib.util.RobotMath;

public class ShooterSubsystem extends ClosedLoopSubsystem {

    // {degrees, bottom motor rpm, top motor rpm}
    private static final double[][] degreesToRPMsCurve = {
            { 0, 0, 0 }, // Closest
            { 0, 0, 0 }, // Furthest
    };

    private WPI_TalonFX m_leftBottomMotor;
    private WPI_TalonFX m_rightBottomMotor;
    private WPI_TalonFX m_topMotor;

    private VisionTargetSupplier m_targetSupplier;
    private VisionTarget m_currentTarget;

    private double m_lastVisionRPMs;

    /**
     * 
     * @param leftBottomShooter  The motor on the bottom left
     * @param rightBottomShooter The motor on the bottom right
     * @param topRoller          The motor on the top
     */
    public ShooterSubsystem(WPI_TalonFX leftBottomShooter, WPI_TalonFX rightBottomShooter, WPI_TalonFX topRoller) {
        m_leftBottomMotor = leftBottomShooter;
        m_rightBottomMotor = rightBottomShooter;
        m_topMotor = topRoller;

        // reset motor configs to known state
        m_leftBottomMotor.configFactoryDefault(Constants.TIMEOUT_MS);
        m_rightBottomMotor.configFactoryDefault(Constants.TIMEOUT_MS);
        m_topMotor.configFactoryDefault(Constants.TIMEOUT_MS);

        m_rightBottomMotor.setInverted(true);
        m_rightBottomMotor.follow(m_leftBottomMotor);

        m_leftBottomMotor.configClosedloopRamp(1.0);

        m_leftBottomMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, Constants.TIMEOUT_MS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_leftBottomMotor.setSensorPhase(true);

        // Configure output range
        m_leftBottomMotor.configNominalOutputForward(0, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configPeakOutputForward(Constants.SHOOTER.SHOOTER_MOTOR_PEAK_OUTPUT, Constants.TIMEOUT_MS);
        m_leftBottomMotor.configPeakOutputReverse(0, Constants.TIMEOUT_MS); // don't run the motors in reverse

        m_leftBottomMotor.config_kP(0, Constants.SHOOTER.SHOOTER_P, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kI(0, Constants.SHOOTER.SHOOTER_I, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kD(0, Constants.SHOOTER.SHOOTER_D, Constants.TIMEOUT_MS);
        m_leftBottomMotor.config_kF(0, Constants.SHOOTER.SHOOTER_F, Constants.TIMEOUT_MS);

        m_topMotor.setInverted(true);
        m_topMotor.configClosedloopRamp(1.0);

        m_topMotor
                .configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, Constants.TIMEOUT_MS);

        // >>> Change this if positive motor output gives negative encoder feedback <<<
        m_topMotor.setSensorPhase(true);

        // Configure output range
        m_topMotor.configNominalOutputForward(0, Constants.TIMEOUT_MS);
        m_topMotor.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
        m_topMotor.configPeakOutputForward(Constants.SHOOTER.SHOOTER_MOTOR_PEAK_OUTPUT, Constants.TIMEOUT_MS);
        m_topMotor.configPeakOutputReverse(0, Constants.TIMEOUT_MS); // don't run the motors in reverse

        m_topMotor.config_kP(0, Constants.SHOOTER.SHOOTER_P, Constants.TIMEOUT_MS);
        m_topMotor.config_kI(0, Constants.SHOOTER.SHOOTER_I, Constants.TIMEOUT_MS);
        m_topMotor.config_kD(0, Constants.SHOOTER.SHOOTER_D, Constants.TIMEOUT_MS);
        m_topMotor.config_kF(0, Constants.SHOOTER.SHOOTER_F, Constants.TIMEOUT_MS);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute
     */
    public void setClosedLoopRPMBottom(double rpm) {
        double nativeSpeed = rpm * Constants.SHOOTER.FALCON_ENCODER_CPR / Constants.MINUTES_TO_100_MS;
        m_leftBottomMotor.set(ControlMode.Velocity, nativeSpeed);
    }

    /**
     * Set the motor speed using closed-loop control
     * 
     * @param rpm rotations per minute
     */
    public void setClosedLoopRPMTop(double rpm) {
        double nativeSpeed = rpm * Constants.SHOOTER.FALCON_ENCODER_CPR / Constants.MINUTES_TO_100_MS;
        m_topMotor.set(ControlMode.Velocity, nativeSpeed);
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

        if (bottomRpms == Double.NaN) {
            bottomRpms = m_lastVisionRPMs;
        }

        setClosedLoopRPMBottom(bottomRpms);
        setClosedLoopRPMTop(topRpms);
    }

    /**
     * Set the motor to a percent output. This bypasses closed-loop control.
     * 
     * @param output
     */
    public void runMotorOpenLoop(double output) {
        m_currentTarget = null;
        m_leftBottomMotor.set(ControlMode.PercentOutput, output);
    }

    public void setVisionTarget(VisionTargetSupplier targetSupplier) {
        m_targetSupplier = targetSupplier;
    }

    /**
     * @return current motor velocity in rpm
     */
    public double getMotorSpeed(WPI_TalonFX motor) {
        return motor.getSelectedSensorVelocity() * Constants.MINUTES_TO_100_MS / Constants.SHOOTER.FALCON_ENCODER_CPR;
    }

    public double getMotorSpeed() {
        return getMotorSpeed(m_leftBottomMotor);
    }

    public double getShooterRPMs() {
        return getMotorSpeed() * Constants.SHOOTER.SHOOTER_GEARING_RATIO;
    }

}
