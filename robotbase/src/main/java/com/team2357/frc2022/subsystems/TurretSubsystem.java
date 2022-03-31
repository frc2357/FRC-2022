package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team2357.frc2022.util.Utility;

public class TurretSubsystem extends ClosedLoopSubsystem {
    private static TurretSubsystem instance = null;

    public static TurretSubsystem getInstance() {
        return instance;
    }

    CANSparkMax m_turretMotor;

    private SparkMaxPIDController m_pidController;

    private PIDController m_trackingPidController;

    private Configuration m_config;

    private double m_targetMotorRotations = Double.NaN;


    public static class Configuration {
        public double m_turretAxisMaxSpeed = 0;

        public IdleMode turretMotorIdleMode = IdleMode.kCoast;
        public int m_turretMotorStallLimitAmps = 0;
        public int m_turretMotorFreeLimitAmps = 0;

        public double m_trackingP = 0;
        public double m_trackingI = 0;
        public double m_trackingD = 0;
        public double m_trackingSetpoint = 0; // The center of the camera view is zero.
        public double m_trackingToleranceDegrees = 0;
        public double m_trackingAllowedError = 0;
        public double m_trackingMaxSpeed = 0;
        public double m_trackingMinSpeed = 0;

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

        m_turretMotor.setInverted(false);
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
            m_trackingPidController = null;
        }
    }

    public void resetEncoder() {
        m_turretMotor.getEncoder().setPosition(0);
    }

    public void stop() {
        setTurretSpeed(0, true, true);;
    }

    public void setTurretAxisSpeed(double axisSpeed) {
        if (isTracking() && axisSpeed == 0) {
            // If we're currently tracking and the axis is in its deadzone, don't interrupt tracking.
            return;
        }
        double motorSpeed = (-axisSpeed) * m_config.m_turretAxisMaxSpeed;
        setTurretSpeed(motorSpeed, true, false);
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
        if (!overrideLimits && (
            atLimits(speed, rotation))) {
            System.err.println("TURRET: setTurretSpeed at limit, cannot go further");
            m_turretMotor.set(0);
            return false;
        }

        m_turretMotor.set(speed);
        return true;
    }

    public double getTurretRotation() {
        return m_turretMotor.getEncoder().getPosition() / m_config.m_turretGearRatio;
    }

    public boolean setTurretRotation(double rotation) {    
        // TODO: Uncomment and test this code.
        /*
        if (rotation <= m_config.m_turretRotationsCounterClockwiseSoftLimit ||
            rotation >= m_config.m_turretRotationsClockwiseSoftLimit) {

            System.err.println("TURRET: setTurretRotation invalid rotation");
            return false;
        }

        setClosedLoopEnabled(true);
        m_targetMotorRotations = rotation * m_config.m_turretGearRatio;
        m_pidController.setReference(m_targetMotorRotations, CANSparkMax.ControlType.kSmartMotion);
        */
        return true;
    }

    public boolean isRotatingToPosition() {
        return !Double.isNaN(m_targetMotorRotations);
    }

    public void flipTurret(double rotation) {
            // TODO: Uncomment and test this code.
/*
        if (rotation > 0) {
            rotation = m_config.m_turretRotationsCounterClockwiseSoftLimit;
        } else {
            rotation = m_config.m_turretRotationsClockwiseSoftLimit;
        }

        setTurretRotation(rotation);
        */
    }

    public boolean atLimits(double speed, double rotation) {
        if(speed < 0 && rotation <= m_config.m_turretRotationsCounterClockwiseSoftLimit ||
        speed > 0 && rotation >= m_config.m_turretRotationsClockwiseSoftLimit) {
            return true;
        }
        return false;
    }

    public boolean isAtTarget() {
        if (isTracking()) {
            double error = m_trackingPidController.getPositionError();
            return error <= m_config.m_trackingAllowedError;
        }

        double currentMotorRotations = m_turretMotor.getEncoder().getPosition();
        return Utility.isWithinTolerance(currentMotorRotations, m_targetMotorRotations, m_config.m_turretMotorAllowedError);
    }

    public void trackTarget() {
        m_trackingPidController = new PIDController(m_config.m_trackingP, m_config.m_trackingI, m_config.m_trackingD);
        m_trackingPidController.setSetpoint(m_config.m_trackingSetpoint);
        m_trackingPidController.setTolerance(m_config.m_trackingToleranceDegrees / 2);
        setClosedLoopEnabled(true);
    }

    public boolean isTracking() {
        return m_trackingPidController != null;
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
        LimelightSubsystem limelight = LimelightSubsystem.getInstance();

        // TODO: Add check to ensure Limelight is up and responsive if possible.
        if (!limelight.validTargetExists()) {
            setClosedLoopEnabled(false);
            return;
        }

        double errorDegrees = limelight.getTX();
        if (Math.abs(errorDegrees) < m_config.m_trackingToleranceDegrees) {
            return;
        }

        double motorSpeed = m_trackingPidController.calculate(errorDegrees);
        double clampedMotorSpeed = com.team2357.lib.util.Utility.clamp(motorSpeed, -m_config.m_trackingMaxSpeed, m_config.m_trackingMaxSpeed);
        //System.out.println("error: " + errorDegrees + ", motorSpeed: " + motorSpeed + " clamped: " + clampedMotorSpeed);

        if(atLimits(clampedMotorSpeed, getTurretRotation())) {
            m_turretMotor.set(0);
            m_trackingPidController = null;
        }

        m_turretMotor.set(clampedMotorSpeed);
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
