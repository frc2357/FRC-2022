package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.Constants;
import com.team2357.lib.arduino.ArduinoUSBController;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

public class TurretSubsystem extends ClosedLoopSubsystem {
    CANSparkMax m_turretMotor;
    private SparkMaxPIDController m_pidController;
    private ArduinoUSBController m_arduinoHallEffectSensor;
    Configuration m_config;

    public double m_currentAngle;

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

        public double m_StartAngle = 0;
    }

    public TurretSubsystem(CANSparkMax turretMotor) {
        m_turretMotor = turretMotor;

        m_config = new Configuration();
        configure(m_config);

        m_arduinoHallEffectSensor = new ArduinoUSBController(Constants.ARDUINO.ARDUINO_SENSOR_DEVICE_NAME);

        m_arduinoHallEffectSensor.start();

        resetHeading();
    }

    public void configure(Configuration config) {
        m_config = config;

        m_turretMotor.setIdleMode(m_config.turretMotorIdleMode);
        m_turretMotor.setSmartCurrentLimit(m_config.m_turretMotorStallLimitAmps, m_config.m_turretMotorFreeLimitAmps);

        m_pidController = m_turretMotor.getPIDController();

        // PID coefficients
        m_config.m_turretMotorP = 5e-5;
        m_config.m_turretMotorI = 1e-6;
        m_config.m_turretMotorD = 0;
        m_config.m_turretMotorIZone = 0;
        m_config.m_turretMotorFF = 0.000156;
        m_config.m_turretMotorMinOutput = 1;
        m_config.m_turretMotorMinOutput = -1;
        m_config.m_turretMotorMaxRPM = 5700;

        // Smart Motion Coefficients
        m_config.m_turretMotorMaxVel = 2000; // rpm
        m_config.m_turretMotorMaxAcc = 1500;

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
        m_currentAngle = m_config.m_StartAngle;
    }

    public void m_setTurretPosition(double position) {
        m_pidController.setReference(position, CANSparkMax.ControlType.kSmartMotion);
    }

    public boolean isOnZero() {
        boolean isMagnetDetected = false;

        if (m_arduinoHallEffectSensor.isConnected())
        {
            isMagnetDetected = !m_arduinoHallEffectSensor.getDeviceFieldBoolean(Constants.ARDUINO.TURRET_HALL_SENSOR_NAME, "state");
        }

        return isMagnetDetected;
    }
}
