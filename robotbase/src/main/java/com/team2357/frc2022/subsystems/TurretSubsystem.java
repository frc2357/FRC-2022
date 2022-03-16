package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;

import edu.wpi.first.math.controller.PIDController;

import com.team2357.frc2022.subsystems.util.VisionTargetSupplier;
import com.team2357.frc2022.util.Utility;

public class TurretSubsystem extends ClosedLoopSubsystem {
    private static TurretSubsystem instance = null;

    public static TurretSubsystem getInstance() {
        return instance;
    }

    CANSparkMax m_turretMotor;

    private SparkMaxPIDController m_pidController;

    private PIDController m_trackingPidController;

    private VisionTargetSupplier m_targetSupplier;

    private Configuration m_config;

    private double m_targetMotorRotations = Double.NaN;


    public static class Configuration {
        public double m_turretAxisMaxSpeed = 0;

        public IdleMode turretMotorIdleMode = IdleMode.kBrake;
        public int m_turretMotorStallLimitAmps = 0;
        public int m_turretMotorFreeLimitAmps = 0;

        public double m_trackingP = 0;
        public double m_trackingI = 0;
        public double m_trackingD = 0;
        public double m_trackingSetpoint = 0; // The center of the camera view is zero.
        public double m_trackingToleranceDegrees = 0;
        public double m_trackingMaxSpeed = 0;

        public double m_turretMotorP = 0;
        public double m_turretMotorI = 0;
        public double m_turretMotorD = 0;
        public double m_turretMotorIZone = 0;
        public double m_turretMotorFF = 0;
        public double m_turretMotorMaxOutput = 0;
        public double m_turretMotorMinOutput = 0;
        public double m_turretMotorMaxRPM = 0;
        public double m_turretMotorMaxVel = 0;
        public double m_turretMotorMinVel = 0;
        public double m_turretMotorMaxAcc = 0;
        public double m_turretMotorAllowedError = 0;

        public double m_turretRotationsCounterClockwiseSoftLimit = 0.0;
        public double m_turretRotationsClockwiseSoftLimit = 0.0;
        public double m_turretGearRatio = 0.0;
    }

    public TurretSubsystem(CANSparkMax turretMotor) {
        instance = this;
        m_turretMotor = turretMotor;
        m_trackingPidController = null;
        resetEncoder();
    }

    public void configure(Configuration config) {
        m_config = config;

        m_turretMotor.setInverted(true);
        m_turretMotor.setIdleMode(m_config.turretMotorIdleMode);
        m_turretMotor.setSmartCurrentLimit(m_config.m_turretMotorStallLimitAmps, m_config.m_turretMotorFreeLimitAmps);

        m_pidController = m_turretMotor.getPIDController();

        // set PID coefficients
        m_pidController.setP(m_config.m_turretMotorP);
        m_pidController.setI(m_config.m_turretMotorI);
        m_pidController.setD(m_config.m_turretMotorD);
        m_pidController.setIZone(m_config.m_turretMotorIZone);
        m_pidController.setFF(m_config.m_turretMotorFF);
        m_pidController.setOutputRange(m_config.m_turretMotorMinOutput, m_config.m_turretMotorMinOutput);

        // Configure smart motion
        int smartMotionSlot = 0;
        m_pidController.setSmartMotionMaxVelocity(m_config.m_turretMotorMaxVel, smartMotionSlot);
        m_pidController.setSmartMotionMinOutputVelocity(m_config.m_turretMotorMinVel, smartMotionSlot);
        m_pidController.setSmartMotionMaxAccel(m_config.m_turretMotorMaxAcc, smartMotionSlot);
        m_pidController.setSmartMotionAllowedClosedLoopError(m_config.m_turretMotorAllowedError, smartMotionSlot);
    }

    @Override
    public void setClosedLoopEnabled(boolean closedLoopEnabled) {
        super.setClosedLoopEnabled(closedLoopEnabled);

        if (!closedLoopEnabled) {
            m_targetMotorRotations = Double.NaN;
            m_targetSupplier = null;
        }
    }

    public void resetEncoder() {
        m_turretMotor.getEncoder().setPosition(0);
    }

    public void stop() {
        setTurretSpeed(0, true, true);;
    }

    public void setTurretAxisSpeed(double axisSpeed) {
        double motorSpeed = axisSpeed * m_config.m_turretAxisMaxSpeed;
        setTurretSpeed(motorSpeed, true, true);
    }

    public boolean setTurretSpeed(double speed, boolean overrideClosedLoop, boolean overrideLimits) {
        if (isClosedLoopEnabled()) {
            if (!overrideClosedLoop) {
                System.err.println("TURRET: setTurretSpeed called while in closed loop");
                return false;
            }
            setClosedLoopEnabled(false);
        }

        double rotation = getTurretRotation();
        if (
            overrideLimits || 
            speed < 0 && rotation <= m_config.m_turretRotationsCounterClockwiseSoftLimit ||
            speed > 0 && rotation >= m_config.m_turretRotationsClockwiseSoftLimit
        ) {
            System.err.println("TURRET: setTurretSpeed at limit, cannot go further");
            return false;
        }

        m_turretMotor.set(speed);
        return true;
    }

    public double getTurretRotation() {
        return m_turretMotor.getEncoder().getPosition() / m_config.m_turretGearRatio;
    }

    public boolean setTurretRotation(double rotation) {
        if (rotation < m_config.m_turretRotationsCounterClockwiseSoftLimit ||
            rotation > m_config.m_turretRotationsClockwiseSoftLimit) {

            System.err.println("TURRET: setTurretRotation invalid rotation");
            return false;
        }

        setClosedLoopEnabled(true);
        m_targetMotorRotations = rotation * m_config.m_turretGearRatio;
        m_pidController.setReference(m_targetMotorRotations, CANSparkMax.ControlType.kSmartMotion);
        return true;
    }

    public boolean isRotatingToPosition() {
        return !Double.isNaN(m_targetMotorRotations);
    }

    public boolean isTracking() {
        return m_targetSupplier != null;
    }

    public void flip360() {
        double rotation = getTurretRotation();
        if (rotation > 0) {
            rotation -= 1.0;
        } else {
            rotation += 1.0;
        }

        setTurretRotation(rotation);
    }

    public boolean isAtTarget() {
        double currentMotorRotations = m_turretMotor.getEncoder().getPosition();
        return Utility.isWithinTolerance(currentMotorRotations, m_targetMotorRotations, m_config.m_turretMotorAllowedError);
    }

    public void trackTarget(VisionTargetSupplier targetSupplier) {
        m_targetSupplier = targetSupplier;
        m_trackingPidController = new PIDController(m_config.m_trackingP, m_config.m_trackingI, m_config.m_trackingD);
        m_trackingPidController.setSetpoint(m_config.m_trackingSetpoint);
        m_trackingPidController.setTolerance(m_config.m_trackingToleranceDegrees);
        setClosedLoopEnabled(true);
    }

    @Override
    public void periodic() {
        handleMotorStall();

        if (isClosedLoopEnabled()) {
            if (isRotatingToPosition()) {
                if (isAtTarget()) {
                    if (!isTracking()) {
                        stop();
                        return;
                    }
                    m_targetMotorRotations = Double.NaN;
                }
            }
            if (isTracking() && !isRotatingToPosition()) {
                // We're tracking a target.
                trackingPeriodic();
            }
        }
    }

    private void trackingPeriodic() {
        VisionTarget target = m_targetSupplier.getAsVisionTarget();

        if (target == null) {
            // No target found. Just stop here, but keep tracking.
            m_turretMotor.set(0);
            return;
        }

        double errorDegrees = target.getX();
        double motorSpeed = m_trackingPidController.calculate(errorDegrees);
        double clampedMotorSpeed = com.team2357.lib.util.Utility.clamp(motorSpeed, -m_config.m_trackingMaxSpeed, m_config.m_trackingMaxSpeed);
        System.out.println("error: " + errorDegrees + ", speed:" + motorSpeed + ", clamped: " + clampedMotorSpeed);
        //m_turretMotor.set(clampedMotorSpeed);
    }

    /**
     * Handles if the turret is stalling
     * Probably happening because we are on the mechanical stop
     */
    private void handleMotorStall() {
        if (m_turretMotor.getOutputCurrent() >= m_config.m_turretMotorStallLimitAmps) {
            System.err.println("TURRET: Motor stalled!");
            stop();
        }
    }
}
