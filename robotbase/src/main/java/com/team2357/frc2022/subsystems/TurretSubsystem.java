package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.util.VisionTargetSupplier;
import com.team2357.lib.arduino.ArduinoUSBController;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretSubsystem extends ClosedLoopSubsystem {
    CANSparkMax m_turretMotor;
    private SparkMaxPIDController m_pidController;
    private ArduinoUSBController m_arduinoHallEffectSensor;
    Configuration m_config;

    private VisionTargetSupplier m_targetSupplier;
    private VisionTarget m_currentTarget;

    private double m_targetDegrees;

    // Has the turret been zeroed
    private boolean m_isZeroed;
    private boolean m_isFlipping;

    public static class Configuration {
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

        public double m_turretRotationsClockwiseSoftLimit = 0;
        public double m_turretRotationsCounterClockwiseSoftLimit = 0;
        public double m_rotationsPerDegree = 0;
        public double m_degreeOffset = 0;
    }

    public TurretSubsystem(CANSparkMax turretMotor) {
        m_turretMotor = turretMotor;

        m_isFlipping = false;

        m_arduinoHallEffectSensor = new ArduinoUSBController(Constants.ARDUINO.ARDUINO_SENSOR_DEVICE_NAME);

        m_arduinoHallEffectSensor.start();

        m_isZeroed = false;
        resetHeading();
    }

    public void configure(Configuration config) {
        m_config = config;

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

    public void setTurretSpeed(double speed) {
        m_turretMotor.set(speed);
    }

    public void resetHeading() {
        m_turretMotor.getEncoder().setPosition(0);
    }

    public double getTurretDegrees() {
        return m_turretMotor.getEncoder().getPosition() / m_config.m_rotationsPerDegree;
    }

    /**
     * 
     * @param targetDegrees Desired position of the turret in degrees
     */
    public void setTurretPosition(double targetDegrees) {
        m_targetDegrees = targetDegrees;
        double setPoint = (m_targetDegrees + m_config.m_degreeOffset) * m_config.m_rotationsPerDegree;
        m_pidController.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
    }

    public boolean atDegrees() {
        return atDegrees(m_targetDegrees);
    }

    public boolean atDegrees(double targetDegrees) {
        return com.team2357.frc2022.util.Utility.isWithinTolerance(getTurretDegrees(), targetDegrees,
                m_config.m_turretMotorAllowedError);
    }

    public boolean isOnZero() {
        boolean isMagnetDetected = false;

        if (m_arduinoHallEffectSensor.isConnected()) {
            isMagnetDetected = !m_arduinoHallEffectSensor
                    .getDeviceFieldBoolean(Constants.ARDUINO.TURRET_HALL_SENSOR_NAME, "state");
        }

        if (isMagnetDetected) {
            m_isZeroed = true;
        }

        return isMagnetDetected;
    }

    @Override
    public void periodic() {
        if (isClosedLoopEnabled()) {
            turretTrackingPeriodic();
        }

        // For tuning
        SmartDashboard.putNumber("SetPoint", m_targetDegrees);
        SmartDashboard.putNumber("Encoder Pos", m_turretMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Encoder Vel", m_turretMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Output", m_turretMotor.getAppliedOutput());
    }

    public boolean hasTarget() {
        return m_currentTarget != null;
    }

    public boolean isTargetLocked() {
        if (!isClosedLoopEnabled()) {
            return false;
        }
        return atDegrees();
    }

    @Override
    public void setClosedLoopEnabled(boolean enabled) {
        if (enabled) {
            return;
        }
        super.setClosedLoopEnabled(false);
        disableTrackingPeriodic();
    }

    public void initTrackingPeriodic(VisionTargetSupplier targetSupplier) {
        m_targetSupplier = targetSupplier;
    }

    public void disableTrackingPeriodic() {
        m_targetSupplier = null;
        m_turretMotor.set(0);
    }

    public void turretTrackingPeriodic() {
        m_currentTarget = m_targetSupplier.getAsVisionTarget();

        if (m_isZeroed) {
            if (m_currentTarget != null) {

                double degrees = calculateDegrees(m_currentTarget);
                setTurretPosition(degrees);
                
            } else {
                if(m_isFlipping) {
                    if(atDegrees()) {
                        m_isFlipping = false;
                    }
                } else {
                    System.err.println("----- NO VISION TARGET -----");
                }
            }
        }

        // Occasionally reset heading to reduce error overtime
        if (isOnZero()) {
            resetHeading();
        }
    }

    /**
     * Calculate setpoint based on target x value
     * Assumes limelight is mounted centered on the turret
     * 
     * @param target The vision target.
     * @return desired degree on the turret
     */
    private double calculateDegrees(VisionTarget target) {
        double degrees = target.getX() + getTurretDegrees();

        // Cases if target is out of reach
        if (degrees < m_config.m_turretRotationsClockwiseSoftLimit) {
            degrees = m_config.m_turretRotationsCounterClockwiseSoftLimit + (degrees - m_config.m_turretRotationsClockwiseSoftLimit);
            m_isFlipping = true;
        } else if (degrees > m_config.m_turretRotationsCounterClockwiseSoftLimit) {
            degrees = m_config.m_turretRotationsClockwiseSoftLimit + (degrees - m_config.m_turretRotationsCounterClockwiseSoftLimit);
            m_isFlipping = true;
        }

        return degrees;
    }
}
