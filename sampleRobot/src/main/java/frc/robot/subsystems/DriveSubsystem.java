package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private DifferentialDrive m_differentialDrive;
    private Configuration m_config;
    public static DriveSubsystem instance;
    private MotorControllerGroup m_rightControllers;
    private MotorControllerGroup m_leftControllers;

    public static class Configuration {
        public double m_speedSensitivity;
        public double m_turnSensitivity;
    }

    public DriveSubsystem(MotorControllerGroup leftControllers, MotorControllerGroup rightControllers) {
        rightControllers.setInverted(true);
        m_rightControllers = rightControllers;
        m_leftControllers = leftControllers;
        m_differentialDrive = new DifferentialDrive(leftControllers, rightControllers);
        instance = this;
    }

    public void config(Configuration configuration) {
        m_config = configuration;
    }

    public void setDrive(double speed, double turn) {
        speed *= m_config.m_speedSensitivity;
        turn *= m_config.m_turnSensitivity;
        m_differentialDrive.arcadeDrive(speed, turn);
    }

    public static DriveSubsystem getInstance() {
        return instance;
    }

}
