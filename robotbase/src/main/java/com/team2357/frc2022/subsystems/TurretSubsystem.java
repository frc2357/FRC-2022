package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.frc2022.util.Utility;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretSubsystem extends ClosedLoopSubsystem {
    private static TurretSubsystem instance = null;

    public static TurretSubsystem getInstance() {
        return instance;
    }

    CANSparkMax m_turretMotor;

    private SparkMaxPIDController m_pidController;

    private Configuration m_config;

    private double m_targetMotorRotations = Double.NaN;

    public static class Configuration {
        public double m_turretAxisMaxSpeed = 0;

        public IdleMode turretMotorIdleMode = IdleMode.kBrake;
        public int m_turretMotorStallLimitAmps = 0;
        public int m_turretMotorFreeLimitAmps = 0;

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
        m_turretMotor.getEncoder().setPosition(0);
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
        }
    }

    public void stop() {
        setTurretSpeed(0, true);;
    }

    public void setTurretAxisSpeed(double axisSpeed) {
        double motorSpeed = axisSpeed * m_config.m_turretAxisMaxSpeed;
        setTurretSpeed(motorSpeed, true);
    }

    public void setTurretSpeed(double speed, boolean overrideClosedLoop) {
        if (isClosedLoopEnabled()) {
            if (!overrideClosedLoop) {
                System.err.println("TURRET: setTurretSpeed called while in closed loop");
                return;
            }

            setClosedLoopEnabled(false);
        }
        m_turretMotor.set(speed);
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

    public boolean isAtTarget() {
        double currentMotorRotations = m_turretMotor.getEncoder().getPosition();
        return Utility.isWithinTolerance(currentMotorRotations, m_targetMotorRotations, m_config.m_turretMotorAllowedError);
    }

    @Override
    public void periodic() {
        handleMotorStall();

        if (isClosedLoopEnabled() && isAtTarget()) {
            // We've reached our target, stop closed loop.
            setTurretSpeed(0, true);
        }
    }

    /**
     * Handles if the turret is stalling
     * Probably happening because we are on the mechanical stop
     */
    private void handleMotorStall() {
        if (m_turretMotor.getOutputCurrent() >= m_config.m_turretMotorStallLimitAmps) {
            System.err.println("TURRET: Motor stalled!");
            setTurretSpeed(0, true);
            setClosedLoopEnabled(false);
        }
    }
}
